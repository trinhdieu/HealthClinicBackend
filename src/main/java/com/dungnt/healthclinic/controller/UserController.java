package com.dungnt.healthclinic.controller;

import com.dungnt.healthclinic.dto.SignUpRequest;
import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.model.Role;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.service.ClinicSerService;
import com.dungnt.healthclinic.service.RoleService;
import com.dungnt.healthclinic.service.UserService;
import com.dungnt.healthclinic.service.impl.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleService roleService;
    private ValidationService validationService;
    private ClinicSerService clinicSerService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService,
                          ValidationService validationService, ClinicSerService clinicSerService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.validationService = validationService;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) throws Exception {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id,
                                           @RequestBody User user) throws Exception {
        Optional<User> currentUser = userService.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        validationService.checkNullParameter(user);
        currentUser.get().setName(user.getName());
        currentUser.get().setDateOfBirth(user.getDateOfBirth());
        currentUser.get().setAddress(user.getAddress());
        currentUser.get().setEmail(user.getEmail());
        currentUser.get().setCountry(user.getCountry());
        currentUser.get().setGender(user.getGender());
        userService.save(currentUser.get());

        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }


    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        User user = new User();
        validationService.validateCredentials(signUpRequest);
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        Role role = roleService.findByName(signUpRequest.getRole());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
//        Optional<ClinicService> clinicService = clinicSerService.findById(signUpRequest.getServiceId());
//        if (!clinicService.isEmpty()) {
//            user.setRoom(clinicService.get().getRoom());
//        }
        if (signUpRequest.getRoom() != null) {
            user.setRoom(signUpRequest.getRoom());
        }
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getUserByRoom", method = RequestMethod.POST)
    public ResponseEntity<User> getUserByRoom(@RequestParam("room") String room) throws Exception {
        List<User> users = userService.findAllByRoom(room);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        User user = users.get(0);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUsersByRole", method = RequestMethod.POST)
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam("role") String role) throws Exception {
        List<User> users = userService.findAll();
        List<User> usersResponse = new ArrayList<>();
        for (User user: users) {
            Set<String> roleNames = user.getRoleNames();
            if (roleNames.size() == 1 && roleNames.contains(role)) {
                usersResponse.add(user);
            }
        }
        if (usersResponse.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersResponse, HttpStatus.OK);
    }

}

