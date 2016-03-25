package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
