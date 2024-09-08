package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "reviews_recommendation")
public class ContractorRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int likeCount;
    private int rating;
    @ElementCollection
    @CollectionTable(name = "review_tags", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> reviews = new ArrayList<>();
    private Date date;
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Local_Contractor contractor;
}
