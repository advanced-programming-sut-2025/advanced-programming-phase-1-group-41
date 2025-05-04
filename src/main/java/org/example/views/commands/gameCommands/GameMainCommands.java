package org.example.views.commands.gameCommands;

import org.example.views.commands.Command;

import java.util.regex.Matcher;

public enum GameMainCommands implements Command {
    MenuEnter("\\s*menu\\s+enter\\s+(?<menuName>.*)\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    ShowCurrentMenu("\\s*menu\\s+show\\s+(?<menuName>.*)\\s*"),
    NewGame("\\s*game\\s+new\\s+-u\\s+(?<username1>.*)\\s+(?<username2>.*)\\s+(?<username3>.*)\\s*"),
    GameMap("\\s*game\\s+map\\s+(?<mapNumber>.*)\\s*"),
    LoadGame("\\s*load\\s+game\\s*"),
    ExitGame("\\s*exit\\s+game\\s*"),
    NextTurn("\\s*next\\s+turn\\s*"),
    GreenhouseBuild("\\s*greenhouse\\s+build\\s*"),
    Walk("\\s*walk\\s+-l\\s+(?<x>.*)\\s*,\\s*(?<y>.*)\\s*"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+(?<x>.*)\\s*,\\s*(?<y>.*)\\s+-s\\s+(?<size>.*)\\s*"),
    HelpReadingMap("\\s*help\\s+reading\\s+map\\s*"),
    EnergyShow("\\s*energy\\s+show\\s*"),
    EnergySet("\\s*energy\\s+set\\s+-v\\s+(?<value>.*)\\s*"),
    EnergyUnlimited("\\s*energy\\s+unlimited\\s*"),
    Plant("\\s*plant\\s+-s\\s+(?<seed>.*)\\s+-d\\s+(?<direction>.*)\\s*"),
    ShowPlant("\\s*showplant\\s+-l\\s+(?<x>.*)\\s*,\\s*(?<y>.*)\\s*"),
    Fertilize("\\s*fertilize\\s+-f\\s+(?<fertilizer>.*)\\s+-d\\s+(?<direction>.*)\\s*"),
    HowMuchWater("\\s*howmuch\\s+water\\s*"),
    ShowCraftingRecipes("\\s*crafting\\s+show\\s+recipes\\s*"),
    CraftingCraft("\\s*crafting\\s+craft\\s+(?<itemName>.*)\\s*"),
    PlaceItem("\\s*place\\s+item\\s+-n\\s+(?<itemName>.*)\\s+-d\\s+(?<direction>.*)\\s*"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.*)\\s+-c\\s+(?<count>.*)\\s*"),
    CookingRefrigerator("\\s*cooking\\s+refrigerator\\s+(put|pick)\\s+(?<item>.*)\\s*"),
    CookingShowRecipes("\\s*cooking\\s+show\\s+recipes\\s*"),
    CookingPrepare("\\s*cooking\\s+prepare\\s+(?<recipeName>.*)\\s*"),
    Eat("\\s*eat\\s+(?<foodName>.*)\\s*"),
    Build("\\s*build\\s+(?<buildingName>.*)\\s+-l\\s+(?<x>.*)\\s*,\\s*(?<y>.*)\\s*"),
    Produces("\\s*produces\\s*"),
    CollectProduce("\\s*collect\\s+produce\\s+-n\\s+(?<name>.*)\\s*"),
    Fishing("\\s*fishing\\s+-p\\s+(?<fishingPole>.*)\\s*"),
    ArtisanUse("\\s*artisan\\s+use\\s+(?<artisanName>.*)\\s+(?<item1Name>.*)\\s*"),
    ArtisanGet("\\s*artisan\\s+get\\s+(?<artisanName>.*)\\s*"),
    ;

    private final String pattern;

    GameMainCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
