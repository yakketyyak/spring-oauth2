package com.yakketyyak.oauth2.config;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * The Class UserDetailsServiceImpl.
 */
@Component
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	/** The user repository. */
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = this.userRepository.findByUsername(username);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrincipal(user);
	}
}
