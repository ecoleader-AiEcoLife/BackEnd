package com.example.practice_eddy.model.mark;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "marks")
@Schema(description = "마커 엔티티")
@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "마커 ID", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "마커 제목", example = "서울타워")
    private String title;

    @Column(nullable = false)
    @Schema(description = "위도", example = "37.551176")
    private Float lat;

    @Column(nullable = false)
    @Schema(description = "경도", example = "126.988449")
    private Float lng;

    @Column(nullable = false)
    @Schema(description = "위치 설명", example = "서울특별시 용산구 용산2가동")
    private String location;

    @Column(name = "img_url")
    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
    private String imgUrl;

    public Mark() {
    }

    public Mark(Long id, String title, Float lat, Float lng, String location, String imgUrl) {
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


    public MarkDTO toDTO() {
        return new MarkDTO(id, title, lat, lng, location, imgUrl);
    }

    public void update(String title, Float lat, Float lng, String location, String imgUrl) {
        this.title = title;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
        this.imgUrl = imgUrl;
    }
}
