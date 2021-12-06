package com.example.todoapp.dto;

import com.example.todoapp.model.TodoList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItemDTO {
    private Long id;
    private String text;
    private Integer index;
    private Boolean checked = false;

    public TodoItemDTO() { }

    public TodoItemDTO(int index, String text) {
        this.index = index;
        this.text = text;
    }


    @Override
    public String toString() {
        return "ItemDTO {" +
                "id: " + id +
                ", index: " + index +
                ", text: " + text +
                ", checked: " + checked;
    }
}
