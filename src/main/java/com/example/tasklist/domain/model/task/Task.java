package com.example.tasklist.domain.model.task;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter @Setter
@NoArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String title;

    private LocalDateTime expirationDate;

    @Column(name = "image")
    @CollectionTable(name = "tasks_images")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;





}
