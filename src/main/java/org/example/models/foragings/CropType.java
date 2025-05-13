package org.example.models.foragings;

import org.example.models.Season;

import java.util.ArrayList;
import java.util.Arrays;

public enum CropType {
    BlueJazz("BlueJazz", SeedType.BlueJazzSeed, new ArrayList<>(Arrays.asList(1, 2, 2, 3)), 7, true, -1, 50, true, 45, false, Season.Spring),
    Carrot("Carrot", SeedType.CarrotSeed,new ArrayList<>(Arrays.asList(1, 1, 1)), 3, true, -1, 35, true, 75, false, Season.Spring),
    Cauliflower("Cauliflower", SeedType.CauliflowerSeed, new ArrayList<>(Arrays.asList(1, 2, 4, 4, 1)), 12, true, -1, 175, true, 75, true, Season.Spring),
    CoffeeBean("CoffeeBean", SeedType.CoffeeBeanSeed, new ArrayList<>(Arrays.asList(1, 2, 2, 3, 2)), 10, false, 2, 15, false, -1, false, Season.Spring, Season.Summer),
    Garlic("Garlic", SeedType.GarlicSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 60, true, 20,  false, Season.Spring),
    GreenBean("GreenBean", SeedType.Bean,new ArrayList<>(Arrays.asList(1, 1, 1, 3, 4)), 10, false, 3, 40, true, 25,  false, Season.Spring),
    Kale("Kale", SeedType.KaleSeed, new ArrayList<>(Arrays.asList(1, 2, 2, 1)), 6, true, -1, 110, true, 50, false, Season.Spring),
    Parsnip("Parsnip", SeedType.ParsnipSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 35, true, 25,  false, Season.Spring),
    Potato("Potato", SeedType.PotatoSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)), 6, true, -1, 80, true, 25,  false, Season.Spring),
    Rhubarb("Rhubarb", SeedType.RhubarbSeed, new ArrayList<>(Arrays.asList(2, 2, 2, 3, 4)), 13, true, -1, 220, false, -1,  false, Season.Spring),
    Strawberry("Strawberry", SeedType.StrawberrySeed, new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2)), 8, false, 4, 120, true, 50, false, Season.Spring),
    Tulip("Tulip", SeedType.TulipSeed, new ArrayList<>(Arrays.asList(1, 1, 2, 2)), 6, true, -1, 30, true, 45,  false, Season.Spring),
    UnmilledRice("UnmilledRice", SeedType.Rice, new ArrayList<>(Arrays.asList(1, 2, 2, 3)), 8, true, -1, 30, true, 3, false, Season.Spring),
    Blueberry("Blueberry", SeedType.BlueberrySeed, new ArrayList<>(Arrays.asList(1, 3, 3, 4, 2)), 13, false, 4, 50, true, 25, false, Season.Summer),
    Corn("Corn", SeedType.CornSeed, new ArrayList<>(Arrays.asList(2, 3, 3, 3, 3)), 14, false, 4, 50, true, 25, false, Season.Summer, Season.Autumn),
    Hops("Hops", SeedType.HopsStarter, new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4)), 11, false, 1, 25, true, 45, false, Season.Summer),
    HotPepper("HotPepper", SeedType.HotPepperSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1)), 5, false, 3, 40, true, 13, false, Season.Summer),
    Melon("Melon", SeedType.MelonSeed, new ArrayList<>(Arrays.asList(1, 2, 3, 3, 3)), 12, true, -1, 250, true, 113, true, Season.Summer),
    Poppy("Poppy", SeedType.PoppySeed, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, -1, 140, true, 45, false, Season.Summer),
    Radish("Radish", SeedType.RadishSeed, new ArrayList<>(Arrays.asList(2, 1, 2, 1)), 6, true, -1, 90, true, 45, false, Season.Summer),
    RedCabbage("RedCabbage", SeedType.RedCabbageSeed, new ArrayList<>(Arrays.asList(2, 1, 2, 2, 2)), 9, true, -1, 260, true, 75, false, Season.Summer),
    Starfruit("Starfruit", SeedType.StarfruitSeed, new ArrayList<>(Arrays.asList(2, 3, 2, 3, 3)), 13, true, -1, 750, true, 125, false, Season.Summer),
    SummerSpangle("SummerSpangle", SeedType.SummerSpangleSeed, new ArrayList<>(Arrays.asList(1, 2, 3, 1)), 8, true, -1, 90, true, 45, false, Season.Summer),
    SummerSquash("SummerSquash", SeedType.SummerSquashSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)), 6, false, 3, 45, true, 63, false, Season.Summer),
    Sunflower("Sunflower", SeedType.SunflowerSeed, new ArrayList<>(Arrays.asList(1, 2, 3, 2)), 8, true, -1, 80, true, 45, false, Season.Summer, Season.Autumn),
    Tomato("Tomato", SeedType.TomatoSeed, new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3)), 11, false, 4, 60, true, 20, false, Season.Summer),
    Wheat("Wheat", SeedType.WheatSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 25, false, -1, false, Season.Summer, Season.Autumn),
    Amaranth("Amaranth", SeedType.AmaranthSeed, new ArrayList<>(Arrays.asList(1, 2, 2, 2)), 7, true, -1, 150, true, 50, false, Season.Autumn),
    Artichoke("Artichoke", SeedType.ArtichokeSeed, new ArrayList<>(Arrays.asList(2, 2, 1, 2, 1)), 8, true, -1, 160, true, 30, false, Season.Autumn),
    Beet("Beet", SeedType.BeetSeed, new ArrayList<>(Arrays.asList(1, 1, 2, 2)), 6, true, -1, 100, true, 30, false, Season.Autumn),
    BokChoy("BokChoy", SeedType.BokChoySeed, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 4, true, -1, 80, true, 25, false, Season.Autumn),
    Broccoli("Broccoli", SeedType.BroccoliSeed, new ArrayList<>(Arrays.asList(2, 2, 2, 2)), 8, false, 4, 70, true, 63, false, Season.Autumn),
    Cranberries("Cranberries", SeedType.CranberrySeed, new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2)), 7, false, 5, 75, true, 38, false, Season.Autumn),
    Eggplant("Eggplant", SeedType.EggplantSeed, new ArrayList<>(Arrays.asList(1, 1, 1, 1)), 5, false, 5, 60, true, 20, false, Season.Autumn),
    FairyRose("FairyRose", SeedType.FairyRoseSeed, new ArrayList<>(Arrays.asList(1, 4, 4, 3)), 12, true, -1, 290, true, 45, false, Season.Autumn),
    Grape("Grape", SeedType.GrapeStarter, new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3)), 10, false, 3, 80, true, 38, false, Season.Autumn),
    Pumpkin("Pumpkin", SeedType.PumpkinSeed, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 3)), 13, true, -1, 320, false, -1, true, Season.Autumn),
    Yam("Yam", SeedType.Yam, new ArrayList<>(Arrays.asList(1, 3, 3, 3)), 10, true, -1, 160, true, 45, false, Season.Autumn),
    SweetGemBerry("SweetGemBerry", SeedType.RareSeed, new ArrayList<>(Arrays.asList(2, 4, 6, 6, 6)), 24, true, -1, 3000, false, -1, false, Season.Autumn),
    Powdermelon("Powdermelon", SeedType.PowdermelonSeed, new ArrayList<>(Arrays.asList(1, 2, 1, 2, 1)), 7, true, -1, 60, true, 63, true, Season.Winter),
    AncientFruit("AncientFruit", SeedType.AncientSeed, new ArrayList<>(Arrays.asList(2, 7, 7, 7, 5)), 28, false, 7, 550, false, -1, false, Season.Spring, Season.Summer, Season.Autumn);

    private final String Name;
    private final SeedType source;
    private final ArrayList<Integer> stages;
    private final int HarvestTime;
    private final boolean OneTimeHarvest;
    private final int RegrowthTime;
    private final int BaseSellPrice;
    private final boolean IsEatable;
    private final int Energy;
    private final boolean canBecomeGiant;
    private final Season[] GrowingSeasons;

    CropType(String name, SeedType source, ArrayList<Integer> stages, int harvestTime, boolean oneTimeHarvest, int regrowthTime, int baseSellPrice, boolean isEatable, int energy, boolean canBecomeGiant, Season... growingSeasons) {
        this.Name = name;
        this.source = source;
        this.stages = stages;
        this.HarvestTime = harvestTime;
        this.OneTimeHarvest = oneTimeHarvest;
        this.RegrowthTime = regrowthTime;
        this.BaseSellPrice = baseSellPrice;
        this.IsEatable = isEatable;
        this.Energy = energy;
        this.canBecomeGiant = canBecomeGiant;
        this.GrowingSeasons = growingSeasons;
    }

    public String getName() { return Name; }
    public SeedType getSource() { return source; }
    public ArrayList<Integer> getStages() { return stages; }
    public int getHarvestTime() { return HarvestTime; }
    public boolean isOneTimeHarvest() { return OneTimeHarvest; }
    public int getRegrowthTime() { return RegrowthTime; }
    public int getBaseSellPrice() { return BaseSellPrice; }
    public boolean isEatable() { return IsEatable; }
    public int getEnergy() { return Energy; }
    public boolean canBecomeGiant() { return canBecomeGiant; }
    public Season[] getGrowingSeasons() { return GrowingSeasons; }

    @Override
    public String toString() {
        StringBuilder stages = new StringBuilder();
        ArrayList<Integer> stagesList = getStages();
        for (int i = 0; i < stagesList.size(); i++) {
            if(i == stagesList.size() - 1) {
                stages.append(stagesList.get(i));
            } else{
                stages.append(stagesList.get(i)).append("-");
            }
        }
        StringBuilder result = new StringBuilder();
        String res;
        result.append("Name: ").append(getName()).append("\n");
        result.append("Source: ").append(getSource().getName()).append("\n");
        result.append("Stages: ").append(stages).append("\n");
        result.append("Total Harvest Time: ").append(getHarvestTime()).append("\n");
        result.append("One Time: ").append(isOneTimeHarvest()).append("\n");
        res = String.valueOf(getRegrowthTime());
        if(getRegrowthTime() == -1){
            res = "-";
        }
        result.append("Regrowth Time: ").append(res).append("\n");
        result.append("Base Sell Price: ").append(getBaseSellPrice()).append("\n");
        result.append("Is Edible: ").append(isEatable()).append("\n");
        res = String.valueOf(getEnergy());
        if(getEnergy() == -1){
            res = "-";
        }
        result.append("Base Energy: ").append(res).append("\n");
        res = String.valueOf(getEnergy() / 2);
        if(getEnergy() == -1){
            res = "-";
        }
        result.append("Base Health: ").append(res).append("\n");
        result.append("Season: ").append(getSource().getSeason()).append("\n");
        result.append("Can Become Giant: ").append(canBecomeGiant()).append("\n");
        return result.toString();
    }

    public static CropType parseCropType(String cropType) {
        for (CropType value : CropType.values()) {
            if(value.getName().equalsIgnoreCase(cropType)) {
                return value;
            }
        }
        return null;
    }
}
