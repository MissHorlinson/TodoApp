package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoListDTO;
import com.example.todoapp.model.TodoList;
import com.example.todoapp.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todolist")
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("/addList")
    public TodoList addNewList(@RequestBody TodoListDTO todoListDTO) { return todoListService.addList(todoListDTO); }

    @PutMapping("/list/{listId}/edit")
    public TodoList editList(@PathVariable(name = "listId") Long listId,
                             @RequestBody TodoListDTO todoListDTO) {
        return todoListService.editList(listId, todoListDTO);
    }

    @GetMapping("/getLists")
    public List<TodoList> getList() { return todoListService.getTodoListsCollection();}

    @DeleteMapping("/list/{listId}/delete")
    public void deleteList(@PathVariable(name = "listId") Long listId) {
        todoListService.delete(listId);
    }
}
