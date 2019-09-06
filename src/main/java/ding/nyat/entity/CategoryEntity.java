package ding.nyat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", length = 65536)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "category_series",
            joinColumns = {@JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_category_series"))},
            inverseJoinColumns = {@JoinColumn(name = "series_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_series_series"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id", "series_id"})})
    private Set<SeriesEntity> series;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_category_tag"))
    private Set<TagEntity> tags;
}
