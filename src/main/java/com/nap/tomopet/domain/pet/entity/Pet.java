package com.nap.tomopet.domain.pet.entity;

import com.nap.tomopet.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type", nullable = false)
    private PetType petType; // DOG, CAT

    @Column(name = "breed", nullable = false, length = 50)
    private String breed;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "weight", nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender; // MALE, FEMALE

    @Column(name = "is_neutered", nullable = false)
    private Boolean isNeutered;

    @Column(name = "activity_level", nullable = false, precision = 3, scale = 2)
    private BigDecimal activityLevel;

    @Column(name = "rer", nullable = false)
    private Integer rer;

    @Column(name = "mer", nullable = false)
    private Integer mer;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // RER/MER 계산 메서드
    public void calculateCaloricNeeds() {
        double weightKg = this.weight.doubleValue();
        double rerValue = 70 * Math.pow(weightKg, 0.75);
        this.rer = (int) Math.round(rerValue);
        this.mer = (int) Math.round(rerValue * this.activityLevel.doubleValue());
    }
}