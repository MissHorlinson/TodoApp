package com.example.todoapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "listtodo")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Long id;

    @Column(name = "list_title")
    private String title;

    @OneToMany (mappedBy = "list", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TodoItem> items = new ArrayList<>();

    public TodoList(String title, List<TodoItem> todoItemList) {
        this.title = title;
        this.items = todoItemList;
    }

    public void addItem(TodoItem item) {
        items.add(item);
    }
}
