package org.uj.letter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RecommendationLetter {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;
    @Column(name = "body", nullable = false)
    private String body;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "validated", nullable = false)
    private boolean validated;
}
