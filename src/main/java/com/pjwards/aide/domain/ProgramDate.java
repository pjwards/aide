package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class ProgramDate {

    public static final String DAY_FORMAT = "yyyy-MM-dd";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssz")
    private Date day;

    @OneToMany(
            targetEntity = Program.class,
            mappedBy = "date",
            fetch = FetchType.EAGER
    )
    private List<Program> programList;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    public ProgramDate() {
    }

    public ProgramDate(Date day) {
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public Date getDay() {
        return day;
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

    public List<Program> getProgramList() {
        return programList;
    }

    public Conference getConference() {
        return conference;
    }

    public void update(ProgramDate updated) {
        this.day = updated.day;
    }

    public void update(Date day) {
        this.day = truncateDate(day);
    }

    public static class Builder {
        private ProgramDate built;

        public Builder(Date day) {
            built = new ProgramDate();
            built.day = built.truncateDate(day);
        }

        public Builder(String day) throws ParseException {
            built = new ProgramDate();
            built.setFormattedDay(day);
        }

        public Builder(String day, String format) throws ParseException {
            built = new ProgramDate();
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
