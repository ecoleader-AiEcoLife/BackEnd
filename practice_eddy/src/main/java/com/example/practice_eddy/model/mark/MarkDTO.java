package com.example.practice_eddy.model.mark;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "마커 DTO")
public final class MarkDTO {

    @Schema(description = "마커 ID", example = "1")
    private final Long id;

    @Schema(description = "마커 제목", example = "서울타워")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1자 이상 15자 이하여야 합니다.")
    private final String title;

    @Schema(description = "위도", example = "37.551176")
    @NotNull(message = "위도는 필수입니다.")
    @DecimalMin(value = "-90.0", message = "위도는 -90.0 이상이어야 합니다.")
    @DecimalMax(value = "90.0", message = "위도는 90.0 이하여야 합니다.")
    private final Float lat;

    @Schema(description = "경도", example = "126.988449")
    @NotNull(message = "경도는 필수입니다.")
    @DecimalMin(value = "-180.0", message = "경도는 -180.0 이상이어야 합니다.")
    @DecimalMax(value = "180.0", message = "경도는 180.0 이하여야 합니다.")
    private final Float lng;

    @Schema(description = "위치 설명", example = "서울특별시 용산구 용산2가동")
    @Size(max = 100, message = "위치 설명은 100자 이하여야 합니다.")
    private final String location;

    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
    @Size(max = 255, message = "이미지 URL은 255자 이하여야 합니다.")
    private final String imgUrl;

    public MarkDTO(Long id, String title, Float lat, Float lng, String location, String imgUrl) {
        this.id = id;
        this.title = title;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
        this.imgUrl = imgUrl;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLng() {
        return lng;
    }

    public String getLocation() {
        return location;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Mark toEntity() {
        return new Mark(id, title, lat, lng, location, imgUrl);
    }
}