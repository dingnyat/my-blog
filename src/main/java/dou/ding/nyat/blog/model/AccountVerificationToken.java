package dou.ding.nyat.blog.model;

import java.util.Date;

public class AccountVerificationToken extends CommonModel<Long> {

    private String token;
    private Account account;
    private Date time;

    public AccountVerificationToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
