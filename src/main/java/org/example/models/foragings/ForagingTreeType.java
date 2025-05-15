package org.example.models.foragings;

import org.example.models.Season;
import org.example.models.foragings.Nature.TreeType;

public enum ForagingTreeType {
    Oak(TreeType.Oak),
    Maple(TreeType.Maple),
    Pine(TreeType.Pine),
    Mahogany(TreeType.Mahogany),
    Mushroom(TreeType.Mushroom);
//    Mystic(TreeType.Mystic);

    private final TreeType treeType;

    ForagingTreeType(TreeType treeType){
        this.treeType = treeType;
    }
    public TreeType getTreeType() {
        return treeType;
    }
}
