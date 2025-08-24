package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoWebController {

    private final TodoService todoService;

    public TodoWebController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Show all todos
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listTodos", todoService.getAllTodos());
        model.addAttribute("todo", new Todo()); // for form binding
        return "index";
    }

    // Save new todo
    @PostMapping("/saveTodo")
    public String saveTodo(@ModelAttribute("todo") Todo todo) {
        todoService.createTodo(todo);
        return "redirect:/";
    }

    // Update todo (mark complete/incomplete)
    @GetMapping("/updateTodo/{id}")
    public String updateTodo(@PathVariable("id") Long id) {
        Todo todo = todoService.getTodoById(id);
        todo.setCompleted(!todo.isCompleted()); // toggle status
        todoService.updateTodo(id, todo);
        return "redirect:/";
    }

    // Delete todo
    @GetMapping("/deleteTodo/{id}")
    public String deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return "redirect:/";
    }
}
