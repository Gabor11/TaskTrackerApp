package hu.uni.eszterhazy.service.impl;

//import com.sun.xml.internal.ws.api.pipe.TubelineAssembler;
import hu.uni.eszterhazy.dao.TaskDAO;
import hu.uni.eszterhazy.exceptions.DateNotOK;
import hu.uni.eszterhazy.exceptions.NotMatchingID;
import hu.uni.eszterhazy.model.Task;
import hu.uni.eszterhazy.service.TaskService;
import hu.uni.eszterhazy.service.exceptions.InvalidRange;
import hu.uni.eszterhazy.service.exceptions.TaskAlreadyExists;
import hu.uni.eszterhazy.service.exceptions.TaskNotFound;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TaskServiceImpl implements TaskService {
    TaskDAO dao;

    public TaskServiceImpl(TaskDAO dao) {
        this.dao = dao;
    }

    public Collection<Task> listAllTasks() throws IOException {
        return dao.readTasks();
    }

    public Task getTaskByID(String id) throws TaskNotFound, IOException {
        return dao.readTaskById(id);
    }

    public void addTask(Task task) throws TaskAlreadyExists, IOException {
        dao.createTask(task);
    }

    public void addTasks(Collection<Task> tasks) throws TaskAlreadyExists, IOException, DateNotOK {
        for (Task task : tasks) {
            dao.createTask(task);
        }
    }

    public void updateTask(Task task) throws TaskNotFound, IOException {
        dao.updateTask(task);
    }

    public void deleteTask(String taskId) throws TaskNotFound, IOException, NotMatchingID, DateNotOK {
        Task task = new Task(taskId);
        dao.removeTask(task);
    }

    public Collection<Task> getTasksBetweenStartDates(LocalDate fromStartDate, LocalDate toStartDate) throws IOException, InvalidRange {
        if(toStartDate.isBefore(fromStartDate)){
            throw new InvalidRange(fromStartDate+"-"+toStartDate);
        }
        Collection<Task> tasks = listAllTasks();
        Collection<Task> result = new ArrayList<Task>();
        for (Task t : tasks) {
            if((t.getDueDate().isAfter(fromStartDate)
                    || t.getDueDate().isEqual(fromStartDate))
                    && (t.getDueDate().isBefore(toStartDate)
                    || t.getDueDate().isEqual(toStartDate))){
                result.add(t);
            }
        }
        return result;
    }

    public Collection<Task> getTasksBetweenDueDates(LocalDate fromDueDate, LocalDate toDueDate) throws IOException, InvalidRange {
        if(toDueDate.isBefore(fromDueDate)){
            throw new InvalidRange(fromDueDate + "-" + toDueDate);
        }
        Collection<Task> tasks = listAllTasks();
        Collection<Task> result = new ArrayList<Task>();
        for (Task t : tasks) {
            if((t.getDueDate().isAfter(fromDueDate)
                    || t.getDueDate().isEqual(fromDueDate))
                    && (t.getDueDate().isBefore(toDueDate)
                    || t.getDueDate().isEqual(toDueDate))){
                result.add(t);
            }
        }
        return result;
    }
}