package hu.uni.eszterhazy.service;

import hu.uni.eszterhazy.exceptions.DateNotOK;
import hu.uni.eszterhazy.exceptions.NotMatchingID;
import hu.uni.eszterhazy.model.Task;
import hu.uni.eszterhazy.service.exceptions.InvalidRange;
import hu.uni.eszterhazy.service.exceptions.TaskAlreadyExists;
import hu.uni.eszterhazy.service.exceptions.TaskNotFound;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

public interface TaskService {
    Collection<Task> listAllTasks() throws IOException, DateNotOK;

    Task getTaskByID(String id) throws TaskNotFound, IOException, DateNotOK;

    void addTask(Task task) throws TaskAlreadyExists, IOException, DateNotOK;
    void addTasks(Collection<Task> tasks) throws TaskAlreadyExists, IOException, DateNotOK;
    void updateTask(Task task) throws TaskNotFound, IOException, DateNotOK;

    void deleteTask(String taskId) throws TaskNotFound, IOException, DateNotOK, NotMatchingID;

    Collection<Task> getTasksBetweenStartDates(LocalDate fromStartDate, LocalDate toStartDate) throws IOException, InvalidRange;

    Collection<Task> getTasksBetweenDueDates(LocalDate fromDueDate, LocalDate toDueDate) throws IOException, InvalidRange;
}