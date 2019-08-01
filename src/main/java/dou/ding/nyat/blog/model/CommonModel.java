package dou.ding.nyat.blog.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class CommonModel<PrimaryKeyType extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected PrimaryKeyType id;
}
