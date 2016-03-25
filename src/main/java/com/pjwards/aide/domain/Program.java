package com.pjwards.aide.domain;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob()
    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date begin;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date end;

    @ManyToOne
    @JoinColumn(name = "program_date_id")
    private ProgramDate date;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    public Long getId() {
        return id;
    }

    public Program setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Program setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Program setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getBegin() {
        return begin;
    }

    public Program setBegin(Date begin) {
        this.begin = begin;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public Program setEnd(Date end) {
        this.end = end;
        return this;
    }

    public ProgramDate getDate() {
        return date;
    }

    public Program setDate(ProgramDate date) {
        this.date = date;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Program setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Conference getConference() {
        return conference;
    }

    public Program setConference(Conference conference) {
        this.conference = conference;
        return this;
    }

    /**
     * Get begin time
     *
     * @return begin time
     */
    public Time getBeginTime() {
        return new Time(this.getBegin().getTime());
    }

    /**
     * Get end time
     *
     * @return end time
     */
    public Time getEndTime() {
        return new Time(this.getEnd().getTime());
    }
}
