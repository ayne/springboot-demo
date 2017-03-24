package com.example.service;

import com.example.data.User;
import com.example.dto.request.ChangePassword;
import com.example.repository.UserRepository;
import com.example.util.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by charmanesantiago on 23/03/2017.
 */

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String encode(String rawPassword){
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    public boolean matches(String newPassword, String oldPassword){
        return new BCryptPasswordEncoder().matches(newPassword, oldPassword);
    }

    public Map<String, Object> createUser(String xUserId, User user) throws ServiceException {

        Map<String, Object> response = new LinkedHashMap<>();

        if (user.getPassword() != null) {
            user.setPassword(encode(user.getPassword()));
        }

        try {
            userRepository.save(user);
            log.info("Created user and saved: " + user);
        } catch (Exception e) {
            Throwable rootCause = e.getCause();
            log.error("Error creating user ", rootCause);
        }
        return response;
    }

    public Map<String, Object> getAllUsers(){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("result", userRepository.findAll());
        return response;
    }

    public Map<String, Object> changePassword(String xUserId, ChangePassword changePassword){
        if(xUserId == null){
            throw new InvalidRequestException();
        }
        User user = userRepository.findOne(Integer.parseInt(xUserId));
        if(user != null) {
            //check if the request has the correct current password of the user
            if(matches(changePassword.getOldPassword(), user.getPassword())){
                String encodedPassword = encode(changePassword.getNewPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("result", true);
                return result;
            }
            else{
                throw new InvalidRequestException();
            }

        }
        else{
            throw new InvalidRequestException();
        }
    }

    public List<User> getUsersByEmail(String email){
        return userRepository.findAllByEmail(email);
    }

    public List<User> getUsersByEmailAndName(String email, String name){
        return userRepository.findAllByEmailAndName(email, name);
    }

    public Map<String, Object>getUser(Integer id){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("result", userRepository.findOne(id));
        return response;
    }

    public boolean deleteUser(Integer id){
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new InvalidRequestException();
        } else {
            userRepository.delete(user);
            return true;
        }
    }
}
