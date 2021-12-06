package com.example.todoapp.dto;

import com.example.todoapp.model.TodoItem;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TodoListDTO {
    private Long id;
    private String title;
    private List<TodoItem> todoItemList;

    public TodoListDTO() { }

    public TodoListDTO(String title, List<TodoItem> todoItemList) {
        this.title = title;
        this.todoItemList = todoItemList;
    }

}
