package hu.uni.eszterhazy.controller;


import hu.uni.eszterhazy.exceptions.DateNotOK;
import hu.uni.eszterhazy.exceptions.NotMatchingID;
import hu.uni.eszterhazy.model.Task;
import hu.uni.eszterhazy.service.TaskService;
import hu.uni.eszterhazy.service.exceptions.InvalidRange;
import hu.uni.eszterhazy.service.exceptions.TaskAlreadyExists;
import hu.uni.eszterhazy.service.exceptions.TaskNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.GregorianCalendar;

@Controller
public class TaskController {
    TaskService service;

    public TaskController(@Autowired TaskService service) { this.service = service; }

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello() {
        return "Hello";
    }

    @RequestMapping(value = "/task/count")
    @ResponseBody
    public int getTaskNumber() throws IOException, DateNotOK {
        return service.listAllTasks().size();
    }

    @RequestMapping(value = "/task/list")
    @ResponseBody
    public Collection<Task> listTasks() throws IOException, DateNotOK {
        return service.listAllTasks();
    }

    @RequestMapping(value = "/task/add",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addTask(@RequestBody Task task) throws IOException, TaskAlreadyExists, DateNotOK {
        service.addTask(task);
        return "New Task added successfully: " + task.getCaption();
    }

    @RequestMapping(value = "/task/addmore",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addTasks(@RequestBody Collection<Task> task) throws IOException, TaskAlreadyExists, DateNotOK {
        service.addTasks(task);
        return "New Tasks added.";
    }

    @RequestMapping(value = "/task/{id:[0-9]{2}}")
    @ResponseBody
    public Task getTaskByID(@PathVariable String id) throws IOException, TaskNotFound, DateNotOK {
        return service.getTaskByID(id);
    }

    @RequestMapping(value = "/task/update",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateTask(@RequestBody Task task) throws IOException, TaskNotFound, DateNotOK {
        service.updateTask(task);
        return "Task update successful. ID: " + task.getId();
    }

    @RequestMapping(value = "/task/delete/{id:[0-9]{2}}")
    @ResponseBody
    public String deleteTaskByID(@PathVariable String id) throws IOException, TaskNotFound, DateNotOK, NotMatchingID {
        service.deleteTask(id);
        return "Task deleted successfully. ID: " + id;
    }

    @RequestMapping(value = "/task/list/startDates")
    @ResponseBody
    public Collection<Task> getTasksBetweenStartDates(
            @RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromStartDate,
            @RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toStartDate) throws IOException, InvalidRange {
        return service.getTasksBetweenStartDates(fromStartDate, toStartDate);
    }

    @RequestMapping(value = "/task/list/dueDates")
    @ResponseBody
    public Collection<Task> getTasksBetweenDueDates(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDueDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDueDate) throws IOException, InvalidRange {
        return service.getTasksBetweenDueDates(fromDueDate,toDueDate);
    }
}
