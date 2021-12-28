package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Task;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.services.TaskService;
import com.example.smartsolutioninnovapi.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Override
    public Response addTask(Task task, User connectedAdmin) {
        return null;
    }

    @Override
    public Response updateTask(Task task, User connectedAdmin) {
        return null;
    }

    @Override
    public Response deleteTask(Task task, User connectedAdmin) {
        return null;
    }

    @Override
    public Response getTaskById(long id) {
        return null;
    }

    @Override
    public Response getMyTasks(User connectedAdmin) {
        return null;
    }

    @Override
    public Response getTasksByUser(long id) {
        return null;
    }
}
