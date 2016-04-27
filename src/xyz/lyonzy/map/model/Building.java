package xyz.lyonzy.map.model;

/**
 * Created by brendan Lyons on 25/04/2016.
 * This class deals with buildings, primarily used for displaying them
 */
public class Building {
    private int buildingNo;
    private String buildingName;
    private String openingHours;
    private String buildingInfo;
    private String image;
    private int imageRef;

    public Building(int buildingNo, String buildingName, String openingHours, String buildingInfo, String image, int imageRef) {
        this.buildingNo = buildingNo;
        this.buildingName = buildingName;
        this.openingHours = openingHours;
        this.buildingInfo = buildingInfo;
        this.image = image;
        this.imageRef = imageRef;
    }

    public Building(int buildingNo) {
        this.buildingNo = buildingNo;
        this.buildingName = "Please Enter Name";
        this.openingHours = "Please Enter Hours";
        this.buildingInfo = "Please Enter Info";
        this.image = "Please Enter Image";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getBuildingInfo() {
        return buildingInfo;
    }

    public void setBuildingInfo(String buildingInfo) {
        this.buildingInfo = buildingInfo;
    }

    public int getImageRef() {
        return imageRef;
    }

    public void setImageRef(int imageRef) {
        this.imageRef = imageRef;
    }
}
