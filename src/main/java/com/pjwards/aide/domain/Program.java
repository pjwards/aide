package com.pjwards.aide.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Program {

    public static final int MAX_LENGTH_TITLE = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_TITLE)
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

    public Program() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public Date getEnd() {
        return end;
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

    public ProgramDate getDate() {
        return date;
    }

    public Room getRoom() {
        return room;
    }

    public Conference getConference() {
        return conference;
    }

    public void update(Program updated) {
        this.title = updated.title;
        this.description = updated.description;
        this.begin = updated.begin;
        this.end = updated.end;
    }

    public static class Builder {
        private Program built;

        public Builder(String title, String description, Date begin, Date end) {
            built = new Program();
            built.title = title;
            built.description = description;
            built.begin = begin;
            built.end = end;
        }

        public Program build() {
            return built;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public Builder conference(Conference conference) {
            built.conference = conference;
            return this;
        }

        public Builder room(Room room) {
            built.room = room;
            return this;
        }

        public Builder date(ProgramDate date) {
            built.date = date;
            return this;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
