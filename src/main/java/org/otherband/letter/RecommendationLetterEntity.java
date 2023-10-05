package org.otherband.letter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recommendation_letter")
public class RecommendationLetterEntity {
    @Id
    private String id;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    private String authorEmail;
    @Column(nullable = false)
    private boolean validated;
}
