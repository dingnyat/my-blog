package dou.ding.nyat.blog.model;

import java.util.Set;

public class Category extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
    private Set<Series> seriesSet;
    private Set<Tag> tags;

    public Category() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Series> getSeriesSet() {
        return seriesSet;
    }

    public void setSeriesSet(Set<Series> seriesSet) {
        this.seriesSet = seriesSet;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
