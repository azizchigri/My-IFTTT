package com.area.server.components.user.controller;

import javax.validation.Valid;

import com.area.server.components.user.model.ApplicationUser;
import com.area.server.components.user.repository.ApplicationUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Instantiates a new User controller.
     *
     * @param applicationUserRepository the application user repository
     * @param bCryptPasswordEncoder     the b crypt password encoder
     */
    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Sign up response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid ApplicationUser user) {
    	if (applicationUserRepository.findByUsername(user.getUsername()) != null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    /**
     * Update response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ApplicationUser user) {
        ApplicationUser entity = applicationUserRepository.findByUsername(user.getUsername());
        if (entity == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User doesn't exist");
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entity.setRedditToken(user.getRedditToken());
        entity.setGmailToken(user.getGmailToken());
        entity.setSpotifyToken(user.getSpotifyToken());
        entity.setTwitchToken(user.getTwitchToken());
        entity.setYoutubeToken(user.getYoutubeToken());
        entity.setSubServices(user.getSubServices());
        applicationUserRepository.save(entity);
        entity.setPassword("");
        return ResponseEntity.ok(entity);
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    @GetMapping("")
    public ResponseEntity<Object> getUser(@RequestParam("username") String username) {
    	ApplicationUser entity = applicationUserRepository.findByUsername(username);
    	if (entity == null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User doesn't exist");
    	entity.setPassword("");
        return ResponseEntity.ok(entity);
    }
}