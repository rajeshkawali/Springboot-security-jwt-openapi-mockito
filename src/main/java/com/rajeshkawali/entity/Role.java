package com.rajeshkawali.entity;

import static com.rajeshkawali.entity.Permission.ADMIN_CREATE;
import static com.rajeshkawali.entity.Permission.ADMIN_DELETE;
import static com.rajeshkawali.entity.Permission.ADMIN_READ;
import static com.rajeshkawali.entity.Permission.ADMIN_UPDATE;
import static com.rajeshkawali.entity.Permission.USER_READ;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Rajesh_Kawali
 * 
 */
@RequiredArgsConstructor
public enum Role {

	USER(Set.of(USER_READ)), 
	ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE));

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
}
