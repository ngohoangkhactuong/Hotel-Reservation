package com.khactuong.hotel.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.khactuong.hotel.model.repository.RoomTypeRepository;
import com.khactuong.hotel.service.IRoomTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements IRoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public List<String> getAllRoomTypes() {
        return roomTypeRepository.findAllRoomTypes();
    }

}
