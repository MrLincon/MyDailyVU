package com.example.mydailyvu.Models;

import java.util.List;

public class Routine {

    private String startTime,endTime,subject,department,teacher,routine,room,orderHour,orderMinute,am_pm;
    private List<String> teachers;

    public Routine() {
    }

    public Routine(String startTime, String endTime, String subject, String department, String teacher, String routine, String room, String orderHour, String orderMinute, String am_pm, List<String> teachers) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.department = department;
        this.teacher = teacher;
        this.routine = routine;
        this.room = room;
        this.orderHour = orderHour;
        this.orderMinute = orderMinute;
        this.am_pm = am_pm;
        this.teachers = teachers;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOrderHour() {
        return orderHour;
    }

    public void setOrderHour(String orderHour) {
        this.orderHour = orderHour;
    }

    public String getOrderMinute() {
        return orderMinute;
    }

    public void setOrderMinute(String orderMinute) {
        this.orderMinute = orderMinute;
    }

    public String getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(String am_pm) {
        this.am_pm = am_pm;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }
}
