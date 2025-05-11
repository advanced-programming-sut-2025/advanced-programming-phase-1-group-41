package org.example.controllers;

import org.example.models.*;
import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.RockType;
import org.example.models.items.Item;
import org.example.models.tools.Pickaxe;
import org.example.models.tools.Tool;

import java.util.regex.Matcher;

public class ToolsController {
    int x;
    int y;
    public Result equipTool(Matcher matcher){
        String toolName = matcher.group(1).trim();
        Tool tool = Finder.getToolByName(toolName);
        if(tool == null){
            return new Result(false, "Tool " +
                    toolName +
                    " not found");
        }
        App.getGame().getCurrentPlayer().setCurrentTool(tool);
        return new Result(true, "current tool is set to "+ tool.getName());
    }

    public Result showCurrentTool(Matcher matcher){
        Tool tool = App.getGame().getCurrentPlayer().getCurrentTool();
        if(tool == null){
            return new Result(false, "you don't have any tool equipped");
        }
        return new Result(true, "current tool is set to "+ tool.getName());
    }

    public Result showAvailableTools(Matcher matcher){
        StringBuilder message = new StringBuilder();
        message.append("Available tools:\n");
        App.getGame().getCurrentPlayer().getInventory().getSlots().forEach(slot -> {
            if(slot.getItem() instanceof Tool){
                message.append(slot.getItem().getName()).append(", ");
            }
        });
        message.delete(message.length()-2, message.length());
        return new Result(true, message.toString());
    }

    public Result upgradeTool(Matcher matcher){return null;}

    public Result preValidateUseTool(Matcher matcher){
        String dirName = matcher.group(1).trim();
        int dir = Integer.parseInt(dirName)-1;
        if(dir < 0 || dir > 7){
            return new Result(false, "invalid dir");
        }
        int [][]dirs = {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},};
        x = App.getGame().getCurrentPlayer().getX()+dirs[dir][0];
        y = App.getGame().getCurrentPlayer().getY()+dirs[dir][1];
        return new Result(true, "x: "+x+" y: "+y);
    }

    public Result useTool(Matcher matcher){
        Result preResult = preValidateUseTool(matcher);
        if (!preResult.success()){
            return preResult;
        }
        System.out.println(preResult);

        Cell cell = Finder.findCellByCoordinates(x,y,App.getGame().getCurrentPlayerFarm());
        System.out.println(x+" "+y);
        if(cell == null){
            return new Result(false, "Cell not found");
        }
        Tool tool = App.getGame().getCurrentPlayer().getCurrentTool();
        if(tool == null){
            return new Result(false, "you don't have any tool equipped");
        }

        if(tool instanceof Pickaxe){
            Pickaxe pickaxe = (Pickaxe) tool;
            int energy = 0;
            switch (pickaxe.getLevel()){
                case Default -> energy = 5;
                case Copper -> energy = 4;
                case Iron -> energy = 3;
                case Gold -> energy = 2;
                case Iridium -> energy = 1;
            }
            // todo check the mining level

            System.out.println(cell.getObjectMap().getClass());
            if(cell.getObjectMap() instanceof Mineral){
                App.getGame().getCurrentPlayer().getInventory().addToInventory(
                        (Item) cell.getObjectMap(), 1
                );
                String name = ((Item)cell.getObjectMap()).getName();
                App.getGame().getCurrentPlayer().decEnergy(energy);
                cell.setObjectMap(new Mine(x,y,App.getGame().getCurrentPlayerFarm(),12121212));
                return new Result(true, "got a "+name);
            }else if(cell.getObjectMap() instanceof Rock){
                ((Rock) cell.getObjectMap()).decreaseHitPoints();
                if(((Rock) cell.getObjectMap()).getHitPoints() == 0){
                    if(((Rock) cell.getObjectMap()).getRockType().equals(RockType.BigRock)){
                        App.getGame().getCurrentPlayer().getInventory().addToInventory(
                                (Item) cell.getObjectMap(), 4
                        );
                    }else{
                        App.getGame().getCurrentPlayer().getInventory().addToInventory(
                                (Item) cell.getObjectMap(), 1
                        );
                    }
                    App.getGame().getCurrentPlayer().decEnergy(energy);
                    String namemeeme = ((Item)cell.getObjectMap()).getName();
                    if(((Rock) cell.getObjectMap()).getRockType().equals(RockType.BigRock)){
                        Cell cell2 = Finder.findCellByCoordinates(x + 1, y,App.getGame().getCurrentPlayerFarm());
                        assert cell2 != null;
                        cell2.setObjectMap(new Grass());
                        Cell cell3 = Finder.findCellByCoordinates(x + 1, y + 1,App.getGame().getCurrentPlayerFarm());
                        assert cell3 != null;
                        cell3.setObjectMap(new Grass());
                        Cell cell4 = Finder.findCellByCoordinates(x, y + 1,App.getGame().getCurrentPlayerFarm());
                        assert cell4 != null;
                        cell4.setObjectMap(new Grass());
                    }
                    cell.setObjectMap(new Grass());
                    return new Result(true, "got a "+namemeeme);
                } else{
                    if(cell.getObjectMap() instanceof Rock && !cell.getObjectMap().getName().equals(new Grass().getName())){
                        return new Result(true, "Hits Left: "+((Rock) cell.getObjectMap()).getHitPoints());
                    }
                    return new Result(true,"broke");
                }
            }else{
                App.getGame().getCurrentPlayer().decEnergy(Math.max(0,energy-1));
                return new Result(false,"fck this sht");
            }
        }



        return new Result(false,"unable to use this ig");


    }


}
