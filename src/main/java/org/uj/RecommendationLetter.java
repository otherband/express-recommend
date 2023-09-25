package org.uj;

import lombok.Data;

@Data
public class RecommendationLetter {
    private String id;
    private String title;
    private String body;
    private String author;
    private boolean validated;
}
