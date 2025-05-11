package org.example.controllers;

import org.example.models.*;
import org.example.models.buildings.Nature.Mineral;
import org.example.models.buildings.Nature.Rock;
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
        int x = App.getGame().getCurrentPlayer().getX()+dirs[dir][0];
        int y = App.getGame().getCurrentPlayer().getY()+dirs[dir][1];
        return new Result(true, "x: "+x+" y: "+y);
    }

    public Result useTool(Matcher matcher){
        Result preResult = preValidateUseTool(matcher);
        if (!preResult.success()){
            return preResult;
        }
        System.out.println(preResult);

        Cell cell = Finder.findCellByCoordinates(x,y,App.getGame().getCurrentPlayerFarm());
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

            if(cell.getObjectMap() instanceof Mineral){
                App.getGame().getCurrentPlayer().getInventory().addToInventory(
                        (Item) cell.getObjectMap(), 1
                );
                App.getGame().getCurrentPlayer().decEnergy(energy);
                return new Result(true, "got a "+((Item)cell.getObjectMap()).getName());
            }else if(cell.getObjectMap() instanceof Rock){
                App.getGame().getCurrentPlayer().getInventory().addToInventory(
                        (Item) cell.getObjectMap(), 1
                );
                App.getGame().getCurrentPlayer().decEnergy(energy);
                return new Result(true, "got a "+((Item)cell.getObjectMap()).getName());
            }else{
                App.getGame().getCurrentPlayer().decEnergy(Math.max(0,energy-1));
                return new Result(false,"fck this sht");
            }
        }



        return new Result(false,"unable to use this ig");


    }


}
