package com.example.practice_eddy.model;

public class DisposalBoard {

    private Long id;
    private String itemName;
    private String content;

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getContent() {
        return content;
    }

    public DisposalBoard(Long id, String itemName, String content) {
        this.id = id;
        this.itemName = itemName;
        this.content = content;
    }
}
