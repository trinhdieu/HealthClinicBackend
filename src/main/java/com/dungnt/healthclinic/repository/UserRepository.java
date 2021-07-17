package com.dungnt.healthclinic.repository;

import com.dungnt.healthclinic.model.Role;
import com.dungnt.healthclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
//    User findAllByPrivilege(Integer privilege);
    List<User> findAllByRoom(String room);
//    List<User> findAllByRoles(Set<Role> roles);

}
