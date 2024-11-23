package com.khactuong.hotel.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.khactuong.hotel.model.entity.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Query("SELECT rt FROM RoomType rt WHERE rt.name = :name")
    RoomType findByName(String name);

    @Query("SELECT rt.name FROM RoomType rt")
    List<String> findAllRoomTypes();

    boolean existsByName(String roomType);
}
