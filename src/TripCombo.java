public class TripCombo {
    Trip trip;
    TripOffering tripOffering;

    public TripCombo(Trip trip, TripOffering tripOffering) {
        this.trip = trip;
        this.tripOffering = tripOffering;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TripOffering getTripOffering() {
        return tripOffering;
    }

    public void setTripOffering(TripOffering tripOffering) {
        this.tripOffering = tripOffering;
    }
}
