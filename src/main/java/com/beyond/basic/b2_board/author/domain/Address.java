package com.beyond.basic.b2_board.author.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String zipCode;

//    원투원일때는 unique설정
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", unique = true, foreignKey=@ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Author author;







}
