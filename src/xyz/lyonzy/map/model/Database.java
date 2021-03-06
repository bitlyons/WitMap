package xyz.lyonzy.map.model;

import xyz.lyonzy.map.misc.Alerts;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Brendan Lyons on 24/04/2016.
 * This file deals with connecting the database, and all the possible statements that the program can make to it
 */
@SuppressWarnings("ALL")
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
            Alerts.databaseFail();
            System.exit(0);
        }
    }

    public ArrayList<Building> buildingNames() {
        try {
            ArrayList<Building> names = new ArrayList<>();
            stat = myConnection.createStatement();
            String selectStatment = "Select * from building JOIN image ON  building.image = image.iId";
            ResultSet building = stat.executeQuery(selectStatment);

            while (building.next()) {
                names.add(new Building(building.getInt("bId"), building.getString("bName"), building.getString("bOpeningHours"),
                        building.getString("bInfo"), building.getString("iURL"), building.getInt("iId")));
            }
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Building getBuilding(int buildingId) throws Exception {
        try {
            stat = myConnection.createStatement();
            String selectQuery = "Select * from building  JOIN image ON  building.image = image.iId Where bId =" + buildingId;
            ResultSet resultSet = stat.executeQuery(selectQuery);
            resultSet.next();
            return new Building(resultSet.getInt("bId"), resultSet.getString("bName"), resultSet.getString("bOpeningHours"),
                    resultSet.getString("bInfo"), resultSet.getString("iURL"), resultSet.getInt("iId"));
        } catch (Exception e) {
            String selectQuery = "Select * from building Where bId =" + buildingId;
            ResultSet resultSet = stat.executeQuery(selectQuery);
            resultSet.next();
            return new Building(resultSet.getInt("bId"), resultSet.getString("bName"), resultSet.getString("bOpeningHours"),
                    resultSet.getString("bInfo"), "", resultSet.getInt("image"));
        }
    }

    public int addBuilding(Building building) throws Exception {
        stat = myConnection.createStatement();
        String insertStatement = "Insert into building (bId ,aId, bName, bInfo, bOpeningHours) values (" +
                building.getBuildingNo() + ", " +
                building.getBuildingNo() + ", \"" + building.getBuildingName() + "\", \"" +
                building.getBuildingInfo() + "\" , \"" + building.getOpeningHours() + "\" )";
        int success = stat.executeUpdate(insertStatement);


        insertStatement = "Insert into image (buId, iURL) values (" + building.getBuildingNo() + "," + "\"def" +
                building.getBuildingNo() + "\")";
        int success2 = stat.executeUpdate(insertStatement);


        String selectStatment = "Select * from image where iURL= \"" + "def" +
                building.getBuildingNo() + "\"";
        ResultSet x = stat.executeQuery(selectStatment);
        x.next();
        int id = x.getInt("iId");

        /* insertStatement = "UPDATE building set image = (SELECT iId from image where iURL =\"" + "def" +
                building.getBuildingNo() + "\") where bId =" + building.getBuildingNo(); */
        insertStatement = "UPDATE building set image =" + id + " WHERE bId = '" + building.getBuildingNo() + "'";
        int success3 = stat.executeUpdate(insertStatement);


        return (success + success2 + success3) / 3;
    }


    public int updateBuilding(int bId, String bName, String bInfo, String bOpeningHours, String image) {

        try {
            stat = myConnection.createStatement();
            String insertStatement = "Update building set bName='" + bName + "' ,bInfo = '" + bInfo +
                    "',bOpeningHours = '" + bOpeningHours + "' WHERE bId = " + bId;

            int success = stat.executeUpdate(insertStatement);
            Building temp = getBuilding(bId);

            String insertImage = "Update image set iURL = \"" + image + "\" WHERE  iId= " + temp.getImageRef();
            int success2 = stat.executeUpdate(insertImage);

            return (success + success2) / 2;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }


    public boolean deleteBuilding(int bId) {
        try {
            stat = myConnection.createStatement();
            String deleteStatement = "Delete from Building where bId= " + bId;
            stat.executeUpdate(deleteStatement);
            deleteStatement = "Delete from room where bId= " + bId;
            return stat.executeUpdate(deleteStatement) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addMainImage(int buId, String iURL) {
        try {
            stat = myConnection.createStatement();
            String deleteStatement = "Insert into Image (buId, iURL) values (" + buId + ", iURL = '" + iURL + "')";
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


    public ResultSet getAllArea() {
        try {
            stat = myConnection.createStatement();
            String selectQuery = "Select * from area ";
            return stat.executeQuery(selectQuery);
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }


    public int addArea(Area area) throws Exception {

        String insertStatement = "Insert into area (aId ,x, y, height, width) values (" + area.getBuildingNo() + ","
                + area.getX() + "," + area.getY() + "," + area.getHeight() + "," + area.getWidth() + ")";
        int success = stat.executeUpdate(insertStatement);
        return success;
    }

    public int updateArea(int aId, double x, double y, double height, double width) throws Exception {

        String insertStatement = "Update area set x=" + x + ",y=" + y + ",height = " + height +
                ",width = " + width + " WHERE aId = " + aId;
        int success = stat.executeUpdate(insertStatement);
        return success;
    }

    public ArrayList<Room> getRooms(int bId) {
        try {
            stat = myConnection.createStatement();
            String selectStatment = "Select * from room where bId = " + bId;
            ResultSet rooms = stat.executeQuery(selectStatment);
            ArrayList<Room> roomsList = new ArrayList<>();
            while (rooms.next()) {
                roomsList.add(new Room(rooms.getInt("rId"), rooms.getInt("bId"), rooms.getString("rName")));
            }
            return roomsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int updateRoom(String oldRoomName, String roomName) {
        try {
            stat = myConnection.createStatement();
            String insertStatement = "Update room set rName = \"" + roomName + "\" where rName =\"" + oldRoomName + "\"";
            return stat.executeUpdate(insertStatement);
        } catch (Exception e) {
            System.out.println("error");
        }
        return 0;
    }

    public boolean addRoom(int bId, String rName) {
        try {
            stat = myConnection.createStatement();
            String deleteStatement = "Insert into room (bId, rName) values (" + bId + ", \"" + rName + "\")";
            return stat.executeUpdate(deleteStatement) == 1;
        } catch (Exception e) {
            return false;
        }
    }


    public int deleteRoom(String oldRoomName) {
        try {
            stat = myConnection.createStatement();
            String insertStatement = "Delete from room where rName= \"" + oldRoomName + "\"";
            return stat.executeUpdate(insertStatement);
        } catch (Exception e) {
            System.out.println("error");
        }
        return 0;
    }

    public ArrayList getAllImages() {
        try {
            stat = myConnection.createStatement();
            String selectStatment = "Select * from image";
            ResultSet images = stat.executeQuery(selectStatment);
            ArrayList<String> imageList = new ArrayList<>();
            while (images.next()) {
                imageList.add(images.getString("iURL"));
            }
            return imageList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getImages(int bid) {
        try {
            stat = myConnection.createStatement();
            String selectStatment = "Select * from image where buId = " + bid;
            ResultSet images = stat.executeQuery(selectStatment);
            ArrayList<String> imageList = new ArrayList<>();
            while (images.next()) {
                imageList.add(images.getString("iURL"));
            }
            return imageList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addImage(int bId, String iURL) {
        try {
            stat = myConnection.createStatement();
            String deleteStatement = "Insert into image (buId, iURL) values (" + bId + ", \"" + iURL + "\")";
            return stat.executeUpdate(deleteStatement) == 1;
        } catch (Exception e) {
            return false;
        }
    }


    public int deleteImage(String oldUrl) {
        try {
            stat = myConnection.createStatement();
            String insertStatement = "Delete from image where iURL= \"" + oldUrl + "\"";
            return stat.executeUpdate(insertStatement);
        } catch (Exception e) {
            System.out.println("error");
        }
        return 0;
    }

    public int updateImage(String oldurl, String url) {
        try {
            stat = myConnection.createStatement();
            String insertStatement = "Update image set iURL = \"" + url + "\" where iURL =\"" + oldurl + "\"";
            return stat.executeUpdate(insertStatement);
        } catch (Exception e) {
            System.out.println("error");
        }
        return 0;
    }

}
