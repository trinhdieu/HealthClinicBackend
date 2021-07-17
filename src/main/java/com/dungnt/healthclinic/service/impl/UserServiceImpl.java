package com.dungnt.healthclinic.service.impl;

import com.dungnt.healthclinic.model.MyUserDetails;
import com.dungnt.healthclinic.model.Role;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.repository.UserRepository;
import com.dungnt.healthclinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) throws Exception {
        if (id == null) {
            throw new Exception("Gia tri id null");
        }
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAllByRoom(String room) throws Exception {
        if (room == null || room == "") {
            throw new Exception("Gia tri room null");
        }
        return userRepository.findAllByRoom(room);
    }

//    @Override
//    public List<User> findAllByRoles(Set<Role> roles) {
//        return userRepository.findAllByRoles(roles);
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            try {
                throw new Exception("Gia tri username null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Khong the tim ra username " + username);
        }
        return new MyUserDetails(user);
    }

    public UserDetails loadUserById(Long id) throws Exception {
        if (id == null) {
            throw new Exception("Gia tri id null");
        }
        Optional<User> user = userRepository.findById(id);
        if (user.get() == null) {
            throw new Exception("Khong tim ra user co id = " + id);
        }
        return new MyUserDetails(user.get());
    }
}
