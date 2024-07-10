package com.example.practice_eddy.model.disposalBoard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class DisposalBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String itemName;
    @NotBlank
    private String content;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    public DisposalBoard() {
    }

    public DisposalBoard(Long id, String itemName, String content, LocalDateTime createDate,
        LocalDateTime modifiedDate) {
        this.id = id;
        this.itemName = itemName;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public DisposalBoardDTO toDisposalBoardDTO() {
        return new DisposalBoardDTO(this.id, this.itemName, this.content, this.createDate,
            this.modifiedDate);
    }

}