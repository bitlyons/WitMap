package xyz.lyonzy.map.model;


public class Consts {
    static int noOfBuildings=0;
    static int currentBuilding;

    public static void CurrentBuilding(int currentBuilding) {
        Consts.currentBuilding = currentBuilding;
    }

    public static void setNoOfBuildings(int noOfBuildings) {
        Consts.noOfBuildings = noOfBuildings;
    }

    public static int getCurrentBuilding() {
        return currentBuilding;
    }
}
