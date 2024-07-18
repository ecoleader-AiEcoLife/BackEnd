package com.example.practice_eddy.model.disposalBoard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String imgUrl;

    public Type() {
    }

    public Type(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public TypeDTO toDTO() {
        return new TypeDTO(id, name, imgUrl);
    }

    public void update(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}