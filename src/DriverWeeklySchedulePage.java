import javax.swing.*;
import java.sql.SQLException;

public class DriverWeeklySchedulePage {
    JFrame frame;

    public DriverWeeklySchedulePage(String driver, String date) throws SQLException {
        frame = new JFrame("Weekly Schedule of " + driver);
        frame.setSize(800,500);
        frame.setResizable(false);

        DatabaseController db = new DatabaseController();
        TripOffering[] tripOfs = db.getDriverWeeklySchedule(driver, date);

        String[] columns = { "Trip Number", "Date", "Scheduled Start Time", "Scheduled Arrival Time" };

        String[][] data = new String[tripOfs.length][columns.length];//colums length = 4

        //convert to strings
        for(int i = 0; i < tripOfs.length; i++) {
            int tripNumb = tripOfs[i].getTripNumber();
            String ddate = tripOfs[i].getDate();
            String sst = tripOfs[i].getScheduledStartTime();
            String sat = tripOfs[i].getGetScheduledArrivalTime();

            data[i][0] = String.valueOf(tripNumb);
            data[i][1] = ddate;
            data[i][2] = sst;
            data[i][3] = sat;
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
