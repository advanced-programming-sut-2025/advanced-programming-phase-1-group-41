package org.example.models;

public enum Gender {
    Male, Female;

    public static Gender parseGender(String gender){
        if(gender.equals("male")){
            return Male;
        }else if(gender.equals("female")){
            return Female;
        }
        return null;
    }
}
