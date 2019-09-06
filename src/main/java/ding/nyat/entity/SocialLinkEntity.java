package ding.nyat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "social_link")
public class SocialLinkEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "link", nullable = false, length = 1024)
    private String link;
}
