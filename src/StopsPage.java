import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.SQLException;

public class StopsPage {
    JFrame frame;

    public StopsPage(String tripID) throws SQLException {
        frame = new JFrame("List of Stops ");
        frame.setSize(800,500);
        frame.setResizable(false);

        DatabaseController db = new DatabaseController();
        TripStopInfo[] tsi = db.getAllTripStops(tripID);

        String[] columns = { "Trip Number", "Stop Number", "Sequence Number", "Driving Time"};

        String[][] data = new String[tsi.length][columns.length];//colums length = 4

        //convert to strings
        for(int i = 0; i < tsi.length; i++) {
            int tripNumb = tsi[i].getTripNumber();
            int stopN = tsi[i].getStopNumber();
            int seqN = tsi[i].getSequenceNumber();
            String drivingT = tsi[i].getDrivingTime();

            data[i][0] = String.valueOf(tripNumb);
            data[i][1] = String.valueOf(stopN);
            data[i][2] = String.valueOf(seqN);
            data[i][3] = drivingT;
        }


        JTable table = new JTable(data, columns);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50,50,700,350);


        frame.add(sp);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
