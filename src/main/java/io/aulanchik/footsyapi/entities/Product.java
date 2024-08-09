package io.aulanchik.footsyapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    private String name;
    private String code;
    private Double price;
    private Integer quantity;
    private Boolean isNew = true;
    private String gender;

    @Lob
    private String description;

    @Temporal(TemporalType.DATE)
    private Date productionDate = new Date();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Color> colors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<File> files = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Detail> details = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Size> sizes = new ArrayList<>();


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
