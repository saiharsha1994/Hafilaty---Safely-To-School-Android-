package com.example.valuetechsa.admin_school_app;

/**
 * Created by ValueTechSA on 09-01-2017.
 */
public class SearchResultsBus {
    private String busId;
    private String routeName;
    private String chassisNumber;
    private String PlateNumber;
    private String fahas;
    private int positionsdrop;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public String getFahas() {
        return fahas;
    }

    public void setFahas(String fahas) {
        this.fahas = fahas;
    }

    public int getPositionsdrop() {
        return positionsdrop;
    }

    public void setPositionsdrop(int positionsdrop) {
        this.positionsdrop = positionsdrop;
    }
}
