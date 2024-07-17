package com.example.practice_eddy.model.mark;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "마커 DTO")
public final class MarkDTO {
    @Schema(description = "마커 ID", example = "1")
    private final Long id;

    @Schema(description = "마커 제목", example = "서울타워")
    private final String title;

    @Schema(description = "위도", example = "37.551176")
    private final Float lat;

    @Schema(description = "경도", example = "126.988449")
    private final Float lng;

    @Schema(description = "위치 설명", example = "서울특별시 용산구 용산2가동")
    private final String location;

    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
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