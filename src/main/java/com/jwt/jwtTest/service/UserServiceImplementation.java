package com.jwt.jwtTest.service;

import com.jwt.jwtTest.domain.Role;
import com.jwt.jwtTest.domain.Userr;
import com.jwt.jwtTest.repo.RoleRepo;
import com.jwt.jwtTest.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public Userr saveUser(Userr user) {
        log.info("Saving the new user {} to the database", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving the new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Userr user = userRepo.findByusername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Userr getUser(String username) {
        log.info("Fetching user: {}", username);
        return userRepo.findByusername(username);
    }

    @Override
    public List<Userr> getUsers() {
        log.info("Fetching all the users");
        return userRepo.findAll();
    }
}