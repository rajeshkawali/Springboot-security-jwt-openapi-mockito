package com.rajeshkawali.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Rajesh_Kawali
 * 
 */
@RequiredArgsConstructor
public enum Permission {

	USER("user"),
	USER_READ("user:read"),
	ADMIN("admin"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");

    @Getter
    private final String permission;
}
