package com.lcwy.rating.repository;

import com.lcwy.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating,String> {
}
