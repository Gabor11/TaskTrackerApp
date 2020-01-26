package hu.uni.eszterhazy.dao;

import hu.uni.eszterhazy.exceptions.DateNotOK;
import hu.uni.eszterhazy.model.Task;
import hu.uni.eszterhazy.service.exceptions.TaskAlreadyExists;
import hu.uni.eszterhazy.service.exceptions.TaskNotFound;

import java.io.IOException;
import java.util.Collection;

public interface TaskDAO {

    Collection<Task> readTasks() throws IOException;

    Task readTaskById(String id) throws IOException, TaskNotFound;

    void createTask(Task task) throws IOException, TaskAlreadyExists;

    void updateTask(Task task) throws IOException, TaskNotFound;

    void removeTask(Task task) throws IOException, TaskNotFound;
}

