package models;

public class User {
    String username;
    String password;
    String email;
    String nickname;
    int age;

    public User(String username, String password, String email, String nickname, int age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }


    @Override
    public String toString() {
        return this.username + " " this.password;
    }
}
