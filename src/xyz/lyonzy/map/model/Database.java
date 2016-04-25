package xyz.lyonzy.map.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by brend on 24/04/2016.
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
        String selectQuery = "Select * from buildings Where buldingId =" + buildingId;
        ResultSet resultSet = stat.executeQuery(selectQuery);
        resultSet.next();
        return new Building(resultSet.getInt("buildingId"), resultSet.getString("Name"), resultSet.getString("Info"),
                resultSet.getString("OpeningHours"), resultSet.getString("ImageURL"));

    }
}
