package xyz.lyonzy.map.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Brendan Lyons on 24/04/2016.
 * This file deals with connecting the database, and all the possible statements that the program can make to it
 */
public class Database {
    private String username = "root";
    private String password = "root";
    private String host = "127.0.0.1";
    private String database = "witmap";
    private String dbURL = "jdbc:mysql://" + host + "/" + database + "?user="
            + username + "&password=" + password;
    private Statement stat;
    private java.sql.Connection myConnection;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            myConnection = DriverManager.getConnection(dbURL);
        } catch (Exception e) {
            System.out.println("unable to connect to database");
            //System.out.println(e.getMessage());
        }
    }

    public Building getBuilding(int buildingId) throws Exception {

        stat = myConnection.createStatement();
        String selectQuery = "Select * from building  JOIN image ON  building.image = image.iId Where bId =" + buildingId;
        ResultSet resultSet = stat.executeQuery(selectQuery);
        resultSet.next();
        return new Building(resultSet.getInt("bId"), resultSet.getString("bName"), resultSet.getString("bOpeningHours"),
                resultSet.getString("bInfo"), resultSet.getString("iURL"));
    }

    public boolean deleteBuilding(int bId) {
        try {
            stat = myConnection.createStatement();
            String deleteStatement = "Delete from Building where bId= " + bId;
            return stat.executeUpdate(deleteStatement) == 1;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean deleteArea(int aId) {
        try {
            stat = myConnection.createStatement();
            String deleteStatement = "Delete from area where aId= " + aId;
            return stat.executeUpdate(deleteStatement) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public int numberOfAreas() {
        try {
            stat = myConnection.createStatement();
            String selectQuery = "Select COUNT(*) as areas from area ";
            ResultSet resultSet = stat.executeQuery(selectQuery);
            resultSet.next();
            return resultSet.getInt("areas");
        } catch (Exception e) {
            System.out.println("Error With Database");
            return 0;
        }
    }

    public Area getArea(int area) throws Exception {
        stat = myConnection.createStatement();
        String selectQuery = "Select * from area where aId = " + area;
        ResultSet resultSet = stat.executeQuery(selectQuery);
        resultSet.next();
        return new Area(resultSet.getDouble("x"), resultSet.getDouble("y"), resultSet.getDouble("height"),
                resultSet.getDouble("width"), resultSet.getInt("aId"));
    }

    public int addArea(Area area) throws Exception {

        String insertStatement = "Insert into area (aId ,x, y, height, width) values (" + area.getBuildingNo() + ","
                + area.getX() + "," + area.getY() + "," + area.getHeight() + "," + area.getWidth() + ")";
        int success = stat.executeUpdate(insertStatement);
        System.out.println("Success/Status: " + success);
        return success;
    }

    public int updateArea(int aId, double x, double y, double height, double width) throws Exception {

        String insertStatement = "Update area set x=" + x + ",y=" + y + ",height = " + height +
                ",width = " + width + " WHERE aId = " + aId;
        int success = stat.executeUpdate(insertStatement);
        System.out.println("Success/Status: " + success);
        return success;
    }

}
