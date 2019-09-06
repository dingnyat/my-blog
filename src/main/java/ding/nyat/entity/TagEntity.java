package ding.nyat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true, length = 16)
    private String code;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<PostEntity> posts;
}
