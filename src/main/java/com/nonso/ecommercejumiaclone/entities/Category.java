package com.nonso.ecommercejumiaclone.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String categoryName;
    @Column
    private String description;
    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    @JsonBackReference
    private List<Product> productList = new ArrayList<>();
}
