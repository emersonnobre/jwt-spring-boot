package com.jwt.jwtTest.service;

import com.jwt.jwtTest.domain.Role;
import com.jwt.jwtTest.domain.Userr;
import com.jwt.jwtTest.repo.RoleRepo;
import com.jwt.jwtTest.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userr user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> { 
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public Userr saveUser(Userr user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Userr user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Userr getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<Userr> getUsers() {
        return userRepo.findAll();
    }

}