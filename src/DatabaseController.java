import java.sql.*;

public class DatabaseController {
    private Connection connection;

    public DatabaseController() throws SQLException {
        String host = "127.0.0.1";
        int port = 3306;
        String database = "lab4";
        String username = "root";
        String password = "root";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        this.connection = DriverManager.getConnection(url, username, password);
    }



    //if driver doesnt exist then insert it to driver table
    public void insertDriver(String driver, String phoneN) throws SQLException {
        String sql1 = "select * from Driver where DriverName = ?;";
        PreparedStatement s = connection.prepareStatement(sql1);
        s.setString(1, driver);
        ResultSet rs = s.executeQuery();
        if(!rs.next()) {
            String sql = "insert into Driver values(?,?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, driver);
            st.setString(2, phoneN);
            st.executeUpdate();
        }
    }


    public void addActualTripStopInfo(String tripN, String date, String sst,
                                      String stopN, String sat, String actualst,
                                      String acutalat, String NumbIN, String NumbOUT) throws SQLException {
        String sql = "insert into ActualTripStopInfo values (?,?,?,?,?,?,?,?,?);";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1,tripN);
        st.setString(2,date);
        st.setString(3,sst);
        st.setString(4,stopN);
        st.setString(5,sat);
        st.setString(6,actualst);
        st.setString(7,acutalat);
        st.setString(8,NumbIN);
        st.setString(9,NumbOUT);
        st.executeUpdate();
    }

    public String addBus(String model, String year) throws SQLException {
        String busId = "";
        String sql = "insert into Bus (Model, Year) values(?,?);";
        PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, model);
        st.setInt(2,Integer.parseInt(year));
        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        if(rs.next()) {
            busId = Integer.toString(rs.getInt(1));
        }
        return busId;
    }

    public void addBus(String id, String model, String year) throws SQLException {
        String sql = "insert into Bus (BusId, Model, Year) values(?,?,?);";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1,Integer.parseInt(id));
        st.setString(2, model);
        st.setInt(3,Integer.parseInt(year));
        st.executeUpdate();
    }

    public boolean busExists(String busID) throws SQLException {
        String sql = "select * from Bus where BusId = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, busID);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return true;
        }else {
            return false;
        }
    }

    public void addTripOffering(String tripIDStr, String date, String start, String arrival, String driver, String busID) throws SQLException{
        String sql = "insert into TripOffering values(?, ?, ?, ?, ?, ?);";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, Integer.parseInt(tripIDStr));
        st.setString(2, date);
        st.setString(3, start);
        st.setString(4, arrival);
        st.setString(5, driver);
        st.setString(6, busID);
        st.executeUpdate();
    }

    public void deleteTripOffering(String tripIDStr, String date, String start) throws SQLException {
        String sql = "DELETE FROM TripOffering " +
                     "WHERE TripNumber=? AND date=? and ScheduledStartTime=?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(tripIDStr));
        statement.setString(2, date);
        statement.setString(3, start);
        statement.executeUpdate();
    }

    public Trip getTrip(int tripNumber) throws SQLException {
        Trip trip = null;
        String sql = "select StartLocationName, DestinationName " +
                     "from Trip " +
                     "where TripNumber = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, tripNumber);
        ResultSet rs = st.executeQuery();

        if(rs.next()) {
            String startLocation = rs.getString("StartLocationName");
            String destination = rs.getString("DestinationName");
            trip = new Trip(tripNumber, startLocation, destination);
        }

        return trip;
    }

    public TripCombo[] getAllTrips(String startLoc, String dest) throws SQLException {
        TripCombo[] trips = null;
        String sql0 = "select count(*) as NumberOfRows from ( " +
                "select T.TripNumber from Trip t " +
                "inner join TripOffering o on t.TripNumber = o.TripNumber " +
                "where t.StartLocationName = ? and t.DestinationName = ? " +
                ") A;" ;
        String sql = "select * " +
                "from Trip t " +
                "inner join TripOffering o on t.TripNumber = o.TripNumber " +
                "where t.StartLocationName = ? and t.DestinationName = ? ;";
        //to get the number of rows
        PreparedStatement st = connection.prepareStatement(sql0);
        st.setString(1, startLoc);
        st.setString(2, dest);
        ResultSet r = st.executeQuery();
        int rows = 0;
        if(r.next()) {
            rows = r.getInt("NumberOfRows");
        }
        trips = new TripCombo[rows];

        //to get the trips
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, startLoc);
        statement.setString(2, dest);
        ResultSet rs = statement.executeQuery();
        for (int i = 0; rs.next() && i < rows; i++) {
            int tripID = rs.getInt("TripNumber");
            String startLocation = rs.getString("StartLocationName");
            String destination = rs.getString("DestinationName");
            int busId = rs.getInt("BusId");
            String date = rs.getString("Date");
            String scStartTime = rs.getString("ScheduledStartTime");
            String scArrivalTime = rs.getString("ScheduledArrivalTime");
            String driver = rs.getString("DriverName");

            Trip tripTemp = new Trip(tripID,startLocation,destination);
            TripOffering tripOfferingTemp = new TripOffering(tripID, busId, date, scStartTime, scArrivalTime, driver);

            trips[i] = new TripCombo(tripTemp, tripOfferingTemp);
        }
        return trips;
    }

    public TripStopInfo[] getAllTripStops(String tripN) throws SQLException {
        TripStopInfo[] tsi = null;
        String sql0 = "select count(*) as NumOfRows " +
                "from TripStopInfo t " +
                "where t.TripNumber = ? ;";
        String sql = "select * " +
                "from TripStopInfo t " +
                "where t.TripNumber = ? ;";

        PreparedStatement s = connection.prepareStatement(sql0);
        s.setInt(1, Integer.parseInt(tripN));
        ResultSet r = s.executeQuery();
        int rows = 0;
        if(r.next()) {
            rows = r.getInt("NumOfRows");
        }
        tsi = new TripStopInfo[rows];


        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1,Integer.parseInt(tripN));
        ResultSet rs = st.executeQuery();
        for (int i = 0; rs.next() && i < rows; i++) {
            int tripID = rs.getInt("TripNumber");
            int stopN = rs.getInt("StopNumber");
            int seqN = rs.getInt("SequenceNumber");
            String drivingTime = rs.getString("DrivingTime");

            tsi[i] = new TripStopInfo( tripID, stopN, seqN, drivingTime );
        }
        return tsi;
    }

    public TripOffering[] getDriverWeeklySchedule(String driver, String date) throws SQLException {
        TripOffering[] tripOfs = null;
        String sql0 = "select count(*) as NumOfRows " +
                "from Driver d " +
                "inner join TripOffering t on t.DriverName = d.DriverName " +
                "where d.DriverName = ? " +
                "and t.Date between ? and DATE_ADD(?, interval 7 day);";
        String sql = "select * " +
                "from Driver d " +
                "inner join TripOffering t on t.DriverName = d.DriverName " +
                "where d.DriverName = ? " +
                "and t.Date between ? and DATE_ADD(?, interval 7 day);";


        PreparedStatement s = connection.prepareStatement(sql0);
        s.setString(1, driver);
        s.setString(2, date);
        s.setString(3, date);
        ResultSet r = s.executeQuery();
        int rows = 0;
        if(r.next()) {
            rows = r.getInt("NumOfRows");
        }
        tripOfs = new TripOffering[rows];


        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1,driver);
        st.setString(2, date);
        st.setString(3, date);
        ResultSet rs = st.executeQuery();
        for (int i = 0; rs.next() && i < rows; i++) {
            int tripN = rs.getInt("TripNumber");
            String ddate = rs.getString("Date");
            String startT = rs.getString("ScheduledStartTime");
            String arrivalT = rs.getString("ScheduledArrivalTime");

            tripOfs[i] = new TripOffering( tripN, 0, ddate, startT, arrivalT, driver);
        }


        return tripOfs;
    }









    public TripCombo[] getAllTrips() throws SQLException {
        TripCombo[] trips = null;
        String sql0 = "select count(*) as NumberOfRows from ( " +
                "select T.TripNumber from Trip t " +
                "inner join TripOffering o on t.TripNumber = o.TripNumber " +
                ") A;" ;
        String sql = "select * " +
                "from Trip t " +
                "inner join TripOffering o on t.TripNumber = o.TripNumber ;";
        //to get the number of rows
        PreparedStatement st = connection.prepareStatement(sql0);
        ResultSet r = st.executeQuery();
        int rows = 0;
        if(r.next()) {
            rows = r.getInt("NumberOfRows");
        }
        trips = new TripCombo[rows];

        //to get the trips
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        for (int i = 0; rs.next() && i < rows; i++) {
            int tripID = rs.getInt("TripNumber");
            String startLocation = rs.getString("StartLocationName");
            String destination = rs.getString("DestinationName");
            int busId = rs.getInt("BusId");
            String date = rs.getString("Date");
            String scStartTime = rs.getString("ScheduledStartTime");
            String scArrivalTime = rs.getString("ScheduledArrivalTime");
            String driver = rs.getString("DriverName");

            Trip tripTemp = new Trip(tripID,startLocation,destination);
            TripOffering tripOfferingTemp = new TripOffering(tripID, busId, date, scStartTime, scArrivalTime, driver);

            trips[i] = new TripCombo(tripTemp, tripOfferingTemp);
        }
        return trips;
    }





    public void updateDriver(String tripIDStr, String date, String start, String driver) throws SQLException {
        String sql = "update TripOffering " +
                "set DriverName = ? " +
                "where TripNumber = ? and date = ? and ScheduledStartTime = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, driver);
        st.setInt(2, Integer.parseInt(tripIDStr));
        st.setString(3,date);
        st.setString(4, start);
        st.executeUpdate();
    }

    public void updateBus(String tripIDStr, String date, String start, String busID)  throws SQLException {
        String sql = "update TripOffering " +
                "set BusId = ? " +
                "where TripNumber = ? and date = ? and ScheduledStartTime = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, Integer.parseInt(busID));
        st.setInt(2, Integer.parseInt(tripIDStr));
        st.setString(3,date);
        st.setString(4, start);
        st.executeUpdate();
    }

    public void DeleteBus(String busId) throws SQLException {
        String sql = "delete from Bus " +
                "where BusId = ?;";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, Integer.parseInt(busId));
        st.executeUpdate();
    }
}