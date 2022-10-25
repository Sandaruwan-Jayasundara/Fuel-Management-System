package Models;

/**
 * Fuel Station time details
 * Store fuel station start and close time
 */
public class Time {
    private String Arrival;
    private String departure;
    private String StationNumber;
    private String StationName;
    private String StationLocation;

    public Time(String Arrival, String departure, String StationNumber, String StationName, String StationLocation) {
        this.Arrival = Arrival;
        this.departure = departure;
        this.StationNumber = StationNumber;
        this.StationName = StationName;
        this.StationLocation = StationLocation;
    }
    public String getArrival() {
        return Arrival;
    }
    public void setArrival(String arrival) {
        Arrival = arrival;
    }
    public String getDeparture() {
        return departure;
    }
    public void setDeparture(String departure) {
        this.departure = departure;
    }
    public String getStationNumber() {
        return StationNumber;
    }
    public void setStationNumber(String stationNumber) {
        StationNumber = stationNumber;
    }
    public String getStationName() {return StationName;}
    public void setStationName(String stationName) {StationName = stationName;}
    public String getStationLocation() {return StationLocation;}
    public void setStationLocation(String stationLocation) {StationLocation = stationLocation;}
}
