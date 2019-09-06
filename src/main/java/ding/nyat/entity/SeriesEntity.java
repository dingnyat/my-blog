package ding.nyat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "series")
public class SeriesEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "description", nullable = false, length = 65536)
    private String description;

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    private Set<PostEntity> posts;

    @ManyToMany(mappedBy = "series", fetch = FetchType.LAZY)
    private Set<CategoryEntity> categories;
}
