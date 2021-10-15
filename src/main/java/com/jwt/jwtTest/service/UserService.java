package com.jwt.jwtTest.service;

import com.jwt.jwtTest.domain.Role;
import com.jwt.jwtTest.domain.Userr;

import java.util.List;

public interface UserService {
    Userr saveUser(Userr user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Userr getUser(String username);
    List<Userr> getUsers();
}
