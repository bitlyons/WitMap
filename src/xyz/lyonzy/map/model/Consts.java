package xyz.lyonzy.map.model;


public class Consts {

    static int noOfBuildings=0;
    static int currentBuilding;
    static boolean edit = false;
    static boolean added = false;


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
}
