package skcnc.msa3.framework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private static final String[] AUTH_WHITELIST = {
            "/"
            , "/demo1/controller001/**"      //JPA 테스트용
            , "/demo2/kafkacontroller001/**" //카프카 테스트용
            , "/demo3/dbtestcontroller/**"   //Mybatis 테스트용...
            ,"/swagger-ui/**", "/v3/api-docs/**" //swagger 를 사용하기 위해서 제외 해야 하는 필수 url 2개 모두 필수임
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable() //rest api 이므로 기본설정 사용안함
                .csrf().disable() //rest api 이므로 불필요
                .cors().disable() //cors 미사용.
                .formLogin().disable() //로그인화면 비활성
                .logout().disable() //로그아웃 비활성
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll() //제외 url 전체 권한 추가.
                        .anyRequest().authenticated())
                /*.authorizeHttpRequests(authorize -> authorize
                        .shouldFilterAllDispatcherTypes(false)
                        .requestMatchers(AUTH_WHITELIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthenticationFilter(jwtTokenUtil, firebaseService), UsernamePasswordAuthenticationFilter.class)
                 */
                .build();
    }

    //@Value("${spring.profiles.active}")
    //private String activeProfile;

    //@Autowired
    //NoauthAuthenticationEntryPoint noauthAuthenticationEntryPoint;

    /*
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //rest api 이므로 기본설정 사용안함
                .csrf().disable() //rest api 이므로 불필요
                .formLogin().disable() //로그인화면 비활성
                .logout().disable() //로그아웃 비활성
                .cors().disable() //cors 비활성
                .headers().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 토큰사용하므로 세션 불필요
                .and()
                .authorizeRequests() // 다음 요청들에 대한 사용권한 체크
                .anyRequest().hasRole("USER") //그외 요청은 인증된 사용자중 USER role 보유자만 허용
                .and()
                .exceptionHandling().authenticationEntryPoint(noauthAuthenticationEntryPoint)
                .and()
                //jwt 토큰 필터를 usernamepassword 필터 전에 넣는다. <== 이게 중요함
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;
    }*/

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests((requests) -> requests
                        .requestMatchers("/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .cors().disable()
                .csrf().disable()
                .headers().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .and()
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(
                            response.getOutputStream(),
                            ExceptionResponse.of(ExceptionCode.FAIL_AUTHENTICATION)
                    );
                }))
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(
                            response.getOutputStream(),
                            ExceptionResponse.of(ExceptionCode.FAIL_AUTHORIZATION)
                    );
                })).and().build();
    }*/


    /**
     * 인증 제외 처리
     */
    /*@Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/demo1/controller001/login.do",
                "/swagger-ui/**"
        );
    }*/
}
