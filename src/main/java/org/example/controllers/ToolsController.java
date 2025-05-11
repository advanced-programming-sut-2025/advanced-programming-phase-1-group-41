package org.example.controllers;

import org.example.models.App;
import org.example.models.Finder;
import org.example.models.Result;
import org.example.models.tools.Tool;

import java.util.regex.Matcher;

public class ToolsController {
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

    public Result useTool(Matcher matcher){return null;}


}
