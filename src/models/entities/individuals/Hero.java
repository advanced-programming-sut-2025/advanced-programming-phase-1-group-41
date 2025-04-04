package models.entities.individuals;

import models.User;
import models.entities.Entity;

public class Hero extends Human implements Entity {
    User user;

    public Hero(User user) {
        super();
        this.user = user;
    }
}
