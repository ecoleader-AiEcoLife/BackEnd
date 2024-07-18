package com.example.practice_eddy.model.disposalBoard;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "폐기물 타입 DTO")
public record TypeDTO(
    @Schema(description = "타입 ID", example = "1")
    Long id,

    @Schema(description = "타입 이름", example = "일반쓰레기")
    @NotBlank(message = "타입 이름은 필수입니다.")
    @Size(min = 1, max = 15, message = "타입 이름은 1자 이상 15자 이하여야 합니다.")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s{}()\\[\\]_\\-/,]+$", message = "타입 이름에는 문자, 숫자, 공백 및 일부 특수문자({})[]_-/,만 사용할 수 있습니다.")
    String name,

    @Schema(description = "타입 이미지 URL", example = "http://example.com/images/general_waste.png")
    String imgUrl
) {

    public TypeDTO(String name, String imgUrl) {
        this(null, name, imgUrl);
    }

    public Type toEntity() {
        return new Type(id, name, imgUrl);
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String imgUrl() {
        return imgUrl;
    }


}