package model;

import java.time.LocalDate;

public abstract class Task {

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    protected String title;
    protected LocalDate deadline;
    protected Status status = Status.PENDING;

    public Task(String title) {
        this.title = title;
    }

    public Task(String title, LocalDate deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public abstract int calculatePriority();

    public String getTitle() {
        return title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}