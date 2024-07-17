package com.example.practice_eddy.model.disposalBoard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DisposalBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String subContent;

    public DisposalBoard() {
    }

    public DisposalBoard(Long id, String title, String content, String subContent) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.subContent = subContent;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSubContent() {
        return subContent;
    }

    public DisposalBoardDTO toDisposalBoardDTO() {
        return new DisposalBoardDTO(this.id, this.title, this.content, this.subContent);
    }

}