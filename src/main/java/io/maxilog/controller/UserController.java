package io.maxilog.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.maxilog.entity.User;
import io.maxilog.security.*;
import io.maxilog.service.NotificationService;
import io.maxilog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by mossa on 06/11/2017.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Value("${jwt.header}")
    private String tokenHeader;


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) {
        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        }catch (AuthenticationException e){

            return ResponseEntity.badRequest().body(new ErrorMessageResponse("Error Login","Please check your email or password"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails, device);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody User user) throws AuthenticationException {
        userService.save(user);
        return ResponseEntity.ok(new MessageResponse("please verify your email"));
    }

    @RequestMapping(value = "verify/{key}", method = RequestMethod.GET)
    public ResponseEntity<?> activationAccount(@PathVariable(value = "key")String key, Device device){
        User user = userService.activation(key);
        if(user != null){
            JwtUser jwtUser = JwtUserFactory.create(user);
            final String token = jwtTokenUtil.generateToken((UserDetails) jwtUser,device);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            return new ResponseEntity<>(jwtUser, headers, HttpStatus.OK);
        }else{
            return ResponseEntity.status(404).body(new MessageResponse("Token doesn't exist"));
        }
    }

    @RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(@RequestBody ObjectNode data, Device device) throws MessagingException {
        User user = userService.findByEmail(data.get("email").asText());
        if(user != null){
            String passwordToken = jwtTokenUtil.generatePasswordToken(JwtUserFactory.create(user),device);
            notificationService.sendResetPasswordEmail(user,passwordToken);
            return ResponseEntity.ok(new MessageResponse("Please check your email"));
        }else{
            return ResponseEntity.status(404).body(new MessageResponse("Email doesn't exist"));
        }
    }

    @RequestMapping(value = "resetpassword")
    public ResponseEntity<?> resetPassword(@RequestParam(value = "key") String key, @RequestBody ObjectNode data){
        System.out.println(key);
        User user = userService.findByEmail(jwtTokenUtil.getUsernameFromPasswordToken(key));
        System.out.println(key);
        if(user != null){
            if(jwtTokenUtil.validatePasswordToken(key,JwtUserFactory.create(user))){
                user.setPassword(data.get("password").asText());
                user.setLastPasswordResetDate(new Date());
                userService.update(user);
                return ResponseEntity.ok(new MessageResponse("Password Updated"));
            }else {
                return ResponseEntity.status(404).body(new MessageResponse("Token expired"));
            }
        }else{
            return ResponseEntity.status(404).body(new MessageResponse("Token doesn't exist"));
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "me", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(Authentication authentication) {
        /*String token = request.getHeader(tokenHeader).substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(email);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
        Object principal = authentication.getPrincipal();
        return (JwtUser)principal;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAuthenticatedUser(@RequestBody User user,Authentication authentication, Device device) {
        Object principal = authentication.getPrincipal();
        JwtUser jwtUser = (JwtUser)principal;
        User u = userService.findByEmail(jwtUser.getEmail());
        System.out.println(u.getEmail());
        if(!user.getFirstname().equals("")){
            u.setFirstname(user.getFirstname());
        }
        if(!user.getLastname().equals("")){
            u.setLastname(user.getLastname());
        }
        userService.update(u.getId(),u);
        final String token = jwtTokenUtil.generateToken(jwtUser, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "password", method = RequestMethod.PATCH)
    public ResponseEntity<?> updatePassword(@RequestBody User user,Authentication authentication, Device device) {
        Object principal = authentication.getPrincipal();
        JwtUser jwtUser = (JwtUser)principal;
        User u = userService.findByEmail(jwtUser.getEmail());
        System.out.println(u.getEmail());
        if(!user.getPassword().equals("")){
            System.out.println(user.getPassword());
            u.setPassword(user.getPassword());
            u.setLastPasswordResetDate(new Date());
            userService.update(u.getId(),u);
            final String token = jwtTokenUtil.generateToken(jwtUser, device);

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }else {
            return ResponseEntity.badRequest().body("");
        }

    }

}
