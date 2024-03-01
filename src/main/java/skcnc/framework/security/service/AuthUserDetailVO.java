package skcnc.framework.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDetailVO implements UserDetails {
	private static final long serialVersionUID = -1725683501141793191L;

	/**
	 * 마이데이터 사용자 아이디를 PK 로 한다.
	 */
	@Getter @Setter
	private String msa_id;
	
	/**
	 * 토큰 최초 생성시간(로그인 시간), 단위 : 초
	 */
	@Getter @Setter
	private Date tokenIssuedAt;
	
	/**
	 * 해당 사용자가 보유한 role
	 */
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(m -> new SimpleGrantedAuthority("ROLE_" + m)).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.msa_id;
	}

	
	//이후 부터는 jwt 토큰 사용하면서 불필요하므로 모두 true로 리턴하고 사용안함.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
