package hu.uni.eszterhazy.service.exceptions;

public class TaskNotFound extends Exception {
    public TaskNotFound(String id) { super(id); }
}
