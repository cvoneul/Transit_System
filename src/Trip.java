public class Trip {

    private int tripID;
    private String StartLoc, destination;

    public Trip(int tripID, String StartLoc, String destination) {
        this.tripID = tripID;
        this.StartLoc = StartLoc;
        this.destination = destination;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getStartLoc() {
        return StartLoc;
    }

    public void setStartLoc(String startLoc) {
        StartLoc = startLoc;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
