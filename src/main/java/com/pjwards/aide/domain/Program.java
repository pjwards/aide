package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjwards.aide.exception.WrongInputDateException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Program {

    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
    private Date begin;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
    private Date end;

    @ManyToOne
    @JoinColumn(name = "program_date_id")
    private ProgramDate date;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "conference_id")
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

    public void update(String title, String description, Date begin, Date end) {
        this.title = title;
        this.description = description;
        this.begin = begin;
        this.end = end;
    }

    public void dateChecker() throws WrongInputDateException {
        this.dateChecker(this.begin, this.end);
    }


    public void dateChecker(Date begin, Date end) throws WrongInputDateException {
        if (begin == null) {
            throw new WrongInputDateException("Input wrong date, begin is empty.");
        } else if (end == null) {
            throw new WrongInputDateException("Input wrong date, end is empty.");
        } else if (begin.getTime() > end.getTime()) {
            throw new WrongInputDateException(String.format("Input wrong dates begin: %s,end: %s", begin, end));
        }
        LOGGER.debug("Input wrong dates begin: {}, end: {}", begin, end);
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
