package com.khactuong.hotel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.khactuong.hotel.common.enumeration.ERoomType;
import com.khactuong.hotel.exception.InternalServerException;
import com.khactuong.hotel.exception.ResourceNotFoundException;
import com.khactuong.hotel.model.entity.Room;
import com.khactuong.hotel.model.entity.RoomType;
import com.khactuong.hotel.model.repository.RoomRepository;
import com.khactuong.hotel.model.repository.RoomTypeRepository;
import com.khactuong.hotel.service.IRoomService;

import jakarta.annotation.PostConstruct;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    @PostConstruct
    public void init() {
        List<RoomType> roomTypes = new ArrayList<>();

        for (ERoomType eRoomType : ERoomType.values()) {
            if (!roomTypeRepository.existsByName(eRoomType.toString())) {

                RoomType roomType = new RoomType();
                roomType.setName(
                        eRoomType.toString());
                roomTypeRepository.save(roomType);
            }
        }

        if (!roomTypes.isEmpty()) {
            roomTypeRepository.saveAll(roomTypes);
        }
    }

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {

        // find room type by name
        RoomType roomTypeByName = roomTypeRepository.findByName(roomType);
        if (roomTypeByName == null) {
            throw new ResourceNotFoundException("Sorry, Room type not found!");
        }

        Room room = new Room();
        room.setRoomType(roomTypeByName);
        room.setRoomPrice(roomPrice);

        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if (theRoom.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, Room not found!");
        }
        Blob photoBlob = theRoom.get().getPhoto();
        if (photoBlob != null) {
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if (theRoom.isPresent()) {
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {

        RoomType roomTypeByName = roomTypeRepository.findByName(roomType);

        Room room = roomRepository.findById(roomId).get();
        if (roomType != null)
            room.setRoomType(roomTypeByName);
        if (roomPrice != null)
            room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServerException("Fail updating room");
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
    }
}
