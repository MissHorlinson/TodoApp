package com.example.todoapp.controller;


import com.example.todoapp.bot.MessageLogBot;
import com.example.todoapp.dto.TodoItemDTO;
import com.example.todoapp.model.TodoItem;
import com.example.todoapp.service.TodoItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/todoitems")
public class TodoItemController {

    private final TodoItemService todoItemService;
    private final MessageLogBot messageLogBot;

    @Value("${bot.chartId}")
    private String chartId;

    @Autowired
    public TodoItemController(TodoItemService todoItemService, MessageLogBot messageLogBot) {
        this.todoItemService = todoItemService;
        this.messageLogBot = messageLogBot;
    }

    @PostMapping("/list/{listId}/addItem")
    public TodoItem save(@PathVariable(name = "listId") Long listId,
                         @RequestBody TodoItemDTO todoItemDTO) {

        TodoItem item = todoItemService.save(listId, todoItemDTO);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chartId);
        sendMessage.setText(item.msgForBot("created"));

        try{
            messageLogBot.execute(sendMessage);
        } catch (TelegramApiException ex) {}
        return item;
    }

    @GetMapping("/getList")
    public List<TodoItem> getItemByListId(@RequestParam(name = "listId") Long listId) { return todoItemService.getByListId(listId); }

    @GetMapping("/getItem")
    public TodoItem getItemById(@RequestParam(name = "itemId") Long itemId) { return todoItemService.getById(itemId); }


    @GetMapping("/getList/{listId}/getIndex/{index}")
    public TodoItem getItemByIndex(@PathVariable(name = "listId") Long listId,
                                   @PathVariable(name = "index") int index) {
        return  todoItemService.getByIndex(index, listId); }

    @GetMapping("/get")
    public List<TodoItem> getItems() { return todoItemService.getItemsCollection(); }

    @PutMapping("/list/{listId}/item/{index}/edit")
    public TodoItem updateItem(@PathVariable(name = "listId") Long listId,
                               @PathVariable(name = "index") int index,
                               @RequestBody TodoItemDTO todoItemDTO) {

        TodoItem item = todoItemService.editItem(listId, index, todoItemDTO);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chartId);
        sendMessage.setText(item.msgForBot("updated"));

        try{
            messageLogBot.execute(sendMessage);
        } catch (TelegramApiException ex) {}

        return item;}

    @PutMapping("/list/{listId}/item/{index}/check")
    public TodoItem isChecked(@PathVariable(name = "listId") Long listId,
                              @PathVariable(name = "index") int index) {

        TodoItem item = todoItemService.setChecked(listId, index);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chartId);
        String mode = item.getChecked() ? "checked" : "unchecked";
        sendMessage.setText(item.msgForBot("was " + mode));

        try{
            messageLogBot.execute(sendMessage);
        } catch (TelegramApiException ex) {}

        return item; }

    @DeleteMapping("/list/{listId}/item/{index}/delete")
    public void deleteItem(@PathVariable(name = "listId") Long listId,
                           @PathVariable(name = "index") int index) {
        todoItemService.delete(listId, index);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chartId);
        sendMessage.setText("Item with index " + index + " in list " + listId + " was deleted");

        try{
            messageLogBot.execute(sendMessage);
        } catch (TelegramApiException ex) {}
    }
}
