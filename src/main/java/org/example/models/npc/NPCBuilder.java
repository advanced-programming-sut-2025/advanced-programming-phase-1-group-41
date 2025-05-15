package org.example.models.npc;


import org.example.models.App;
import org.example.models.Occupation;
import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.buildings.Building;
import org.example.models.buildings.marketplaces.*;
import org.example.models.buildings.npchomes.AbigailHome;
import org.example.models.buildings.npchomes.NPCHome;
import org.example.models.buildings.npchomes.RobinHome;
import org.example.models.items.CraftableItem;
import org.example.models.items.Food;
import org.example.models.items.Item;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;
import org.example.models.items.Slot;
import org.example.models.items.craftableitems.CraftableNames;
import org.example.models.items.craftableitems.SmokedFish;
import org.example.models.locations.Village;

import java.util.ArrayList;
import java.util.Arrays;

public class NPCBuilder {
    ArrayList<NPC> NPCs = new ArrayList<>();
    public NPCBuilder() {
        buildNPCs();


    }
    private void buildNPCs() {
        for(Building building:App.getGame().getVillage().getBuildings()){
            if(building instanceof FishShop){
                NPCs.add(new Willy("Willy", Occupation.Fisher,(FishShop) building,
                        new ArrayList<String>(Arrays.asList(
                                "You found legend fish back in you farm?, i though they are just legend",
                                "Best season for fishing, yeah?",
                                "Rain makes the fishes to hide, just wait for its time",
                                "Do not let your lake to freeze, now it`s the time")),
                        new ArrayList<Item>(Arrays.asList(
                                new Fish(FishType.Sunfish),
                                Food.DishOTheSea,
                                Food.BakedFish)),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(new Fish(FishType.Crimsonfish),1),
                                new Slot(new Fish(FishType.Tilapia),2),
                                new Slot(new Fish(FishType.Perch),10))),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(new Slot(new Fish(FishType.Legend), 1), new Slot(new Fish(FishType.Angler), 10), "find the legend", "Find me a legend fish, i`ll give you 10 angler", "it`s beautiful!"),
                                new Quest(new Slot(new SmokedFish(), 2), new Slot(new Fish(FishType.Glacierfish), 5), "cook the fish", "I love Glacier Fish, more than that,I love food", "Yummy"),
                                new Quest(new Slot(CraftableItem.Beer, 10), new Slot(new Fish(FishType.RainbowTrout), 100), "better than fish", "Can you find it for me?", "oh Gosh , thank you")))));
            }else if(building instanceof Jojamart){
                NPCs.add(new Morris("Morris", Occupation.Farmer, (Jojamart) building,
                        new ArrayList<String>(Arrays.asList(
                                "Crops need love and sunlight.",
                                "Best feeling? Harvest day!",
                                "Watch out for crows... they’re smart.",
                                "Dawn is the perfect time to start work."
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                Food.FarmerLunch,
                                Food.SurvivalBurger,
                                Food.VegetableMedley
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(Food.Salad, 3),
                                new Slot(Food.Bread, 2),
                                new Slot(Food.FarmerLunch, 1)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(new Slot(Food.FruitSalad, 1), new Slot(Food.PumpkinPie, 2), "F for the Fruits", "Make something good from you fruits", "Tastes like strength!"),
                                new Quest(new Slot(Food.VegetableMedley, 3), new Slot(CraftableItem.Honey, 5), "Garden Exchange", "Veggies for sweetness?", "Sweet deal!"),
                                new Quest(new Slot(Food.FarmerLunch, 1), new Slot(CraftableItem.GoldBar, 2), "Midday Boost", "Can’t farm without fuel.", "Let’s keep growing.")
                        )
                )));
            }else if(building instanceof Saloon){
                NPCs.add(new Gus("Gus", Occupation.Bartender, (Saloon) building,
                        new ArrayList<String>(Arrays.asList(
                                "Nothing like a cold one after a long day.",
                                "Got stories and ales for everyone.",
                                "Keep the bar clean and the mugs full.",
                                "Try the house special – it bites back!"
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                CraftableItem.Beer,
                                CraftableItem.Mead,
                                CraftableItem.PaleAle
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(CraftableItem.Beer, 5),
                                new Slot(CraftableItem.Cheese, 2),
                                new Slot(Food.Pizza, 1)
                        )),
                                new ArrayList<Quest>(Arrays.asList(
                                new Quest(new Slot(CraftableItem.Coffee, 1), new Slot(CraftableItem.Mead, 3), "Long Night", "give me some cofee", "that would do it!"),
                                new Quest(new Slot(CraftableItem.Beer, 10), new Slot(Food.Cookie, 5), "Pub Game Reward", "Beer for snacks? Always a win.", "Tasty!"),
                                new Quest(new Slot(Food.TripleShotEspresso, 1), new Slot(CraftableItem.PaleAle, 2), "Wake-Up Call", "Need a counter-drink? Got you.", "Now I'm balanced!")
                        ))
                ));
            }else if(building instanceof MarnieRanch){
                NPCs.add(new Marine("Marnie", Occupation.Rancher, (MarnieRanch) building,
                        new ArrayList<String>(Arrays.asList(
                                "Animals are like family – treat them right.",
                                "Milk day is the best day!",
                                "Happy animals give better produce.",
                                "Don't forget to feed them before sunset!"
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                CraftableItem.Cheese,
                                CraftableItem.GoatCheese,
                                CraftableItem.LargeCheese
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(CraftableItem.Cheese, 2),
                                new Slot(CraftableItem.LargeGoatCheese, 1),
                                new Slot(CraftableItem.Honey, 4)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(new Slot(new Product(ProductType.GoatMilk), 2), new Slot(CraftableItem.GoldBar, 3), "Goat's Gift", "Can you fetch me goat milk? I’ll pay well.", "Delicious!"),
                                new Quest(new Slot(CraftableItem.TruffleOil, 3), new Slot(Food.PumpkinPie, 5), "Fruit for Pie", "Let’s bake something tasty.", "Sweet trade!"),
                                new Quest(new Slot(CraftableItem.Cheese, 5), new Slot(Food.Bread, 5), "Cheesy Exchange", "Stocking up for winter meals.", "That'll last me weeks!")
                        ))
                ));
            }else if(building instanceof Blacksmith){
                NPCs.add(new Clint("Clint", Occupation.BlackSmith, (Blacksmith) building,
                        new ArrayList<String>(Arrays.asList(
                                "Forge work is loud but satisfying.",
                                "Steel sings when it's strong.",
                                "Don't touch the anvil. It's hot.",
                                "A good hammer is worth its weight."
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                Food.MinerTreat,
                                CraftableItem.Coffee,
                                Food.TripleShotEspresso
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(CraftableItem.Coffee, 2),
                                new Slot(Food.MinerTreat, 2),
                                new Slot(Food.Bread, 3)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(new Slot(Food.TripleShotEspresso, 1), new Slot(Food.MinerTreat, 3), "Smith’s Brew", "I need high energy to smelt all day.", "Whew! Much better."),
                                new Quest(new Slot(Food.Spaghetti, 2), new Slot(Food.HashBrowns, 4), "Lunch for Tools", "Trade some grub for extra nails.", "Much appreciated."),
                                new Quest(new Slot(CraftableItem.Coffee, 5), new Slot(CraftableItem.Cheese, 1), "Coffee for Craft", "Fuel me up and I’ll throw in some extras.", "Now I'm fired up!")
                        ))
                ));
            }else if(building instanceof CarpenterShop){
                for(Building building1 : App.getGame().getVillage().getBuildings()){
                    if(building1 instanceof RobinHome){
                        NPCs.add(new Robin("Robin", Occupation.Carpenter,(RobinHome)building1, (CarpenterShop) building,
                                new ArrayList<String>(Arrays.asList(
                                        "Wood is the soul of any home.",
                                        "Sawdust in your lungs builds character.",
                                        "A hammer and a dream — that’s all you need.",
                                        "Ever built your own chair? Try it sometime."
                                )),
                                new ArrayList<Item>(Arrays.asList(
                                        Food.MinerTreat,
                                        Food.HashBrowns,
                                        Food.SurvivalBurger
                                )),
                                new ArrayList<Slot>(Arrays.asList(
                                        new Slot(Food.Bread, 4),
                                        new Slot(Food.HashBrowns, 3),
                                        new Slot(Food.MinerTreat, 2)
                                )),
                                new ArrayList<Quest>(Arrays.asList(
                                        new Quest(new Slot(Food.HashBrowns, 5), new Slot(Food.MinerTreat, 1), "Builder's Breakfast", "Need energy to build? Bring food!", "Now that hits the spot."),
                                        new Quest(new Slot(Food.SurvivalBurger, 2), new Slot(Food.Spaghetti, 1), "Heavy Meal Deal", "Burgers for a hearty pasta.", "Can build for days now."),
                                        new Quest(new Slot(Food.Cookie, 10), new Slot(CraftableItem.Cheese, 5), "Cookie Bribe", "Cookies help with client negotiations.", "Sweet talk worked!")
                                ))
                        ));
                    }
                }

            }else if(building instanceof GeneralStore){
                NPCs.add(new Pierre("Pierre", Occupation.Broker, (GeneralStore) building,
                        new ArrayList<String>(Arrays.asList(
                                "Prices go up, prices go down — supply and demand!",
                                "A good deal is a good day.",
                                "Restocking is half the job.",
                                "Let me know if you're buying in bulk!"
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                Food.Bread,
                                Food.Salad,
                                Food.FruitSalad
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(Food.Cookie, 5),
                                new Slot(Food.Bread, 10),
                                new Slot(Food.FruitSalad, 2)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(new Slot(Food.FruitSalad, 1), new Slot(Food.Salad, 5), "Fresh Inventory", "I need to refresh the shelf with healthy food.", "Great quality!"),
                                new Quest(new Slot(Food.Cookie, 10), new Slot(CraftableItem.Beer, 2), "Snack Resupply", "Popular snacks sell fast. Help me out?", "My best-seller!"),
                                new Quest(new Slot(Food.Bread, 8), new Slot(Food.SurvivalBurger, 4), "Sandwich Deal", "Bread for jelly, sounds like a win!", "Perfect pairing.")
                        ))
                ));
            }







        }
        App.getGame().getVillage().setNPCs(NPCs);
    }

}
