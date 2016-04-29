package xyz.lyonzy.map.model;


import java.util.ArrayList;

public class Consts {

    public static ArrayList<Room> rooms;
    static int noOfBuildings = 0;
    static int currentBuilding;
    static boolean edit = false;
    static boolean added = false;
    static Area currentArea;

    public static boolean isEdit() {
        return edit;
    }

    public static void setEdit(boolean edit) {
        Consts.edit = edit;
    }

    public static int getNoOfBuildings() {
        return noOfBuildings;
    }

    public static void setNoOfBuildings(int noOfBuildings) {
        Consts.noOfBuildings = noOfBuildings;
    }

    public static int getCurrentBuilding() {
        return currentBuilding;
    }

    public static Area getCurrentArea() {
        return currentArea;
    }

}
