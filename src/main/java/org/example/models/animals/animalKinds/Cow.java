package org.example.models.animals.animalKinds;

import org.example.models.App;
import org.example.models.Colors;
import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

public class Cow extends Animal {
    public Cow(Player owner, String name) {
        super(owner, name, 1500, BarnOrCageSize.Normal);
        super.breed = Breed.Barn;
    }

    @Override
    public void doTheFuckingJob() {
    }
    public String getChar(){
        return Colors.colorize(1,0,"🐄");
    }

}
