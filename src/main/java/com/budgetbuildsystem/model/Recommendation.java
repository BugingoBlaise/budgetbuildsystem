package com.budgetbuildsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reviews_recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int likeCount;
    private int rating;
    @ElementCollection
    @CollectionTable(name = "review_tags", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> reviews = new ArrayList<>();
    private Date date;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;
}
