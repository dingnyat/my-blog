package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", length = 65536)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_series",
            joinColumns = {@JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_category_series"))},
            inverseJoinColumns = {@JoinColumn(name = "series_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category_series_series"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id", "series_id"})})
    private Set<SeriesEntity> series;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<TagEntity> tags;

    public CategoryEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SeriesEntity> getSeries() {
        return series;
    }

    public void setSeries(Set<SeriesEntity> series) {
        this.series = series;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
