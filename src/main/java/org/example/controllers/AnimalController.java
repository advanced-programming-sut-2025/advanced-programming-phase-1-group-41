package org.example.controllers;

import org.example.models.*;
import org.example.models.animals.Animal;
import org.example.models.animals.Breed;
import org.example.models.animals.animalKinds.*;
import org.example.models.buildings.animalContainer.Barn;
import org.example.models.buildings.animalContainer.BarnType;
import org.example.models.buildings.animalContainer.Coop;
import org.example.models.buildings.animalContainer.CoopType;
import org.example.models.foragings.Nature.Grass;
import org.example.models.foragings.Nature.Nature;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;
import org.example.models.locations.Farm;

import java.util.regex.Matcher;

import static org.example.models.animals.Animal.parseAnimal;
import static org.example.models.buildings.animalContainer.BarnType.parseBarn;
import static org.example.models.buildings.animalContainer.CoopType.parseCoop;

public class AnimalController {
    public Result build(Matcher matcher) {
        String buildingName = matcher.group("buildingName");
        String x = matcher.group("x");
        String y = matcher.group("y");
        if (buildingName.toLowerCase().contains("barn")) {
            BarnType barnType = parseBarn(buildingName.split(" ")[0]);
            if (barnType == null) {
                return new Result(false, "Invalid barn type");
            }
            return buildBarn(Integer.parseInt(x), Integer.parseInt(y), barnType);
        }
        if (buildingName.toLowerCase().contains("coop")) {
            CoopType coopType = parseCoop(buildingName.split(" ")[0]);
            if (coopType == null) {
                return new Result(false, "Invalid coop type");
            }
            return buildCoop(Integer.parseInt(x), Integer.parseInt(y), coopType);
        }
        return new Result(false, "Invalid building name");//TODO more buildings

    }

