package com.example.practice_eddy.model.disposalBoard;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Disposal board DTO")
public record DisposalBoardDTO(
    @Schema(description = "게시글 ID", example = "1")
    Long id,

    @Schema(description = "게시글 제목", example = "페기물 이름")
    String title,

    @Schema(description = "게시글 내용", example = "폐기물 처리에 대한 상세 안내")
    String content,

    @Schema(description = "게시글 부내용", example = "기타 정보")
    String subContent,

    @Schema(description = "폐기물 타입")
    TypeDTO type
) {

    public DisposalBoardDTO(String title, String content, String subContent, TypeDTO type) {
        this(null, title, content, subContent, type);
    }

    public DisposalBoard toEntity() {
        return new DisposalBoard(id, title, content, subContent, type.toEntity());
    }
}