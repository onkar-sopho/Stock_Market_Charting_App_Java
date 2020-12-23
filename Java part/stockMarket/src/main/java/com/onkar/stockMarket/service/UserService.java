package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.UserRepository;
import com.onkar.stockMarket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public User addUser(User user) {
        return repo.save(user);
    }

    public List<User> findAllUser() {
        return repo.findAll();
    }

    public User findUserById(long id) {
        return repo.findById(id).orElse(null);
    }

    public String deleteUser(long id) {
        repo.deleteById(id);
        return "User with id " + id + " deleted successfully";
    }

    public User updateUser(User user) {
        User existingUser = this.findUserById(user.getUserId());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobNo(user.getMobNo());
        existingUser.setUserType(user.getUserType());
        existingUser.setConfirmed(user.getConfirmed());
        return repo.save(existingUser);
    }
}
