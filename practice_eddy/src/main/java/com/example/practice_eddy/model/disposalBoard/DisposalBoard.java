package com.example.practice_eddy.model.disposalBoard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    public DisposalBoard() {
    }

    public DisposalBoard(Long id, String title, String content, String subContent, Type type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.type = type;
    }

    public void update(String title, String content, String subContent, Type type) {
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public DisposalBoardDTO toDisposalBoardDTO() {
        return new DisposalBoardDTO(
            this.id,
            this.title,
            this.content,
            this.subContent,
            this.type.getId()  // Type 객체 대신 typeId를 전달
        );
    }
}