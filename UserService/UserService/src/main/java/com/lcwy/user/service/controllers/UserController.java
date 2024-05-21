package com.lcwy.user.service.controllers;

import com.lcwy.user.service.entities.User;
import com.lcwy.user.service.servies.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        var userResponse = userService.saveUser(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    int retryCount = 1;

    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("getSingleUser Controller");
        logger.info("Retry count: " + retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //Creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
//        logger.info("Fallback is executed because service is down", ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com").
                userName("dummy")
                .about("dummy created because some service is down")
                .userId("14123")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
