package com.example.todoapp.repo;

import com.example.todoapp.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    TodoItem findByIndexAndListId(int index, Long listId);

    TodoItem findTodoItemById(Long id);

    List<TodoItem> findByListId(Long id);
}
