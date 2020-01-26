package hu.uni.eszterhazy.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.uni.eszterhazy.dao.TaskDAO;
import hu.uni.eszterhazy.exceptions.DateNotOK;
import hu.uni.eszterhazy.model.Task;
import hu.uni.eszterhazy.service.exceptions.TaskAlreadyExists;
import hu.uni.eszterhazy.service.exceptions.TaskNotFound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TaskDAOJSON implements TaskDAO {
    File jsonfile;
    ObjectMapper mapper;

    public TaskDAOJSON(String filepath) throws IOException {
        jsonfile = new File(filepath);
        if (!jsonfile.exists()) {
            jsonfile.createNewFile();
            FileWriter writer = new FileWriter(jsonfile);
            writer.write("[]");
            writer.close();
        }
        if (jsonfile.getTotalSpace() <= 0) {
            FileWriter writer = new FileWriter(jsonfile);
            writer.write("[]");
            writer.close();
        }
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public Collection<Task> readTasks() throws IOException {
        Collection<Task> result;
        TypeReference type = new TypeReference<ArrayList<Task>>() {
        };
        result = mapper.readValue(jsonfile, type);
        return result;
    }

    public Task readTaskById(String id) throws IOException, TaskNotFound {
        Collection<Task> tasks = readTasks();
        for (Task t : tasks) {
            if(t.getId().equals(id)) {
                return t;
            }
        }
        throw new TaskNotFound(id);
    }

    public void createTask(Task task) throws IOException, TaskAlreadyExists {
        Collection<Task> tasks = readTasks();
        try {
            readTaskById(task.getId());
            throw new TaskAlreadyExists(task.getId());
        } catch (TaskNotFound taskNotFound) {
            tasks.add(task);
            mapper.writeValue(jsonfile, tasks);
        }

    }

    public void updateTask(Task task) throws IOException, TaskNotFound {
        Collection<Task> tasks = readTasks();
        Task t = readTaskById(task.getId());
        List<Task> tasklist = new ArrayList<Task>(tasks);
        int index = tasklist.indexOf(t);
        tasklist.set(index, task);
        mapper.writeValue(jsonfile,tasklist);

    }

    public void removeTask(Task task) throws IOException, TaskNotFound {
        Collection<Task> tasks = readTasks();
        Task t = readTaskById(task.getId());

        tasks.remove(t);
        mapper.writeValue(jsonfile,tasks);
    }
}
