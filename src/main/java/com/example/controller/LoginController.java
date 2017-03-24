package com.example.controller;

import com.example.dto.request.Login;
import com.example.repository.LoginService;
import com.example.util.ErrorExceptions;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by charmanesantiago on 28/02/2016.
 */

@RestController
@RequestMapping("/login")
public class LoginController extends ErrorExceptions {

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public Object login(@RequestBody @Valid Login login) throws ServiceException {
        return loginService.login(login);
    }

}
