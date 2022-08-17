package com.klishch.diploma.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "scientific_work")
public class ScientificWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime publishDate;

    @NotNull
    @Column(nullable = false)
    private String filePath;

    @NotNull
    @Column(nullable = false)
    private boolean isPublished;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "work" , fetch = FetchType.EAGER)
    private Set<Review> reviews;

}
