package javapost;

import javapost.models.User;

public class CurrentUser {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User aUser) {
        user = aUser;
    }
}
