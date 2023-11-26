package com.lcwy.user.service.servies.impl;

import com.lcwy.user.service.entities.Hotel;
import com.lcwy.user.service.entities.Rating;
import com.lcwy.user.service.entities.User;
import com.lcwy.user.service.exceptions.ResourceNotFoundException;
import com.lcwy.user.service.repositories.UserRepository;
import com.lcwy.user.service.servies.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from database with help of repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " is not found on server."));
        //fetch rating of above user from RATING SERVICE
        //http://localhost:8083/ratings/users/07d1cb09-3fb0-4429-b983-1e95529e86e9
        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}",ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList =  ratings.stream().map(rating -> {
            //api call to hotel service to get rating
            // http://localhost:8082/hotels/d970ce4f-9e35-418b-b628-c68bb4841b0e
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code: {} ",forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }
}
