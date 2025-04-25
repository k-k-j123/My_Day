package com.example.Kaushik.My.Day.Service;

import com.example.Kaushik.My.Day.Model.Users;
import com.example.Kaushik.My.Day.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Users findUser(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        System.out.println("in service "+ userId);
        return userRepository.findById(userId);

    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    public Boolean updateUser(Users updatedUser, HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Users tempUser = userRepository.findById(userId);
        if (tempUser != null) {
            tempUser.setEmail(updatedUser.getEmail());
            tempUser.setPassword(updatedUser.getPassword());
            userRepository.save(tempUser);
            return true;
        }
        return false;
    }

    public Boolean deleteUser(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Users tempUser = userRepository.findById(userId);
        if (tempUser != null) {
            userRepository.delete(tempUser);
            return true;
        }
        return false;
    }
}
