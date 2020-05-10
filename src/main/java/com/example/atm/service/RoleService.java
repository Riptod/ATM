package com.example.atm.service;

import com.example.atm.entity.Role;

public interface RoleService {
    Role save(Role role);

    Role findRoleByRoleName(String roleName);
}
