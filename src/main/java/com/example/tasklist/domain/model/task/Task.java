package com.example.tasklist.domain.model.task;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter @Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String title;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;



}
