package com.example.todoapp.service;

import com.example.todoapp.dto.TodoListDTO;
import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.model.TodoItem;
import com.example.todoapp.model.TodoList;
import com.example.todoapp.repo.TodoItemRepository;
import com.example.todoapp.repo.TodoListRepository;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public TodoList addList(TodoListDTO todoListDTO) {
        List<TodoItem> todoItemList = new ArrayList<>();

        if(todoListDTO.getTodoItemList() != null) {
            todoListDTO.getTodoItemList().forEach(todoItem -> {
                todoItemList.add(new TodoItem(todoItem.getIndex(), todoItem.getText()));
            });
        }

        TodoList todoList = new TodoList(todoListDTO.getTitle(), todoItemList);
        todoList = todoListRepository.save(todoList);
        return  todoList;
    }

    public TodoList editList(Long id, TodoListDTO todoListDTO) {
        return todoListRepository.findById(id).map(list -> {
            list.setTitle(todoListDTO.getTitle());
            return todoListRepository.save(list);
        }).orElseThrow(() -> new ResourceNotFoundException("ListId " + id + " not found"));
    }

    public List<TodoList> getTodoListsCollection() {
        return todoListRepository.findAll();
    }

    public void delete(Long id) {
        todoListRepository.findById(id).map(list -> {
            todoListRepository.delete(list);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ListId " + id + " not found"));
    }
}
