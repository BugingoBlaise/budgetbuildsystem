package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Data
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
    private List<String> reviews=new ArrayList<>();
    private Date date;
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;
}
