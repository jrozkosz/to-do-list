package com.example.todolist.controllers;

import java.sql.Date;
import java.time.LocalDate;

public class Task implements Comparable<Task> {
    private String description;
    private LocalDate deadline;

    public Task(String description, LocalDate deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public String toString(){
        return description + " -> " + deadline.toString();
    }

    public String getDescription() { return description; }

    public LocalDate getDeadline() { return deadline; }

    @Override
    public int compareTo(Task otherTask) {
        return Date.valueOf(this.getDeadline()).compareTo(Date.valueOf(otherTask.getDeadline()));
    }
}
