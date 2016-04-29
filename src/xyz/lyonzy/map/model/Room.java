package xyz.lyonzy.map.model;

/**
 * Created by Brendan Lyons on 27/04/16.
 */
public class Room {
    String name;
    private int roomId;
    private int buildingId;

    public Room(int roomId, int buildingId, String roomName) {
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.name = roomName;
    }

    public Room() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String roomName) {
        this.name = roomName;
    }
}
