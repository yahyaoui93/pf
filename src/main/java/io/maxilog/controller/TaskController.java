package io.maxilog.controller;

import io.maxilog.entity.Task;
import io.maxilog.entity.User;
import io.maxilog.errorHandler.notFoundException;
import io.maxilog.security.JwtUser;
import io.maxilog.security.UserFactory;
import io.maxilog.service.TaskService;
import io.maxilog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    //@PreAuthorize("isAuthenticated()")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value="", method = RequestMethod.GET)
    public List<Task> getTasks(Authentication authentication){
        return taskService.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable(value = "id") int id) throws notFoundException{
        Task task = taskService.findById(id);
        if(task == null){
            throw new notFoundException("Task not found!");
        }
        return task;
    }

    //@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public int createTask(@RequestBody Task task, Authentication authentication){
        Object principal = authentication.getPrincipal();
        User user = UserFactory.create((JwtUser)authentication.getPrincipal());
        return taskService.save(task);
    }

   // @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateTask(@PathVariable(value = "id") int id,@RequestBody Task task){
        taskService.update(id,task);
        return id;
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    //@PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public int deleteTask(@PathVariable(value = "id") int id){
        taskService.delete(id);
        return id;
    }

    /*@ExceptionHandler(notFoundException.class)
    public void notFoundHandler(notFoundException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }*/
}