    private Result buildCoop(int x, int y, CoopType coopType) {
        int xSize = 0;
        int ySize = 0;
        switch (coopType) {
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
                if (cell == null) continue;
                if (!(cell.getObjectMap() instanceof Nature) &&
                        !(cell.getObjectMap() instanceof Grass)) {
                    return new Result(false, "there is obstackle in the way");
                }
            }
        }
        App.getGame().getCurrentPlayerFarm().creatNewCoop(x, y, coopType);
        return new Result(true, "coop added");
    }

    private Result buildBarn(int x, int y, BarnType barnType) {
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
                if (cell == null) continue;
                if (!(cell.getObjectMap() instanceof Nature) &&
                        !(cell.getObjectMap() instanceof Grass)) {
                    return new Result(false, "there is obstackle in the way");
                }
            }
        }
        App.getGame().getCurrentPlayerFarm().creatNewBarn(x, y, barnType);
        return new Result(true, "Barn added");

    }

    public Result buyAnimal(Matcher matcher) {
        String animalType = matcher.group(1).trim().toLowerCase();
        String animalName = matcher.group(2).trim().toLowerCase();
        Animal animal = parseAnimal(animalType, animalName);
        if (animal == null) {
            return new Result(false, "Invalid animal type " + animalType);
        }
        if (animal.getBreed() == Breed.Barn) {
            switch (animal.getSizeNeeded()) {
                case Deluxe -> {
                    for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
                        if (barn.getBarnType().getCapacity() == 12) {
                            if (barn.getCapacity() > 0) {
                                barn.addAnimal(animal);
                                barn.updateCapacity(-1);
                                return new Result(true, "Animal added to " + barn.getBarnType().name() + " barn");
                            }
                        }
                    }
                    return new Result(false, "not enough space for " + animalType);
                }
                case Big -> {
                    for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
                        if (barn.getBarnType().getCapacity() >= 8) {
                            if (barn.getCapacity() > 0) {
                                barn.addAnimal(animal);
                                barn.updateCapacity(-1);
                                return new Result(true, "Animal added to " + barn.getBarnType().name() + " barn");
                            }
                        }
                    }
                    return new Result(false, "not enough space for " + animalType);
                }
                case Normal -> {
                    for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
                        if (barn.getBarnType().getCapacity() >= 4) {
                            if (barn.getCapacity() > 0) {
                                barn.addAnimal(animal);
                                barn.updateCapacity(-1);
                                return new Result(true, "Animal added to " + barn.getBarnType().name() + " barn");
                            }
                        }
                    }
                    return new Result(false, "not enough space for " + animalType);
                }

            }
        } else if (animal.getBreed() == Breed.Coop) {
            switch (animal.getSizeNeeded()) {
                case Deluxe -> {
                    for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
                        if (coop.getCoopType().getCapacity() == 12) {
                            if (coop.getCapacity() > 0) {
                                coop.addAnimal(animal);
                                coop.updateCapacity(-1);
                                return new Result(true, "Animal added to " + coop.getCoopType().name() + " coop");
                            }
                        }
                    }
                    return new Result(false, "not enough space for +" + animalType);
                }
                case Big -> {
                    for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
                        if (coop.getCoopType().getCapacity() >= 8) {
                            if (coop.getCapacity() > 0) {
                                coop.addAnimal(animal);
                                coop.updateCapacity(-1);
                                return new Result(true, "Animal added to " + coop.getCoopType().name() + " coop");
                            }
                        }
                    }
                    return new Result(false, "not enough space for +" + animalType);
                }
                case Normal -> {
                    for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
                        if (coop.getCoopType().getCapacity() >= 4) {
                            if (coop.getCapacity() > 0) {
                                coop.addAnimal(animal);
                                coop.updateCapacity(-1);
                                return new Result(true, "Animal added to " + coop.getCoopType().name() + " coop");
                            }
                        }
                    }
                    return new Result(false, "not enough space for +" + animalType);
                }

            }

        }
        return null;
    }

    public Result pet(Matcher matcher) {
        String name = matcher.group(1);
        Animal theAnimal = null;
        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if (animal.getName().equals(name)) {
                    theAnimal = animal;
                    break;
                }
            }
        }
        for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
            for (Animal animal : coop.getAnimals()) {
                if (animal.getName().equals(name)) {
                    theAnimal = animal;
                    break;
                }
            }
        }
        if (theAnimal == null) {
            return new Result(false, name + " is not one of your pets");
        }
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int x = App.getGame().getCurrentPlayer().getX() + i;
                int y = App.getGame().getCurrentPlayer().getY() + j;
                Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x, y);
                if (cell == null) {
                    continue;
                }
                ObjectMap objectMap = cell.getObjectMap();
                if (theAnimal.getX() == x && theAnimal.getY() == y) {
                    theAnimal.increaseFriendShip(15);
                    theAnimal.setPetToday(true);
                    return new Result(true, "you pet " + name + ", now it loves you more");
                } else if (objectMap instanceof Barn) {
                    for (Animal animal : ((Barn) objectMap).getAnimals()) {
                        if (animal.getName().equals(name)) {
                            animal.increaseFriendShip(15);
                            animal.setPetToday(true);
                            return new Result(true, "you pet " + name + " in its barn, now it loves you more");
                        }
                    }
                } else if (objectMap instanceof Coop) {
                    for (Animal animal : ((Coop) objectMap).getAnimals()) {
                        if (animal.getName().equals(name)) {
                            animal.increaseFriendShip(15);
                            animal.setPetToday(true);
                            return new Result(true, "you pet " + name + " in its coop, now it loves you more");
                        }
                    }
                }
            }
        }
        return new Result(false, name + " is not anywhere around you");
    }

    public Result cheatSetFriendship(Matcher matcher) {
        String name = matcher.group(1);
        String amount = matcher.group(2);
        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if (animal.getName().equals(name)) {
                    animal.increaseFriendShip(Integer.parseInt(amount) - animal.getFriendShip());
                    return new Result(true, "friendShip with " + animal.getName() + " set to " + animal.getFriendShip());
                }
            }
        }
        for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
            for (Animal animal : coop.getAnimals()) {
                if (animal.getName().equals(name)) {
                    animal.increaseFriendShip(Integer.parseInt(amount) - animal.getFriendShip());
                    return new Result(true, "friendShip with " + animal.getName() + " set to " + animal.getFriendShip());
                }
            }
        }
        return new Result(false, "can`t find " + name);

    }

    public Result animalsList(Matcher matcher) {
        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                String name = "animal";
                if (animal instanceof Cow) {
                    name = "Cow";
                } else if (animal instanceof Chicken) {
                    name = "Chicken";
                } else if (animal instanceof Rabbit) {
                    name = "Rabbit";
                } else if (animal instanceof Pig) {
                    name = "Pig";
                } else if (animal instanceof Dino) {
                    name = "Dino";
                } else if (animal instanceof Sheep) {
                    name = "Sheep";
                } else if (animal instanceof Goat) {
                    name = "Goat";
                } else if (animal instanceof Duck) {
                    name = "Duck";
                }
                System.out.println("int your barns you have :");
                System.out.println(animal.getName() + " the " + name + " -> friendShip : " + animal.getFriendShip() + ", is pet today : " + animal.isPetToday() + ", is fed today :" + animal.isFedToday());
            }
        }
        for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
            for (Animal animal : coop.getAnimals()) {
                String name = "animal";
                if (animal instanceof Cow) {
                    name = "Cow";
                } else if (animal instanceof Chicken) {
                    name = "Chicken";
                } else if (animal instanceof Rabbit) {
                    name = "Rabbit";
                } else if (animal instanceof Pig) {
                    name = "Pig";
                } else if (animal instanceof Dino) {
                    name = "Dino";
                } else if (animal instanceof Sheep) {
                    name = "Sheep";
                } else if (animal instanceof Goat) {
                    name = "Goat";
                } else if (animal instanceof Duck) {
                    name = "Duck";
                }
                System.out.println("int your coops you have :");
                System.out.println(animal.getName() + " the " + name + " -> friendShip : " + animal.getFriendShip() + ", is pet today : " + animal.isPetToday() + ", is fed today :" + animal.isFedToday());
            }
        }
        return new Result(true, "");
    }

    public Result shepherdAnimals(Matcher matcher) {
        String name = matcher.group(1);
        int x = Integer.parseInt(matcher.group(2));
        int y = Integer.parseInt(matcher.group(3));
        Animal theAnimal = null;
        Barn theBarn = null;
        Coop theCoop = null;
        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if (animal.getName().equals(name)) {
                    theAnimal = animal;
                    theBarn = barn;
                    break;
                }
            }
        }
        for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
            for (Animal animal : coop.getAnimals()) {
                if (animal.getName().equals(name)) {
                    theAnimal = animal;
                    theCoop = coop;
                    break;
                }
            }
        }
        if (theAnimal == null) {
            return new Result(false, "can`t find " + name);
        }
        Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x, y);
        if (cell == null) {
            return new Result(false, "can`t find cell");
        }
        if (!(cell.getObjectMap() instanceof Grass) &&
                (theBarn != null && !(cell.getObjectMap() instanceof Barn) ||
                        theCoop != null && !(cell.getObjectMap() instanceof Coop))) {
            return new Result(false, "there is no grass in selected area");
        }
        if ((theBarn != null && (cell.getObjectMap() instanceof Barn) ||
                theCoop != null && (cell.getObjectMap() instanceof Coop))) {
            theAnimal.setHome(true);
            theAnimal.setX(-10);
            theAnimal.setY(-10);
            return new Result(true, name + " shepherd to its home");
        }
        switch (App.getGame().getWeatherType()) {
            case Snowy, Stormy, Rainy -> {
                return new Result(false, "you can not shepherd animals on " + App.getGame().getWeatherType().name() + " weather");
            }
            default -> {
            }
        }
        theAnimal.increaseFriendShip(8);//TODO -> place ship on ground
        theAnimal.setHome(false);
        theAnimal.setX(x);
        theAnimal.setY(y);
        theAnimal.setFedToday(true);
        return new Result(true, name + " shepherd to grass");

    }

    public Result feedHay(Matcher matcher) {
        String name = matcher.group(1);
        Animal theAnimal = null;
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int x = App.getGame().getCurrentPlayer().getX();
                int y = App.getGame().getCurrentPlayer().getY();
                Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x + i, y + j);
                if (cell == null) continue;
                ObjectMap objectMap = cell.getObjectMap();
                if (objectMap instanceof Barn) {
                    for (Animal animal : ((Barn) objectMap).getAnimals()) {
                        if (animal.getName().equals(name)) {
                            animal.increaseFriendShip(8);
                            animal.setFedToday(true);
                            return new Result(true, name + " is fed with Hay, now it loves you more");
                        }
                    }
                } else if (objectMap instanceof Coop) {
                    for (Animal animal : ((Coop) objectMap).getAnimals()) {
                        if (animal.getName().equals(name)) {
                            animal.increaseFriendShip(8);
                            animal.setFedToday(true);
                            return new Result(true, name + " is fed with Hay, now it loves you more");
                        }
                    }
                }
            }
        }
        return new Result(false, name + " is not anywhere around you");
    }

    public Result producesList(Matcher matcher) {
        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if (animal.getProduct() != null) {
                    System.out.println(animal.getName() + " has some " + animal.getProduct().getName() + " to collect");
                }
            }
        }
        for (Coop coop : App.getGame().getCurrentPlayerFarm().getCoops()) {
            for (Animal animal : coop.getAnimals()) {
                if (animal.getProduct() != null) {
                    System.out.println(animal.getName() + " has some " + animal.getProduct().getName() + " to collect");
                }
            }
        }
        return new Result(true,"");
    }


    public Result collectProduct(Matcher matcher){
        String name=matcher.group(1);
        Animal theAnimal=null;
        for(Barn barn:App.getGame().getCurrentPlayerFarm().getBarns()){
            for(Animal animal: barn.getAnimals()){
                if(animal.getName().equals(name)){
                    theAnimal = animal;
                    break;
                }
            }
        }
        for(Coop coop:App.getGame().getCurrentPlayerFarm().getCoops()){
            for(Animal animal: coop.getAnimals()){
                if(animal.getName().equals(name)){
                    theAnimal = animal;
                    break;
                }
            }
        }
        if(theAnimal==null){
            return new Result(false,name+" is not one of your pets");
        }
        for(int i=-2;i<=2;i++){
            for(int j=-2;j<=2;j++){
                int x = App.getGame().getCurrentPlayer().getX()+i;
                int y = App.getGame().getCurrentPlayer().getY()+j;
                Cell cell=App.getGame().getCurrentPlayerFarm().getCell(x, y);
                if(cell==null){
                    continue;
                }
                ObjectMap objectMap=cell.getObjectMap();
                if(theAnimal.getX()==x && theAnimal.getY()==y){
                    if(theAnimal.getProduct()==null){
                        return new Result(false,"no product is available");
                    }
                    String productName=theAnimal.getProduct().getName();
                    App.getGame().getCurrentPlayer().getInventory().addToInventory(theAnimal.getProduct(),1);
                    if(theAnimal.getProduct().getName().contains("Milk")||theAnimal.getProduct().getName().contains("Sheep")){
                        theAnimal.increaseFriendShip(5);
                    }
                    theAnimal.setProduct(null);

                    return new Result(true,"you collected " + productName + " from " + theAnimal.getName());
                }
                else if(objectMap instanceof Barn){
                    for(Animal animal:((Barn) objectMap).getAnimals()){
                        if(animal.getName().equals(name)){
                            if(animal.getProduct()!=null) {
                                String productName = animal.getProduct().getName();
                                App.getGame().getCurrentPlayer().getInventory().addToInventory(animal.getProduct(),1);
                                if(theAnimal.getProduct().getName().contains("Milk")||theAnimal.getProduct().getName().contains("Sheep")){
                                    theAnimal.increaseFriendShip(5);
                                }
                                animal.setProduct(null);
                                return new Result(true, "you collected " + productName + " from " + animal.getName());
                            }
                            return new Result(false, "no product is available");
                        }
                    }
                }
                else if(objectMap instanceof Coop){
                    for(Animal animal:((Coop) objectMap).getAnimals()){
                        if(animal.getName().equals(name)){
                            if(animal.getProduct()!=null) {
                                String productName = animal.getProduct().getName();
                                App.getGame().getCurrentPlayer().getInventory().addToInventory(animal.getProduct(),1);
                                if(theAnimal.getProduct().getName().contains("Milk")||theAnimal.getProduct().getName().contains("Sheep")){
                                    theAnimal.increaseFriendShip(5);
                                }
                                animal.setProduct(null);
                                return new Result(true, "you collected " + productName + " from " + animal.getName());
                            }
                            return new Result(false, "no product is available");
                        }
                    }
                }
            }
        }
        return new Result(false,name+" is not anywhere around you");
    }
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

    private void reset(){
        Game game =App.getGame();
        for(Player player:game.getPlayers()){
            Farm farm =Finder.findFarmByPlayer(player);
            assert farm != null;
            for(Barn barn:farm.getBarns()){
                for(Animal animal: barn.getAnimals()){
                    animal.setPetToday(false);
                    animal.setFedToday(true);
//                    animal.setFedToday(false);
                }
            }
            for(Coop coop:farm.getCoops()){
                for(Animal animal: coop.getAnimals()){
                    animal.setPetToday(false);
                    animal.setFedToday(true);
//                    animal.setFedToday(false);
                }
            }
        }
    }
    private void check() {
        Game game = App.getGame();
        for (Player player : game.getPlayers()) {
            Farm farm = Finder.findFarmByPlayer(player);
            assert farm != null;
            for (Barn barn : farm.getBarns()) {
                for (Animal animal : barn.getAnimals()) {
                    if (!animal.isPetToday()) {
                        animal.increaseFriendShip((animal.getFriendShip() / 200) - 10);
                        System.out.println("poor "+animal.getName()+(" did not get any pet from "+player+" last day"));
                    }
                    if (!animal.isFedToday()) {
                        animal.increaseFriendShip(-20);
                        System.out.println("poor "+animal.getName()+(" slept with hunger in "+player+"`s farm last day"));
                    }
                    if (!animal.isHome()) {
                        animal.increaseFriendShip(-20);
                        System.out.println("poor "+animal.getName()+(" slept in cold in "+player+"`s farm last day"));
                    }
                }
            }
            for (Coop coop : farm.getCoops()) {
                for (Animal animal : coop.getAnimals()) {
                    if (!animal.isPetToday()) {
                        animal.increaseFriendShip((animal.getFriendShip() / 200) - 10);
                        System.out.println("poor "+animal.getName()+(" did not get any pet from "+player+" last day"));
                    }
                    if (!animal.isFedToday()) {
                        animal.increaseFriendShip(-20);
                        System.out.println("poor "+animal.getName()+(" slept with hunger in "+player+"`s farm last day"));
                    }else{
                        double specialProduceChance=(animal.getFriendShip()+(150*(0.5 + Math.random()))/1500);

                        if(animal.getFriendShip()>=100&&Math.random()<specialProduceChance){
                            if(animal instanceof Chicken&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.BigChickenEgg));
                            } else if(animal instanceof Cow&&animal.canGiveProduct()){
                                System.out.println("im a cow");
                                animal.setProduct(new Product(ProductType.BigCowMilk));
                            } else if(animal instanceof Goat&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.BigGoatMilk));
                            } else if(animal instanceof Duck&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.DuckFeather));
                            } else if(animal instanceof Rabbit&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.RabbitFoot));
                            } else if(animal instanceof Sheep&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.SheepWool));
                            } else if(animal instanceof Dino&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.DinoEgg));
                            } else if(animal instanceof Pig&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.PigTruffle));
                            }
                        }else{
                            if(animal instanceof Chicken && animal.canGiveProduct()){
                                System.out.println("i am a chicken");
                                animal.setProduct(new Product(ProductType.ChickenEgg));
                            } else if(animal instanceof Cow&&animal.canGiveProduct()){
                                System.out.println("i am a cow");
                                animal.setProduct(new Product(ProductType.CowMilk));
                            } else if(animal instanceof Goat&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.GoatMilk));
                            } else if(animal instanceof Duck&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.DuckEgg));
                            } else if(animal instanceof Rabbit&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.RabbitWool));
                            } else if(animal instanceof Sheep&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.SheepWool));
                            } else if(animal instanceof Dino&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.DinoEgg));
                            } else if(animal instanceof Pig&&animal.canGiveProduct()){
                                animal.setProduct(new Product(ProductType.PigTruffle));
                            }
                        }

                    }
                    if (!animal.isHome()) {
                        animal.increaseFriendShip(-20);
                        System.out.println("poor "+animal.getName()+(" slept in cold in "+player+"`s farm last day"));
                    }
                }
            }
        }
    }

    public void resetAndCheck(){
        check();
        reset();
    }

}
