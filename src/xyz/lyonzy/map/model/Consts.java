package xyz.lyonzy.map.model;


public class Consts {

    static int noOfBuildings=0;
    static int currentBuilding;

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
