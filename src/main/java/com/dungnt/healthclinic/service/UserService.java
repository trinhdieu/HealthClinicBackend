package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.Role;
import com.dungnt.healthclinic.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    Optional<User> findById(Long id) throws Exception;

    void save(User user);

    List<User> findAllByRoom(String room) throws Exception;

//    List<User> findAllByRoles(Set<Role> roles);
}
