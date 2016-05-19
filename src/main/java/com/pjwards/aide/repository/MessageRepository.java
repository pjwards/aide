package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Message;
import com.pjwards.aide.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoom(Room room);

}
