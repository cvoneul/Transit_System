public class TripStopInfo {

    String drivingTime;
    int TripNumber, StopNumber, SequenceNumber;


    public TripStopInfo(int tripNumber, int stopNumber, int sequenceNumber, String drivingTime) {
        this.drivingTime = drivingTime;
        TripNumber = tripNumber;
        StopNumber = stopNumber;
        SequenceNumber = sequenceNumber;
    }



    public String getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(String drivingTime) {
        this.drivingTime = drivingTime;
    }

    public int getTripNumber() {
        return TripNumber;
    }

    public void setTripNumber(int tripNumber) {
        TripNumber = tripNumber;
    }

    public int getStopNumber() {
        return StopNumber;
    }

    public void setStopNumber(int stopNumber) {
        StopNumber = stopNumber;
    }

    public int getSequenceNumber() {
        return SequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        SequenceNumber = sequenceNumber;
    }
}
