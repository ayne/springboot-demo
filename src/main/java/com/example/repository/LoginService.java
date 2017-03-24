package com.example.repository;

import com.example.data.User;
import com.example.dto.request.Login;
import com.example.util.InvalidRequestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by charmanesantiago on 23/03/2017.
 */
@Service
@Slf4j
public class LoginService {

    @Value("${demo.cors.secret.key}")
    private String secretKey;
    @Autowired
    UserRepository userRepository;



    public boolean matches(String newPassword, String oldPassword){
        return new BCryptPasswordEncoder().matches(newPassword, oldPassword);
    }


    public Map<String, Object> login(@RequestBody @Valid Login login) throws ServiceException {

        log.info(login.getEmail() + " attempting to login");

        User user = userRepository.findOneByEmail(login.getEmail());
        log.info("Retrieving user");
        //if user password is null, user is preactivated and needs to set password first.
        if (user == null || user.getPassword() == null) {
            log.warn("Invalid credentials");
            throw new InvalidRequestException();
        }

        if(!(matches(login.getPassword(), user.getPassword()))) {
            log.warn("Wrong password");
            throw new InvalidRequestException();
        }
        log.info("User successfully logged in");
        String token = Jwts.builder().setSubject(user.getName())
                .setId(user.getId().toString()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (3600000 * 2)))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("token", token);
        response.put("user", user);
        return response;
    }
}
