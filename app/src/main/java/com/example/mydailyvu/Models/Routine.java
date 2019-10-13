package com.example.mydailyvu.Models;

public class Routine {

    public String time,sub,teacher,routine,room,order;

    public Routine() {
    }

    public Routine(String time, String sub, String teacher, String routine, String room, String order) {
        this.time = time;
        this.sub = sub;
        this.teacher = teacher;
        this.routine = routine;
        this.room = room;
        this.order = order;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
