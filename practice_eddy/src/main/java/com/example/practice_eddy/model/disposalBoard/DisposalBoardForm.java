package com.example.practice_eddy.model.disposalBoard;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DisposalBoardForm {

    @Schema(description = "게시글 제목", example = "폐기물 이름")
    @NotNull(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1자 이상 15자 이하여야 합니다.")
    private final String title;

    @Schema(description = "게시글 내용", example = "폐기물 처리에 대한 상세 안내")
    @NotNull(message = "내용은 필수입니다.")
    @Size(min = 1, max = 100, message = "내용은 1자 이상 100자 이하여야 합니다.")
    private final String content;

    @Schema(description = "게시글 부내용", example = "기타 정보")
    @NotNull(message = "부내용은 필수입니다.")
    @Size(min = 1, max = 50, message = "부내용은 1자 이상 50자 이하여야 합니다.")
    private final String subContent;

    @Schema(description = "폐기물 타입 ID")
    @NotNull(message = "폐기물 타입 ID는 필수입니다.")
    private final Long typeId;

    public DisposalBoardForm(String title, String content, String subContent, Long typeId) {
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.typeId = typeId;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSubContent() {
        return subContent;
    }

    public Long getTypeId() {
        return typeId;
    }
}