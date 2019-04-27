package dou.ding.nyat.blog.model;

import java.io.Serializable;

public abstract class CommonModel<PrimaryKeyType extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected PrimaryKeyType id;

    public PrimaryKeyType getId() {
        return id;
    }

    public void setId(PrimaryKeyType id) {
        this.id = id;
    }
}
