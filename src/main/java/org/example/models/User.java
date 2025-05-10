package org.example.models;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;

@Entity("users")
public class User {
    @Id
    private ObjectId _id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Gender gender;
    private int age;
    private int numberOfGames;
    private int HighestScore;
//    private Game currentGame =  new Game();
//    private ArrayList<Game> games;
    private String question;
    private String answer;
    private boolean stayLoggedIn;
    @Reference
    private Game currentGame;

    public User() {
    }

    public User( String username, String password, String email, String nickname, Gender gender, String question, String answer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.age = 0;
        this.numberOfGames = 0;
        this.HighestScore = 0;
        this.gender = gender;
        this.question = question;
        this.answer = answer;
        this.stayLoggedIn = false;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getHighestScore() {
        return HighestScore;
    }

    public void setHighestScore(int highestScore) {
        HighestScore = highestScore;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public ObjectId get_id() {
        return _id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }


    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", numberOfGames=" + numberOfGames +
                ", HighestScore=" + HighestScore +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", stayLoggedIn=" + stayLoggedIn +
                '}';
    }
}
