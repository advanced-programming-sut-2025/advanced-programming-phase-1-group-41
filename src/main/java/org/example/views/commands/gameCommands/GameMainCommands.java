package org.example.views.commands.gameCommands;

import org.example.views.commands.Command;

import java.util.regex.Matcher;

public enum GameMainCommands implements Command {
    MenuEnter("\\s*menu\\s+enter\\s+(?<menuName>.*)\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    ShowCurrentMenu("\\s*menu\\s+show\\s+(?<menuName>.*)\\s*"),
    GameMap("\\s*game\\s+map\\s+(?<mapNumber>-?\\d+)\\s*"),
    ExitGame("\\s*exit\\s+game\\s*"),
    DeleteGame("\\s*delete\\s+game\\s*"),
    NextTurn("\\s*next\\s+turn\\s*"),
    Walk("\\s*walk\\s+-l\\s+(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*"),
    PrintMapReal("\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*"),
    PrintMapWhole("\\s*print\\s+whole\\s+map\\s*"),
    PrintMap("\\s*print\\s+map\\s*"),
    HelpReadingMap("\\s*help\\s+reading\\s+map\\s*"),
    EnergyShow("\\s*energy\\s+show\\s*"),
    MoneyShow("\\s*money\\s+show\\s*"),
    SkillShow("\\s*skill\\s+show\\s*"),
    EnergySet("\\s*energy\\s+set\\s+-v\\s+(?<value>-?\\d+)\\s*"),
    EnergyUnlimited("\\s*energy\\s+unlimited\\s*"),
    ShowCraftingRecipes("\\s*crafting\\s+show\\s+recipes\\s*"),
    CraftingCraft("\\s*crafting\\s+craft\\s+(?<itemName>.*)\\s*"),
    PlaceItem("\\s*place\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-d\\s+(?<direction>-?\\d+)\\s*"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-c\\s+(?<count>-?\\d+)\\s*"),
    CookingRefrigerator("\\s*cooking\\s+refrigerator\\s+(?<which>put|pick)\\s+(?<item>.+)\\s*"),
    ShowRefrigerator("\\s*cooking\\s+refrigerator\\s*"),
    CookingShowRecipes("\\s*cooking\\s+show\\s+recipes\\s*"),
    CookingPrepare("\\s*cooking\\s+prepare\\s+(?<recipeName>.+)\\s*"),
    Eat("\\s*eat\\s+(?<foodName>.*)\\s*"),
    Build("\\s*build -a\\s+(?<buildingName>.+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*"),
    Fishing("\\s*fishing\\s+-p\\s+(?<fishingPole>.+)\\s*"),
    ArtisanUse("\\s*artisan\\s+use\\s+(?<artisanName>\\S+)(?<items>\\s+.*)?\\s*"),
    ArtisanGet("\\s*artisan\\s+get\\s+(?<artisanName>.*)\\s*"),
    StartTrade("\\s*start\\s+trade\\s*"),
    WalkHome("\\s*walk\\s+-l\\s+home\\s*"),
    WalkVillage("\\s*walk\\s+-l\\s+village\\s*"),
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
