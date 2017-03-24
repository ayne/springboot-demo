package com.example.controller;

import com.example.data.User;
import com.example.dto.request.ChangePassword;
import com.example.dto.request.SetPassword;
import com.example.service.UserService;
import com.example.util.ErrorExceptions;
import com.example.util.InvalidRequestException;
import io.jsonwebtoken.Claims;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by charmanesantiago on 28/02/2016.
 */

@RestController
@RequestMapping("/users") //this will be the path of our controller
public class UserController extends ErrorExceptions {

    private String coreEndpoint = "baseUrl"; //dummy endpoint only

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    //@Valid used to tell spring to validate that User attributes follow annotated rules on each User's attribute
    public Object createUser(@RequestBody @Valid User user, final HttpServletRequest request) throws ServiceException {
        if(getUserId(request) != null){
            return userService.createUser(getUserId(request), user);
        }
        else{
            throw new InvalidRequestException();
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "/forgot_password") //this will be appended to the base path of our controller (/user/forgot_password)
    public Object forgotPassword(@RequestParam(value = "email") String email) throws ServiceException {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/set_password")
    public Object setPassword(@RequestBody @Valid SetPassword setPassword) throws ServiceException {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change_password")
    public Object changePassword(@RequestBody @Valid ChangePassword changePassword,
                                 final HttpServletRequest request) throws Exception {
        String xuserId = getUserId(request);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Object getUserById(@PathVariable("id") String id, final HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        if(getUserId(request) !=null ){
            response.put("result",userService.getUser(Integer.parseInt(id)));
        }
        return  response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getAllUsers(Pageable p, final HttpServletRequest request) {
        //TODO add aith before allowing to view all users
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Object updateUserById(@RequestBody @Valid User user,
                                 final HttpServletRequest request) throws ServiceException {
        final Claims claims = (Claims) request.getAttribute("claims");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        String xUserId = claims.getId();
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Object deleteUserById(@PathVariable("id") String id, final HttpServletRequest request) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        if(getUserId(request) !=null ){
            response.put("result", userService.deleteUser(Integer.parseInt(id)));
        }
        return response;
    }

    //get the user id sent from the request using claims
    //Before the request reaches any controller, it will first go to our CustomAuthenticationFilter
    //which adds the claims to the request
    private String getUserId(HttpServletRequest request){
        final Claims claims = (Claims) request.getAttribute("claims");
        if(claims == null) return null;
        return claims.getId();
    }
}
