package org.example.controllers;

import org.example.models.*;
import org.example.models.animals.Animal;
import org.example.models.animals.Breed;
import org.example.models.animals.animalKinds.*;
import org.example.models.buildings.animalContainer.Barn;
import org.example.models.buildings.animalContainer.BarnType;
import org.example.models.buildings.animalContainer.Coop;
import org.example.models.buildings.animalContainer.CoopType;
import org.example.models.foragings.Nature.Nature;

import java.util.regex.Matcher;

import static org.example.models.animals.Animal.parseAnimal;
import static org.example.models.buildings.animalContainer.BarnType.parseBarn;
import static org.example.models.buildings.animalContainer.CoopType.parseCoop;

public class AnimalController {
    public Result build(Matcher matcher){
        String buildingName = matcher.group("buildingName");
        String x = matcher.group("x");
        String y = matcher.group("y");
        if(buildingName.toLowerCase().contains("barn")){
            BarnType barnType = parseBarn(buildingName.split(" ")[0]);
            if(barnType == null){
                return new Result(false, "Invalid barn type");
            }
            return buildBarn(Integer.parseInt(x), Integer.parseInt(y), barnType);
        }
        if(buildingName.toLowerCase().contains("coop")){
            CoopType coopType =parseCoop(buildingName.split(" ")[0]);
            if(coopType == null){
                return new Result(false, "Invalid coop type");
            }
            return buildCoop(Integer.parseInt(x), Integer.parseInt(y), coopType);
        }
        return new Result(false,"Invalid building name");//TODO more buildings

    }

    private Result buildCoop(int x, int y, CoopType coopType){
        int xSize=0;
        int ySize=0;
        switch(coopType){
            case Normal -> {
                xSize = 6; ySize=6;
            }
            case Big -> {
                xSize=7;ySize=7;
            }
            case Deluxe -> {
                xSize=8; ySize=8;
            }
        }
        for(int j=y;j<y+ySize;++j){
            for(int i=x;i<x+xSize;++i){
                Cell cell = App.getGame().getCurrentPlayerFarm().getCell(i, j);
                if(cell == null) continue;
                if (!(cell.getObjectMap() instanceof Nature) &&
                        !(cell.getObjectMap() instanceof Grass  )) {
                    return new Result(false, "there is obstackle in the way");
                }
            }
        }
        App.getGame().getCurrentPlayerFarm().creatNewCoop(x,y,coopType);
        return new Result(true,"coop added");
    }

    private Result buildBarn(int x,int y,BarnType barnType) {
        int xSize = 0;
        int ySize = 0;
        switch (barnType) {
            case Normal -> {
                xSize = 6;
                ySize = 6;
            }
            case Big -> {
                xSize = 7;
                ySize = 7;
            }
            case Deluxe -> {
                xSize = 8;
                ySize = 8;
            }
        }
        for (int j = y; j < y + ySize; ++j) {
            for (int i = x; i < x + xSize; ++i) {
                Cell cell = App.getGame().getCurrentPlayerFarm().getCell(i, j);
                if(cell == null) continue;
                if (!(cell.getObjectMap() instanceof Nature) &&
                        !(cell.getObjectMap() instanceof Grass  )) {
                    return new Result(false, "there is obstackle in the way");
                }
            }
        }
        App.getGame().getCurrentPlayerFarm().creatNewBarn(x, y, barnType);
        return new Result(true, "Barn added");

    }

