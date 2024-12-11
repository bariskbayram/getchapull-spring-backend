package com.bkb.getchapull.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Review")
@Table(
        name = "reviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_profile_id", "album_id"})
)
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
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            length = 100
    )
    private String title;

    @Column(
            name = "content",
            nullable = false,
            length = 1000
    )
    private String content;

    @Column(
            name = "point",
            nullable = false
    )
    private int point;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP"
    )
    private OffsetDateTime createdAt;

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

    public Review(String title, String content, int point) {
        this.title = title;
        this.content = content;
        this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Review review = (Review) obj;
        return Objects.equals(id, review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
