package com.br.readnow.api.model;

public enum UserRoleModel {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRoleModel(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
