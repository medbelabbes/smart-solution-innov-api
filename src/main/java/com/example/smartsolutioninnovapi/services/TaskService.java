package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.Task;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.utils.responses.Response;

public interface TaskService {

    Response addTask(Task task, User connectedAdmin);

    Response updateTask(Task task, User connectedAdmin);

    Response deleteTask(Task task, User connectedAdmin);

    Response getTaskById(long id);

    Response getMyTasks(User connectedAdmin);

    Response getTasksByUser(long id);

}
