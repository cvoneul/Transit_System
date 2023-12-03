public class TripOffering {
    int tripNumber, busID;
    String date, scheduledStartTime, getScheduledArrivalTime, driverName;


    public TripOffering(int tripNumber, int busID, String date, String scheduledStartTime, String getScheduledArrivalTime, String driverName) {
        this.tripNumber = tripNumber;
        this.busID = busID;
        this.date = date;
        this.scheduledStartTime = scheduledStartTime;
        this.getScheduledArrivalTime = getScheduledArrivalTime;
        this.driverName = driverName;
    }

    public int getTripNumber() {
        return tripNumber;
    }
    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }
    public int getBusID() {
        return busID;
    }
    public void setBusID(int busID) {
        this.busID = busID;
    }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getScheduledStartTime() { return scheduledStartTime; }
    public void setScheduledStartTime(String scheduledStartTime) { this.scheduledStartTime = scheduledStartTime; }
    public String getGetScheduledArrivalTime() { return getScheduledArrivalTime; }
    public void setGetScheduledArrivalTime(String getScheduledArrivalTime) { this.getScheduledArrivalTime = getScheduledArrivalTime; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
}
