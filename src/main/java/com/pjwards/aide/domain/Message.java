package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob()
    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonManagedReference
    private User sender;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    private Room room;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    public Message() {
    }

    public void update(Message message) {
        this.message = message.message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public User getSender() {
        return sender;
    }

    public Message setSender(User sender) {
        this.sender = sender;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Message setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Message setDate(Date date) {
        this.date = date;
        return this;
    }

    public static class Builder {
        private Message built;

        public Builder(String message, User sender, Room room) {
            built = new Message();
            built.message = message;
            built.sender = sender;
            built.room = room;
            built.date = new Date();
        }

        public Message build() {
            return built;
        }
    }
}
