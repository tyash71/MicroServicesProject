package com.lcwy.hotel.services.impl;

import com.lcwy.hotel.entities.Hotel;
import com.lcwy.hotel.exceptions.ResouceNotFoundException;
import com.lcwy.hotel.repositories.HotelRepository;
import com.lcwy.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(()->new ResouceNotFoundException("hotel with given id not found"));
    }
}
