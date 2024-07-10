package com.example.practice_eddy.model.disposalBoard;

import java.time.LocalDateTime;

public record DisposalBoardDTO(Long id, String itemName, String content, LocalDateTime createDate,
                               LocalDateTime modifiedDate) {

    public DisposalBoardDTO(String itemName, String content) {
        this(null, itemName, content, null, null);
    }

    public DisposalBoard toEntity() {
        return new DisposalBoard(id, itemName, content, createDate, modifiedDate);
    }
}