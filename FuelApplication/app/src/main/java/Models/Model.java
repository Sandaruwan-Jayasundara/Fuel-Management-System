package Models;

/**
 * Fuel model
 * store all fuel information
 */
public class Model {
    String liters;
    String arrival;
    String departure;
    String queue;
    String StationNumber;
    String StationName;
    String  StationLocation;

    public Model(String liters, String arrival, String departure, String queue, String StationNumber, String StationName, String StationLocation) {
        this.liters = liters;
        this.arrival = arrival;
        this.departure = departure;
        this.queue = queue;
        this.StationNumber = StationNumber;
        this.StationName = StationName;
        this.StationLocation = StationLocation;
    }
    public String getLiters() {
        return liters;
    }
    public void setLiters(String liters) {
        this.liters = liters;
    }
    public String getArrival() {
        return arrival;
    }
    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
    public String getDeparture() {
        return departure;
    }
    public void setDeparture(String departure) {
        this.departure = departure;
    }
    public String getQueue() {
        return queue;
    }
    public void setQueue(String queue) {
        this.queue = queue;
    }
    public String getStationNumber() {
        return StationNumber;
    }
    public void setStationNumber(String stationNumber) {
        StationNumber = stationNumber;
    }
    public String getStationName() {
        return StationName;
    }
    public void setStationName(String stationName) {
        StationName = stationName;
    }
    public String getStationLocation() {
        return StationLocation;
    }
    public void setStationLocation(String stationLocation) {
        StationLocation = stationLocation;
    }
}
