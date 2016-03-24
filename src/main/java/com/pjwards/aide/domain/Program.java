package com.pjwards.aide.domain;

import java.sql.Time;

public class Program {
    private String title;
    private String description;
    private Time begin;
    private Time end;
    private ProgramDate date;

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

    public Time getBegin() {
        return begin;
    }

    public Program setBegin(Time begin) {
        this.begin = begin;
        return this;
    }

    public Time getEnd() {
        return end;
    }

    public Program setEnd(Time end) {
        this.end = end;
        return this;
    }

    public ProgramDate getDate() {
        return date;
    }

    public void setDate(ProgramDate date) {
        this.date = date;
    }
}
