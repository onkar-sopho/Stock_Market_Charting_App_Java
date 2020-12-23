package com.onkar.stockMarket.controller;

import com.onkar.stockMarket.model.Company;
import com.onkar.stockMarket.model.User;
import com.onkar.stockMarket.service.CompanyService;
import com.onkar.stockMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @GetMapping("/all")
    public List<User> findAllUser() {
        return service.findAllUser();
    }

    @GetMapping("/find/{id}")
    public User findUserById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }
}
