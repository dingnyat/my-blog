package ding.nyat.annotation;

import ding.nyat.model.Account;

public class Test {
    public static void main(String[] args) {
        Account account = new Account();
        account.setId(12);
        System.out.println(Account.class.getAnnotation(Identifier.class).annotationType());
    }
}
