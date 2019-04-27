package dou.ding.nyat.blog.model;

public class Tag extends CommonModel<Integer> {
    private String code;
    private String name;

    public Tag() {
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
}
