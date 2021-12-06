package com.example.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itemtodo")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_index")
    private int index;

    @Column(name = "text")
    private String text;

    @Column(name = "checked")
    private Boolean checked = false;

    @ManyToOne(fetch = FetchType.LAZY) //, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    @JsonIgnore
    private TodoList list;

    public TodoItem(int index, String text) {
        this.text = text;
        this.index = index;
    }


    public String msgForBot(String action) {
        return "Item " + action +
                " -> index: " + index +
                ", text: " + text +
                ", checked: " + checked +
                ", list: " + list.getTitle();
    }
}
