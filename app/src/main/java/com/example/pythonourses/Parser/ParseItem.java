package com.example.pythonourses.Parser;

import java.io.Serializable;

public class ParseItem  implements Serializable {
    private String number, title, lectureUrl;

    public ParseItem(){
    }

    public ParseItem(String number, String title) {
        this.number = number;
        this.title = title;
    }

    public ParseItem(String number, String title, String lectureUrl) {
        this.number = number;
        this.title = title;
        this.lectureUrl = lectureUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLectureUrl() {
        return lectureUrl;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }
}
