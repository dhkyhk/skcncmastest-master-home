package skcnc.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final String[] AUTH_WHITELIST = { "/ca/auth/**", "/ac/auth/**", "/rp/auth/**",
			"/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs" };

	private final JwtTokenProvider jwtTokenProvider;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Autowired
	NoauthAuthenticationEntryPoint noauthAuthenticationEntryPoint;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).formLogin(AbstractHttpConfigurer::disable)
				// .logout().disable()
				.exceptionHandling(exception -> exception.authenticationEntryPoint(noauthAuthenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated())
		;

		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
