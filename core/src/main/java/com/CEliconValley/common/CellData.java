package com.CEliconValley.common;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Bridge;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.GreenHouse.WaterTank;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.foragings.*;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.items.Item;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.HashMap;


@Embedded
public class CellData {

    int x;
    int y;
    String objectName;
    String className = null;
    HashMap<String, Object> data;

    public CellData() {
    }

    public CellData(Cell cell) {
        data = new HashMap<>();
        this.x = cell.getX();
        this.y = cell.getY();
        this.objectName = cell.getObjectMap().getName();
        fillData(cell);
    }

    private void fillData(Cell cell){
        if(cell.getObjectMap() instanceof ForagingTree f){
            data.put("hitPoints", f.getHitPoints());
            data.put("typeIndex", f.getTypeIndex());
            data.put("isThundered", f.isThundered());
            this.className = f.getClass().getName();
        }
        else if(cell.getObjectMap() instanceof Tree t){
            data.put("hitPoints", t.getHitPoints());
            data.put("typeIndex", t.getTypeIndex());
            data.put("currentStage", t.getCurrentStage());
            data.put("currentStageLevel", t.getCurrentStageLevel());
            data.put("isWateredToday", t.isWateredToday());
            data.put("waterStreak", t.getWaterStreak());
            data.put("isFertilizedToday", t.isFertilizedToday());
            data.put("isProtected", t.isProtected());
            data.put("isThundered", t.isThundered());
            data.put("isAttacked", t.isAttacked());
            data.put("x", t.getX());
            data.put("y", t.getY());
            this.className = t.getClass().getName();
        }
        else if(cell.getObjectMap() instanceof ForagingCrop fc){
            data.put("typeIndex", fc.getTypeIndex());
            this.className = fc.getClass().getName();
        }
        else if(cell.getObjectMap() instanceof Crop c){
            data.put("canRegrow", c.getCanRegrow());
            data.put("currentStage", c.getCurrentStage());
            data.put("currentStageLevel", c.getCurrentStageLevel());
            data.put("isFertilizedToday", c.isFertilizedToday());
            data.put("isGiantCrop", c.isGiantCrop());
            data.put("isProtected", c.isProtected());
            data.put("isWateredToday", c.isWateredToday());
            data.put("regrowthTime", c.getRegrowthTime());
            data.put("stages", c.getStages());
            data.put("typeIndex", c.getTypeIndex());
            data.put("waterStreak", c.getWaterStreak());
            data.put("x", c.getX());
            data.put("y", c.getY());
            this.className = c.getClass().getName();
        }
        else if(cell.getObjectMap() instanceof Rock r){
            data.put("hitPoints", r.getHitPoints());
            data.put("rockType", r.getRockType().ordinal());
            data.put("variant", r.getVariant());
            data.put("anchorX", r.getAnchorX());
            data.put("anchorY", r.getAnchorY());
        }
        else if(cell.getObjectMap() instanceof Grass grass){
            data.put("isFarmland", grass.isFarmland());
            data.put("isGround", grass.isGround());
            data.put("isSand", grass.isSand());
            data.put("isThundered", grass.isThundered());
            data.put("isBombed", grass.isBombed());
        }
    }
    public Cell extractData(){
        TreeType treeType= TreeType.parseTreeType(this.objectName);
        if(treeType != null){
            if(ForagingTree.class.getName().equals(className)){
                ForagingTree ft = new ForagingTree(treeType, getInt(data.get("hitPoints")),
                        getInt(data.get("typeIndex")),
                        (Boolean) data.get("isThundered"));
                Cell newCell = new Cell(ft, x, y);
                return newCell;
            }else if(Tree.class.getName().equals(className)){
                Tree t = new Tree(getInt(data.get("currentStage")),
                    getInt(data.get("currentStageLevel")), getInt(data.get("hitPoints")),
                    (boolean) data.get("isAttacked"), (boolean) data.get("isFertilizedToday"),
                    (boolean) data.get("isProtected"), (boolean) data.get("isThundered"),
                    (boolean) data.get("isWateredToday"), treeType,
                    getInt(data.get("typeIndex")), getInt(data.get("waterStreak")),
                    getInt(data.get("x")), getInt(data.get("y")));
                Cell newCell = new Cell(t, x, y);
                return newCell;
            }
        }
        ForagingCropType foragingCropType = ForagingCropType.parseForagingCropType(this.objectName);
        if(foragingCropType != null){
            ForagingCrop fc = new ForagingCrop(foragingCropType, getInt(data.get("typeIndex")));
            Cell newCell = new Cell(fc, x, y);
            return newCell;
        }
        CropType cropType = CropType.parseCropType(this.objectName);
        if(cropType != null){
            ArrayList<Integer> stages = getIntegerList(data.get("stages")) ;
            Crop c = new Crop((boolean) data.get("canRegrow"),
                    cropType, getInt(data.get("currentStage")),
                getInt(data.get("currentStageLevel")), (boolean) data.get("isFertilizedToday"),
                (boolean) data.get("isGiantCrop"), (boolean) data.get("isProtected") ,
                (boolean) data.get("isWateredToday"), getInt(data.get("regrowthTime")),
                stages, getInt(data.get("typeIndex")) ,
                getInt(data.get("waterStreak")), getInt(data.get("x")),
                    getInt(data.get("y")));
            Cell newCell = new Cell(c, x, y);
            return newCell;
        }
        if(this.objectName.equals(new Rock().getName())){
            int rockTypeInt = getInt(data.get("rockType"));
            RockType rockType = RockType.values()[rockTypeInt];
            Rock r = new Rock(getInt(data.get("hitPoints")),
                    rockType,
                    getInt(data.get("variant")), getInt(data.get("anchorX")), getInt(data.get("anchorY")));
            Cell newCell = new Cell(r, x, y);
            return newCell;
        }
        if(this.objectName.equals(new Grass().getName())){
            Grass g = new Grass((boolean) data.get("isBombed"), (boolean) data.get("isFarmland"),
                (boolean) data.get("isGround"), (boolean) data.get("isSand"),
                (boolean) data.get("isThundered"));
            Cell newCell = new Cell(g, x, y);
            return newCell;
        }
        if(this.objectName.equals(new Lake().getName())){
            return new Cell(new Lake(), x, y);
        }
        if(this.objectName.equals(new Bridge().getName())){
            return new Cell(new Bridge(), x, y);
        }
        if(this.objectName.equals(new Bush().getName())){
            return new Cell(new Bush(), x, y);
        }
        if(this.objectName.equals(new Plant().getName())){
            return new Cell(new Plant(), x, y);
        }
        if(this.objectName.equals(new WaterTank().getName())){
            return new Cell(new WaterTank(), x, y);
        }if(this.objectName.equals(new Mine().getName())){
            return new Cell(new Mine(), x, y);
        }
        Building building = Building.parseBuilding(this.objectName);
        if(building != null){
            return new Cell(building, x, y);
        }
        // TODO
        Item item = Finder.parseItem(this.objectName);
        if(item == null){
            System.out.println("null : "+this.objectName+" "+x+" "+y);
        }
        return new Cell(item, x, y);
    }


    public static int getInt(Object value) {
        if (value instanceof Double) {
            return ((Double) value).intValue(); // trims decimals
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else {
            throw new IllegalArgumentException("Unexpected value type: " + value);
        }
    }
    public static ArrayList<Integer> getIntegerList(Object value) {
        ArrayList<Integer> result = new ArrayList<>();
        if (value instanceof ArrayList<?>) {
            for (Object item : (ArrayList<?>) value) {
                if (item instanceof Double) {
                    result.add(((Double) item).intValue());
                } else if (item instanceof Integer) {
                    result.add((Integer) item);
                } else {
                    throw new IllegalArgumentException("Unexpected list item type: " + item.getClass().getName());
                }
            }
        } else {
            throw new IllegalArgumentException("Expected ArrayList but got " + value.getClass().getName());
        }
        return result;
    }


    public String getClassName() {
        return className;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