    public Result buyAnimal(Matcher matcher){
        String animalType = matcher.group(1).trim().toLowerCase();
        String animalName = matcher.group(2).trim().toLowerCase();
        Animal animal = parseAnimal(animalType,animalName);
        if(animal==null){
            return new Result(false, "Invalid animal type "+animalType);
        }
        if(animal.getBreed()== Breed.Barn){
            switch(animal.getSizeNeeded()){
                case Deluxe ->{
                    for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
                        if (barn.getBarnType().getCapacity()==12){
                            if(barn.getCapacity()>0){
                                barn.addAnimal(animal);
                                barn.updateCapacity(-1);
                                return new Result(true,"Animal added to "+barn.getBarnType().name()+" barn");
                            }
                        }
                    }
                    return new Result(false, "not enough space for "+animalType);
                }
                case Big -> {
                    for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
                        if (barn.getBarnType().getCapacity()>=8){
                            if(barn.getCapacity()>0){
                                barn.addAnimal(animal);
                                barn.updateCapacity(-1);
                                return new Result(true,"Animal added to "+barn.getBarnType().name()+" barn");
                            }
                        }
                    }
                    return new Result(false, "not enough space for "+animalType);
                }
                case Normal -> {
                    for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
                        if (barn.getBarnType().getCapacity()>=4){
                            if(barn.getCapacity()>0){
                                barn.addAnimal(animal);
                                barn.updateCapacity(-1);
                                return new Result(true,"Animal added to "+barn.getBarnType().name()+" barn");
                            }
                        }
                    }
                    return new Result(false, "not enough space for "+animalType);
                }

            }
        }else if(animal.getBreed()== Breed.Coop){
            switch(animal.getSizeNeeded()){
                case Deluxe ->{
                    for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
                        if (coop.getCoopType().getCapacity()==12){
                            if(coop.getCapacity()>0){
                                coop.addAnimal(animal);
                                coop.updateCapacity(-1);
                                return new Result(true,"Animal added to "+coop.getCoopType().name()+" coop");
                            }
                        }
                    }
                    return new Result(false, "not enough space for +"+animalType);
                }
                case Big -> {
                    for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
                        if (coop.getCoopType().getCapacity()>=8){
                            if(coop.getCapacity()>0){
                                coop.addAnimal(animal);
                                coop.updateCapacity(-1);
                                return new Result(true,"Animal added to "+coop.getCoopType().name()+" coop");
                            }
                        }
                    }
                    return new Result(false, "not enough space for +"+animalType);
                }
                case Normal -> {
                    for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
                        if (coop.getCoopType().getCapacity()>=4){
                            if(coop.getCapacity()>0){
                                coop.addAnimal(animal);
                                coop.updateCapacity(-1);
                                return new Result(true,"Animal added to "+coop.getCoopType().name()+" coop");
                            }
                        }
                    }
                    return new Result(false, "not enough space for +"+animalType);
                }

            }

        }
        return null;
    }

    public Result pet(Matcher matcher) {
        String name=matcher.group(1);
        for(int i=-2;i<=2;i++){
            for(int j=-2;j<=2;j++){
                int x = App.getGame().getCurrentPlayer().getX();
                int y = App.getGame().getCurrentPlayer().getY();
                Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x+i,y+j);
                if(cell == null) continue;
                ObjectMap objectMap=cell.getObjectMap();
                if(objectMap instanceof Animal&&objectMap.getName().equals(name)){
                    ((Animal) objectMap).increaseFriendShip(15);
                    return new Result(true,"you pet "+name+", now it loves you more");
                }
                else if(objectMap instanceof Barn){
                    for(Animal animal:((Barn) objectMap).getAnimals()){
                        if(animal.getName().equals(name)){
                            animal.increaseFriendShip(15);
                            return new Result(true,"you pet "+name+" in its barn, now it loves you more");
                        }
                    }
                }
                else if(objectMap instanceof Coop){
                    for(Animal animal:((Coop) objectMap).getAnimals()){
                        if(animal.getName().equals(name)){
                            animal.increaseFriendShip(15);
                            return new Result(true,"you pet "+name+" in its coop, now it loves you more");
                        }
                    }
                }
            }
        }
        return new Result(false,name+" is not anywhere around you");
    }

    public Result cheatSetFriendship(Matcher matcher){
        String name=matcher.group(1);
        String amount=matcher.group(2);
        for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
            for(Animal animal:barn.getAnimals()){
                if(animal.getName().equals(name)){
                    animal.increaseFriendShip(Integer.parseInt(amount)-animal.getFriendShip());
                    return new Result(true,"friendShip with "+animal.getName()+" set to "+animal.getFriendShip());
                }
            }
        }
        for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
            for(Animal animal:coop.getAnimals()){
                if(animal.getName().equals(name)){
                    animal.increaseFriendShip(Integer.parseInt(amount)-animal.getFriendShip());
                    return new Result(true,"friendShip with "+animal.getName()+" set to "+animal.getFriendShip());
                }
            }
        }
        return new Result(false,"can`t find "+name);

    }

    public Result animalsList(Matcher matcher){
        for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
            for(Animal animal:barn.getAnimals()){
                String name = "animal";
                if (animal instanceof Cow) {
                    name="Cow";
                } else if(animal instanceof Chicken){
                    name="Chicken";
                } else if(animal instanceof Rabbit){
                    name="Rabbit";
                } else if(animal instanceof Pig){
                    name="Pig";
                } else if(animal instanceof Dino){
                    name="Dino";
                } else if(animal instanceof Sheep){
                    name="Sheep";
                } else if(animal instanceof Goat){
                    name="Goat";
                } else if(animal instanceof Duck){
                    name="Duck";
                }
                System.out.println(animal.getName()+" the "+name+" -> friendShip : "+animal.getFriendShip()+", is pet today : "+"todo"+", if fed today :"+"todo");
            }
        }
        return new Result(true,"");
    }

    public Result shepherdAnimals(Matcher matcher){
        String name=matcher.group(1);
        int x=Integer.parseInt(matcher.group(2));
        int y=Integer.parseInt(matcher.group(3));
        Animal theAnimal=null;
        Barn theBarn=null;
        Coop theCoop=null;
        for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
            for(Animal animal:barn.getAnimals()){
                if(animal.getName().equals(name)){
                     theAnimal=animal;
                     theBarn=barn;
                     break;
                }
            }
        }
        for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
            for(Animal animal:coop.getAnimals()){
                if(animal.getName().equals(name)){
                   theAnimal=animal;
                   theCoop=coop;
                   break;
                }
            }
        }
        if(theAnimal==null){
            return new Result(false,"can`t find "+name);
        }
        Cell cell =App.getGame().getCurrentPlayerFarm().getCell(x, y);
        if(cell==null){
            return new Result(false,"can`t find cell");
        }
        if (!(cell.getObjectMap() instanceof Grass  )&&
                (theBarn!=null&&!(cell.getObjectMap() instanceof Barn  )||
                theCoop!=null&&!(cell.getObjectMap() instanceof Coop  ))) {
            return new Result(false, "there is no grass in selected area");
        }
        if((theBarn!=null&&(cell.getObjectMap() instanceof Barn  )||
                theCoop!=null&&(cell.getObjectMap() instanceof Coop  ))){
            return new Result(true,name+" shepherd to its home");
        }
        switch (App.getGame().getWeatherType()){
            case Snowy,Stormy,Rainy ->{
                return new Result(false , "you can not shepherd animals on "+App.getGame().getWeatherType().name()+" weather");
            }
            default ->{}
        }
        theAnimal.increaseFriendShip(8);//TODO -> place ship on ground
        return new Result(true,name+" shepherd to grass");

    }

    public Result feedHay(Matcher matcher){
        return null;
    }

    public Result producesList(Matcher matcher){return null;}

    public Result collectProduct(Matcher matcher){return null;}

    public Result sellAnimal(Matcher matcher){
        String name=matcher.group(1);
        Animal theAnimal=null;
        Barn theBarn=null;
        Coop theCoop=null;
        for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
            for(Animal animal:barn.getAnimals()){
                if(animal.getName().equals(name)){
                    theAnimal=animal;
                    theBarn=barn;
                    break;
                }
            }
        }
        for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
            for(Animal animal:coop.getAnimals()){
                if(animal.getName().equals(name)){
                    theAnimal=animal;
                    theCoop=coop;
                    break;
                }
            }
        }
        if(theAnimal==null){
            return new Result(false,"can`t find "+name);
        }
        if(theBarn!=null){
            theBarn.updateCapacity(+1);
        }
        if(theCoop!=null){
            theCoop.updateCapacity(+1);

        }
        double sellPrice = theAnimal.getBuyPrice()*(((double) theAnimal.getFriendShip() /1000)+0.3);
        // todo sell;
        return new Result(true,"sold "+name+" for "+sellPrice);

    }

    public void reset(){
        // for players
        // reset booleans for all barns and coops animals
    }

}
