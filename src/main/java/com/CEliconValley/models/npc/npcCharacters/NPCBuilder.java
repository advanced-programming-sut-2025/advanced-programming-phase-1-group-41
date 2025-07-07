package com.CEliconValley.models.npc.npcCharacters;


import com.CEliconValley.models.App;
import com.CEliconValley.models.Occupation;
import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.marketplaces.*;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.npc.npchomes.*;
import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import com.CEliconValley.models.foragings.Nature.Mineral;
import com.CEliconValley.models.foragings.Nature.MineralType;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.foragings.Nature.Wood;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Products.ProductType;
import com.CEliconValley.models.items.craftableitems.Pickles;
import com.CEliconValley.models.items.craftableitems.SmokedFish;

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
                                "Do not let your lake to freeze, now it`s the time",
                                "At least the worms are happy. Fisher’s delight, right?",
                                "Snowball fight later? ...No? Fine.",
                                "At least the weeds are getting ripped out for free.",
                                "Night air’s crisp. Shame I’m stuck mending tools.",
                                "Missed your face. What’s new?",
                                "Oh. You... really shouldn't have. Like, at all.",
                                "No way… You remembered?! I could hug you right now!"
                                )),
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
                NPCs.add(new Mohsen("Mohsen", Occupation.Fisher,(FishShop) building,
                        new ArrayList<String>(Arrays.asList(
                                "Are kheili jaleb bood",
                                "Are kheili jaleb bood",
                                "Are kheili jaleb bood",
                                "Are kheili jaleb bood",
                                "Are kheili jaleb bood",
                                "Hamin dige?",
                                "Hamin dige?",
                                "Hamin dige?",
                                "Hamin dige?",
                                "khodafez dige",
                                "beram dige"
                                )),
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
                                "Dawn is the perfect time to start work.",
                                "If I hear one more ‘April showers’ joke, I’ll scream.",
                                "If I hibernate like a bear, wake me in spring.",
                                "Think the gods are mad at us, or just bored?",
                                "You ever get spooked by your own scarecrow? Just me?",
                                "Saved you a seat. Try not to spill this time.",
                                "This is my favorite thing in the world. How are you this good to me?"
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
                                "Try the house special – it bites back!",
                                "Perfect weather for staying in... too bad I’ve got chores.",
                                "Can’t feel my toes. That’s normal, right?",
                                "Rain’s sideways. How is rain SIDEWAYS?",
                                "If I wasn’t so tired, I’d stargaze with you.",
                                "Stop working for once—come gossip with me!",
                                "I don’t deserve you. Seriously, thank you!",
                                "Wait—this is the exact one I’ve been saving up for! Did you read my mind?!"
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
                                "Don't forget to feed them before sunset!",
                                "I swear, my chickens hate rain more than foxes.",
                                "My sheep look like fluffy clouds now.",
                                "My chickens are hiding UNDER the coop. Smart.",
                                "My cows sleep standing up. Creepy, huh?",
                                "You look terrible. Long night? Let’s get coffee.",
                                "This is so thoughtful! You’re the best.",
                                "I’ve dropped subtle hints for months… You actually listened!"


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
                                "A good hammer is worth its weight.",
                                "Mud everywhere. My floors will never be clean.",
                                "Path’s icy—watch your step, farmer.",
                                "If my roof survives this, I’ll fix it. Maybe.",
                                "Early to bed, early to rise... or so they say.",
                                "Knew I’d find you here. Predictable as always.",
                                "Is it my birthday and I forgot? Either way, thanks!",
                                "This is so me. You get me better than my own family."
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
                                        "Ever built your own chair? Try it sometime.",
                                        "Rain means free water for the fields, at least.",
                                        "Winter’s long, but at least the pests are gone.",
                                        "Worst part? The noise. Can’t even nap.",
                                        "Stars are nice. Too bad I’m too tired to look.",
                                        "Was just about to hunt you down—help me with this?",
                                        "Aw, you didn’t have to! …But I love it.",
                                        "I was just about to buy this for myself! Now it’s extra special."
                                )),
                                new ArrayList<Item>(Arrays.asList(
                                        Food.Spaghetti,
                                        new Wood(),
                                        CraftableItem.IronBar
                                )),
                                new ArrayList<Slot>(Arrays.asList(
                                        new Slot(Food.Bread, 4),
                                        new Slot(Food.HashBrowns, 3),
                                        new Slot(Food.MinerTreat, 2)
                                )),
                                new ArrayList<Quest>(Arrays.asList(
                                        new Quest(new Slot(new Wood(), 80), 1000.0, "Wood is working", "Need some gold? Bring wood!", "Now that hits the spot."),
                                        new Quest(new Slot(CraftableItem.IronBar, 10), new Slot(CraftableMachine.BeeHouse,3), "Bees of iron", "Wanna see a bee House made by iron?", "cool right?"),
                                        new Quest(new Slot(new Wood(), 1000),25000.0, "More Wood is even working better", "Need more gold? Bring more wood", "I need a new store for this much wood")
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
                                "Let me know if you're buying in bulk!",
                                "My roof’s leaking again. Got any spare straw?",
                                "You ever eaten snow? Tastes like regret.",
                                "Sky’s angry today. What’d we do?",
                                "Ever seen a fox sneaking past midnight? Sneaky buggers.",
                                "There’s my favorite troublemaker!",
                                "You’re spoiling me. I might get used to it.",
                                "I’ll treasure this forever. Seriously—check my will, it’s in there now."
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
            }else if (building instanceof SebastienHome) {
                NPCs.add(new Sebastien("Sebastien", Occupation.Jobless, (SebastienHome) building,
                        new ArrayList<String>(Arrays.asList(
                                "Spring feels so fresh!",
                                "Summer days are long and warm.",
                                "Fall leaves are beautiful to watch.",
                                "Winter brings quiet and peace.",
                                "Ever tried dancing in the rain? ...No? Just me?",
                                "Kids love this. I just love my fireplace.",
                                "Stay inside unless you wanna fly to the next town.",
                                "Night’s for resting... unless you’re a weirdo.",
                                "Ugh, it’s you. Guess I’ll tolerate your company.",
                                "This is perfect! How’d you know I needed this?",
                                "Every time I use this, I’ll think of you. That’s a threat."
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                new Product(ProductType.SheepWool),
                                Food.PumpkinPie,
                                Food.Pizza
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(Food.MakiRoll, 3),
                                new Slot(Food.BakedFish, 2),
                                new Slot(CraftableItem.GoldBar, 1)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(
                                        new Slot(CraftableItem.IronBar, 50),
                                        new Slot(new Mineral(MineralType.Diamond),2),
                                        "switch bars",
                                        "I need some iron to build a new house",
                                        "50 is enough"
                                ),
                                new Quest(
                                        new Slot(Food.PumpkinPie, 1),
                                        5000.0,
                                        "Fresh Pumpkin",
                                        "Help me gather fresh Pumpkin!.",
                                        "Thanks a lot!"
                                ),
                                new Quest(
                                        new Slot(new Rock(), 150),
                                        new Slot(new Mineral(MineralType.Quartz), 50),
                                        "Rocky quest",
                                        "new year, new house, right?",
                                        "tnx mate"
                                )
                        ))
                        ));
            } else if (building instanceof LiaHome) {
                NPCs.add(new Lia("Lia", Occupation.Jobless, (LiaHome) building,
                        new ArrayList<String>(Arrays.asList(
                                "Springtime is when everything wakes up.",
                                "Summer is perfect for adventures.",
                                "Fall means cozy sweaters.",
                                "Winter nights are great for stories.",
                                "You’d think I’d be used to wet socks by now.",
                                "Bet my turnips are frozen solid under this.",
                                "My fence is gone. Just... gone.",
                                "Hear that? Owls. Means the mice are doomed.",
                                "You’re late. I already ate all the good snacks.",
                                "A gift? What’s the catch? …Just kidding, thank you!",
                                "This is the perfect version too! Did you stalk the shopkeeper for me?!"
                        )),
                        new ArrayList<Item>(Arrays.asList(
                                Food.Salad,
                                new Crop(CropType.Grape),
                                CraftableItem.Beer
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(Food.FruitSalad, 1),
                                new Slot(Food.Salad, 3),
                                new Slot(new Crop(CropType.AncientFruit),3)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(
                                        new Slot(new Wood(), 10),//todo add hard wood
                                        500.0,
                                        "Chopper",
                                        "Harvey just stole my door",
                                        "Thanks a lot!"
                                ),
                                new Quest(
                                        new Slot(new Fish(FishType.Salmon),1),
                                        CookingRecipe.SalmonDinner,
                                        "Salmon dinner",
                                        "i wanna show you how to cook salmon",
                                        "not that hard, right?"
                                ),
                                new Quest(
                                        new Slot(new Wood(), 200),
                                        new Slot(CraftableMachine.DeluxeScarecrow,3),
                                        "For Harvey and crows",
                                        "You bring more wood for my door ,I make some scarecrow for your farm",
                                        "Yummy trade!"
                                )
                        ))
                ));
            }else if (building instanceof HarveyHome) {
                NPCs.add(new Harvey("Harvey", Occupation.Jobless, (HarveyHome) building,
                        new ArrayList<String>(Arrays.asList(
                                "get out of here",
                                "take a shower some time you stinky ass",
                                "i wish you were one of these leaves below my shoe",
                                "i dont know witch is more annoying, winter or you",
                                "Ugh, my crops are drowning... hope yours are doing same.",
                                "My fingers are numb... why do I farm again?",
                                "ugh, I hate typhoon... and you",
                                "Moon’s bright enough to work, but I’d rather sleep.",
                                "Hey, you! Just the person I wanted to annoy today.",
                                "Cool. My pile of junk just got bigger.",
                                "I don’t deserve you. Or this. But I’m keeping both."

                        )),
                        new ArrayList<Item>(Arrays.asList(
                                CraftableItem.Coffee,
                                new Pickles(),
                                CraftableItem.Beer
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(new Wood(), 1),
                                new Slot(new Fish(FishType.Sunfish), 2),
                                new Slot(Food.Bread, 1)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(
                                        new Slot(new Crop(CropType.Blueberry),12),
                                        750.0,
                                        "small paradise",
                                        "my paradise looks so empty, do sth",
                                        "ok, no go away"
                                ),
                                new Quest(
                                        new Slot(new Fish(FishType.Salmon),1),
                                        200,
                                        "Salmon for dinner",
                                        "give me a salmon",
                                        "ok, from now on , you are not one of those dumbAsses"
                                ),
                                new Quest(
                                        new Slot(CraftableItem.Beer,1),
                                        new Slot(Food.Salad, 5),
                                        "the one-Player Party",
                                        "I`m having a party with mySelf, bring a bear",
                                        "i wish you were invited to, but i hate you"
                                )
                        ))
                ));
            }  else if (building instanceof AbigailHome) { // Assuming LiaHome exists
                NPCs.add(new Abigail("Abigail", Occupation.Jobless, (AbigailHome) building,
                        new ArrayList<String>(Arrays.asList(
                                "Spring feels so fresh!",
                                "Summer days are long and warm.",
                                "Fall leaves are beautiful to watch.",
                                "Winter brings quiet and peace.",
                                "Rain’s good for the soil, but not for my mood.",
                                "Snow’s pretty until you have to shovel it.",
                                "Hope your barn’s sturdier than my shed!",
                                "You farm at night? That’s dedication... or madness.",
                                "Look who’s here! My day just got better.",
                                "For me? You shouldn’t have! …But I’m glad you did.",
                                "You’re banned from being this thoughtful. My heart can’t take it."


                        )),
                        new ArrayList<Item>(Arrays.asList(
                                new Rock(),
                                new Mineral(MineralType.IronOre),
                                CraftableItem.Coffee
                        )),
                        new ArrayList<Slot>(Arrays.asList(
                                new Slot(new Mineral(MineralType.Ruby),3),
                                new Slot(Food.MakiRoll, 4),
                                new Slot(Food.Tortilla, 10)
                        )),
                        new ArrayList<Quest>(Arrays.asList(
                                new Quest(
                                        new Slot(CraftableItem.GoldBar, 1),
                                        200,
                                        "Gold for friendShip",
                                        "Sorry, i only talk to people whom are my friends",
                                        "hi, my new friend"
                                ),
                                new Quest(
                                        new Slot(Food.PumpkinPie,1),
                                        500.0,
                                        "Friends in need",
                                        "can you bring me a pumpkin pie?",
                                        "here"
                                ),
                                new Quest(
                                        new Slot(new Crop(CropType.Wheat), 50),
                                        new Slot(CraftableMachine.IridiumSprinkler,1),
                                        "for bread",
                                        "give me 50 wheat , you wont regret it",
                                        "oh gosh, you dont have a iridium Sprinkler?"
                                )
                        ))
                ));
            }









        }
        App.getGame().getVillage().setNPCs(NPCs);
    }

}
