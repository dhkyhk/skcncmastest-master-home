package skcnc.framework.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUserDetailService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String msa_id) throws UsernameNotFoundException {
		//실제 인증 처리는 생성된 Jwt 토큰의 유효성 검증으로 갈음한다.
		return AuthUserDetailVO.builder()
				.msa_id(msa_id)
				.build();
	}
}
