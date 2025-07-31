package com.CEliconValley.common;

import com.CEliconValley.models.Gender;
import com.CEliconValley.models.User;
import com.google.gson.Gson;

public class UserData {
    String username;
    String password;
    String email;
    String nickname;
    Gender gender;
    int age;
    int numberOfGames;
    int HighestScore;
    String question;
    String answer;
    boolean stayLoggedIn;
    public UserData(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.numberOfGames = user.getNumberOfGames();
        this.HighestScore = user.getHighestScore();
        this.question = user.getQuestion();
        this.answer = user.getAnswer();
        this.stayLoggedIn = user.isStayLoggedIn();
    }

    public int getAge() {
        return age;
    }

    public String getAnswer() {
        return answer;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public int getHighestScore() {
        return HighestScore;
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public String getPassword() {
        return password;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
