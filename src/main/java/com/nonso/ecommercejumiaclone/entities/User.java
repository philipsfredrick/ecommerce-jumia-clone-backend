package com.nonso.ecommercejumiaclone.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonso.ecommercejumiaclone.entities.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "Missing required field name")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Missing required field email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull(message = "Missing required field password")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Missing avatar upload")
    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(targetEntity = WishList.class, cascade = CascadeType.ALL, mappedBy = "user")
    private List<WishList> wishList = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(targetEntity = Cart.class, cascade = CascadeType.ALL, mappedBy = "user")
    private Cart cart;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter(AccessLevel.NONE)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
