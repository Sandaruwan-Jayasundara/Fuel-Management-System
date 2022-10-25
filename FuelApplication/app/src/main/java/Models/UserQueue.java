package Models;

/**
 * User queue time details
 * Add and remove queue time details
 */
public class UserQueue {
    private String phone;
    private String arrival;
    private String departure;
    private String fuel;

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
    public String getFuel() {
        return fuel;
    }
    public void setFue(String fuel) {
        this.fuel = fuel;
    }
}
