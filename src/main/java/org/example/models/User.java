package org.example.models;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Gender gender;
    private final int id;
    private int age;
    private int numberOfGames;
    private int HighestScore;
    private Game currentGame;
    private ArrayList<Game> games;

    public User(int id){
        this.id = id;
    }

    private String question;
    private String answer;

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public String getNickname() {return nickname;}
    public Gender getGender() {return gender;}
    public int getAge() {return age;}
    public int getNumberOfGames() {return numberOfGames;}
    public int getHighestScore() {return HighestScore;}
    public Game getCurrentGame() {return currentGame;}
    public ArrayList<Game> getGames() {return games;}

    public String getQuestion() {return question;}
    public String getAnswer() {return answer;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setGender(Gender gender) {this.gender = gender;}
    public void setAge(int age) {this.age = age;}
    public void setNumberOfGames(int numberOfGames) {this.numberOfGames = numberOfGames;}
    public void setHighestScore(int HighestScore) {this.HighestScore = HighestScore;}
    public void setCurrentGame(Game currentGame) {this.currentGame = currentGame;}

    public void setQuestion(String question) {this.question = question;}
    public void setAnswer(String answer) {this.answer = answer;}
}
