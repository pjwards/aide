package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class ProgramDate {

    public static final int MAX_LENGTH_NAME = 10;
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String DAY_SCHEDULE_FORMAT = "M.d EEE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
    private Date day;

    @OneToMany(
            targetEntity = Program.class,
            mappedBy = "date",
            fetch = FetchType.EAGER
    )
//    @JsonIgnore
    @JsonBackReference
    private Set<Program> programs;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonManagedReference
    private Conference conference;

    public ProgramDate() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProgramDate setName(String name) {
        this.name = name;
        return this;
    }

    public Date getDay() {
        return day;
    }

    public ProgramDate setDay(Date day) {
        this.day = day;
        return this;
    }

    public List<Program> getPrograms() {
        if (programs == null) return null;
        List<Program> list = new ArrayList<>(programs);
        Collections.sort(list, new ProgramCompare());
        return list;
    }

    class ProgramCompare implements Comparator<Program> {
        @Override
        public int compare(Program arg0, Program arg1) {
            LocalTime begin0 = new LocalTime(arg0.getBegin());
            LocalTime begin1 = new LocalTime(arg1.getBegin());
            return begin0.compareTo(begin1);
        }
    }

    public ProgramDate setPrograms(Set<Program> programs) {
        this.programs = programs;
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
        return this.getFormattedDay(DAY_FORMAT);
    }

    /**
     * Get formatted schedule Day
     *
     * @return formatted schedule day
     */
    public String getFormattedScheduleDay() {
        return this.getFormattedDay(DAY_SCHEDULE_FORMAT);
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
        return this.setFormattedDay(day, DAY_FORMAT);
    }

    /**
     * Set day by given day and format
     *
     * @param day    given day
     * @param format given format
     * @return this
     * @throws ParseException
     */
    public ProgramDate setFormattedDay(String day, String format) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(format);
        this.day = this.truncateDate(formatter.parse(day));
        return this;
    }

    public Date truncateDate(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public void update(ProgramDate updated) {
        this.name = updated.name;
        this.day = updated.day;
    }

    public static class Builder {
        private ProgramDate built;

        public Builder(String name, Date day) {
            built = new ProgramDate();
            built.name = name;
            built.day = built.truncateDate(day);
        }

        public Builder(String name, String day) throws ParseException {
            built = new ProgramDate();
            built.name = name;
            built.setFormattedDay(day);
        }

        public Builder(String name, String day, String format) throws ParseException {
            built = new ProgramDate();
            built.name = name;
            built.setFormattedDay(day, format);
        }

        public ProgramDate build() {
            return built;
        }

        public Builder conference(Conference conference) {
            built.conference = conference;
            return this;
        }
    }

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }*/
}
