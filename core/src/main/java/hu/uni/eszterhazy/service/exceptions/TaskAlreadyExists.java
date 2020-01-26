package hu.uni.eszterhazy.service.exceptions;

public class TaskAlreadyExists extends Exception {
    public TaskAlreadyExists(String id) { super(id); }
}
