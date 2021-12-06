package com.example.todoapp.service;

import com.example.todoapp.bot.MessageLogBot;
import com.example.todoapp.dto.TodoItemDTO;
import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.model.TodoItem;
import com.example.todoapp.repo.TodoItemRepository;
import com.example.todoapp.repo.TodoListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@Slf4j
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoItemService(TodoItemRepository todoItemRepository, TodoListRepository todoListRepository) {
        this.todoItemRepository = todoItemRepository;
        this.todoListRepository = todoListRepository;
    }


    public List<TodoItem> getByListId(Long id) {
        return todoItemRepository.findByListId(id);
    }

    public TodoItem getById(Long id) {
        return todoItemRepository.findTodoItemById(id);
    }

    public TodoItem getByIndex(int index, Long listId) { return todoItemRepository.findByIndexAndListId(index, listId); }

    public List<TodoItem> getItemsCollection() {
        return todoItemRepository.findAll();
    }


    public TodoItem save(Long id, TodoItemDTO todoItemDTO) {

        return todoListRepository.findById(id).map(list -> {
               TodoItem todoItem = new TodoItem(todoItemDTO.getIndex(), todoItemDTO.getText());
               todoItem.setList(list);
               return todoItemRepository.save(todoItem);
        }).orElseThrow(() -> new ResourceNotFoundException("ListId " + id + " not found"));
    }

    public TodoItem editItem(Long listId, int index, TodoItemDTO todoItemDTO) {

        if(!todoListRepository.existsById(listId)) {
            throw new ResourceNotFoundException("ListId " + listId + " not found");
        }

        TodoItem todoItem = todoItemRepository.findByIndexAndListId(index, listId);
        todoItem.setText(todoItemDTO.getText());
        todoItem.setChecked(todoItemDTO.getChecked());
        todoItem = todoItemRepository.save(todoItem);
        return todoItem;
    }

    public  TodoItem setChecked(Long id, int index) {
        if(!todoListRepository.existsById(id)) {
            throw new ResourceNotFoundException("ListId " + id + " not found");
        }

        TodoItem todoItem = todoItemRepository.findByIndexAndListId(index, id);
        todoItem.setChecked(!todoItem.getChecked());
        todoItem = todoItemRepository.save(todoItem);
        return todoItem;
    }

    public void delete(Long id, int index) {
        if(!todoListRepository.existsById(id)) {
            throw new ResourceNotFoundException("ListId " + id + " not found");
        }

        TodoItem todoItem = todoItemRepository.findByIndexAndListId(index, id);
        todoItemRepository.delete(todoItem);
    }

}
