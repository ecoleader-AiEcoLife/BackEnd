package com.example.practice_eddy.model.disposalBoard;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Disposal board DTO")
public record DisposalBoardDTO(
    Long id,

    String title,

    String content,

    String subContent,

    Long typeId
) {

    public DisposalBoardDTO(String title, String content, String subContent, Long typeId) {
        this(null, title, content, subContent, typeId);
    }

    public DisposalBoardDTO(DisposalBoardForm form) {
        this(null, form.getTitle(), form.getContent(), form.getSubContent(), form.getTypeId());
    }
}