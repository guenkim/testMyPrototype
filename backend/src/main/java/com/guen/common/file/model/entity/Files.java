package com.guen.common.file.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guen.common.model.entity.BaseDate;
import com.guen.program.todo.model.entity.Todo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Files extends BaseDate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="filename", nullable = false, length = 100)
    private String fileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", referencedColumnName = "id")
    @JsonBackReference
    private Todo todo;

    @Builder
    public Files(String fileName,Todo todo) {
        this.fileName = fileName;
        this.todo = todo;
    }
}