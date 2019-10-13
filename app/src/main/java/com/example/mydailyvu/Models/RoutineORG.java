package com.example.mydailyvu.Models;

public class RoutineORG {

    public String time,sub,teacher,routine,room,sem,sec,ap;

    public RoutineORG() {
    }

    public RoutineORG(String time, String sub, String teacher, String routine, String room, String sem, String sec, String ap) {
        this.time = time;
        this.sub = sub;
        this.teacher = teacher;
        this.routine = routine;
        this.room = room;
        this.sem = sem;
        this.sec = sec;
        this.ap = ap;
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

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }
}
