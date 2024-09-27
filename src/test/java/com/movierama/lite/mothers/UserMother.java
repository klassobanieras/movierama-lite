package com.movierama.lite.mothers;

import com.movierama.lite.user.User;

public class UserMother {
    public static User createDefaultUser() {
        return new User("default", "password", "default@email.com");
    }
}
