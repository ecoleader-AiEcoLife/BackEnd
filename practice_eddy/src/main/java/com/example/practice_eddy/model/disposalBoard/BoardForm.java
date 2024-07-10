package com.example.practice_eddy.model.disposalBoard;

import jakarta.validation.constraints.NotBlank;

public class BoardForm {
    @NotBlank
    private final String itemName;
    @NotBlank
    private final String content;

    public BoardForm(String itemName, String content) {
        this.itemName = itemName;
        this.content = content;
    }

    public String getItemName() {
        return itemName;
    }

    public String getContent() {
        return content;
    }
}
