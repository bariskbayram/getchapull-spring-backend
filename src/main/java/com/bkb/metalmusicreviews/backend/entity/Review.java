package com.bkb.metalmusicreviews.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Review")
@Table(name = "reviews")
public class Review {

    @Id
    @SequenceGenerator(
            name = "review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_sequence"
    )
    @Column(
            name = "review_id",
            updatable = false
    )
    private int reviewId;

    @Column(
            name = "review_title",
            nullable = false,
            length = 100
    )
    private String reviewTitle;

    @Column(
            name = "review_content",
            nullable = false,
            length = 1000
    )
    private String reviewContent;

    @Column(
            name = "review_point",
            nullable = false
    )
    private int reviewPoint;

    @Column(
            name = "posting_date",
            nullable = false,
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime postingDate;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private Album album;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private UserProfile userProfile;

    public Review(String reviewTitle, String reviewContent, int reviewPoint) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewPoint = reviewPoint;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Review review = (Review) obj;
        return Objects.equals(reviewId, review.getReviewId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }

}
