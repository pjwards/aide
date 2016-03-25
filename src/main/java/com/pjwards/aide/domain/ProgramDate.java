package com.pjwards.aide.domain;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class ProgramDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date day;

    @OneToMany(
            targetEntity = Program.class,
            mappedBy = "date",
            fetch = FetchType.EAGER
    )
    private List<Program> programList;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    public Long getId() {
        return id;
    }

    public ProgramDate setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getDay() {
        return day;
    }

    public ProgramDate setDay(Date day) {
        this.day = day;
        return this;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public ProgramDate setProgramList(List<Program> programList) {
        this.programList = programList;
        return this;
    }

    public Conference getConference() {
        return conference;
    }

    public ProgramDate setConference(Conference conference) {
        this.conference = conference;
        return this;
    }

    /**
     * Get formatted Day
     *
     * @return formatted day
     */
    public String getFormattedDay() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.getDay());
    }

    /**
     * Get formatted Day by given format
     *
     * @param format given format
     * @return formatted day
     */
    public String getFormattedDay(String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(this.getDay());
    }

    /**
     * Set day by given day
     *
     * @param day given day
     * @return this
     * @throws ParseException
     */
    public ProgramDate setFormattedDay(String day) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.day = formatter.parse(day);
        return this;
    }

    /**
     * Set day by given day and format
     *
     * @param day given day
     * @param format given format
     * @return this
     * @throws ParseException
     */
    public ProgramDate setFormattedDay(String day, String format) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(format);
        this.day = formatter.parse(day);
        return this;
    }
}
