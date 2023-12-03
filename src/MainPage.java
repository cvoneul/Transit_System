import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainPage {
    public MainPage(String startN, String destN) throws SQLException {
        JFrame frame = new JFrame("Cal Poly Pomona Transit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,600);
        frame.setResizable(false);

        JLabel subtitle = new JLabel("List of Trips Offerings");
        subtitle.setBounds(550, -25, 250, 100);
        frame.getContentPane().add(subtitle);


        DatabaseController db = new DatabaseController();

        TripCombo[] allTrips;
        if(startN.length() == 0) {
            allTrips = db.getAllTrips();
        }
        else {
            allTrips = db.getAllTrips(startN, destN);//get all trips for the given start and destination name
        }

        String[][] data = new String[allTrips.length][8];//make a 2d array to have everything in string format

        for(int i = 0; i < allTrips.length; i++) {//for all trips, get their data and put them in the 2d array of arrays of string that contain the 8 strings of a Trip
            int tripNumb = allTrips[i].trip.getTripID();
            String startLoc = allTrips[i].trip.getStartLoc();
            String dest = allTrips[i].trip.getDestination();
            int busId = allTrips[i].tripOffering.getBusID();
            String date = allTrips[i].tripOffering.getDate();
            String scStartTime = allTrips[i].tripOffering.getScheduledStartTime();
            String scArrivalTime = allTrips[i].tripOffering.getGetScheduledArrivalTime();
            String driver = allTrips[i].tripOffering.getDriverName();

            data[i][0] = String.valueOf(tripNumb);
            data[i][1] = startLoc;
            data[i][2] = dest;
            data[i][3] = String.valueOf(busId);
            data[i][4] = date;
            data[i][5] = scStartTime;
            data[i][6] = scArrivalTime;
            data[i][7] = driver;
        }

        String[] columns = { "Trip Number", "Starting Location", "Destination", "BusID",
                "Date", "Scheduled Start Time", "Scheduled Arrival Time", "Bus Driver Name"};
        JTable table = new JTable(data, columns);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50,50,1100,350);



        JButton searchButton = new JButton("Search Trips");
        searchButton.setBounds(60,425, 120, 100);
        searchButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = JOptionPane.showInputDialog("Enter the starting location");
                String dest = JOptionPane.showInputDialog(("Enter the destination"));
                try {
                    new MainPage(start, dest);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });

        JButton editButton = new JButton("<html>Edit Trip<br />  Schedule</html>");
        editButton.setBounds(220,425, 120, 100);
        editButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new editPage();
                //frame.dispose();
            }
        });



        JButton displayStopsButton = new JButton("Display Trip Stops");
        displayStopsButton.setBounds(380,425, 120, 100);
        displayStopsButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(displayStopsButton);

        displayStopsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tripID = JOptionPane.showInputDialog("Enter the Trip ID");
                //String stopN = JOptionPane.showInputDialog("Enter the stop number");
                try {
                    new StopsPage(tripID);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //frame.dispose();
            }
        });



        JButton displayDriverScheduleButton = new JButton("<html>Display Driver<br />Weekly Schedule</html>");//this html adds a new line
        displayDriverScheduleButton.setBounds(540,425, 120, 100);
        displayDriverScheduleButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(displayDriverScheduleButton);

        displayDriverScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String driver = JOptionPane.showInputDialog("Enter the full driver name");
                String date = JOptionPane.showInputDialog(("Enter the date"));
                try {
                    new DriverWeeklySchedulePage(driver, date);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //frame.dispose();
            }
        });





        JButton addDriveButton = new JButton("Add a driver");
        addDriveButton.setBounds(700,425, 120, 100);
        addDriveButton.setMargin(new Insets(10, 20, 10, 20));
        frame.getContentPane().add(addDriveButton);

        addDriveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String driver = JOptionPane.showInputDialog("Enter the name of the driver");
               String phoneN = JOptionPane.showInputDialog("Enter the driver's phone number");

                try {
                    db.insertDriver(driver, phoneN);
                    JOptionPane.showMessageDialog(null, "Successfully added Driver: " + driver);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Could not add Driver");
                    throw new RuntimeException(ex);
                }
            }
        });





        JButton busButton = new JButton("<html>Add/Delete<br /> Bus </html>");
        busButton.setBounds(860,425, 120, 100);
        busButton.setMargin(new Insets(10, 20, 10, 20));
        frame.getContentPane().add(busButton);

        busButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new BusPage();
                    //frame.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        JButton insertButton = new JButton("<html>Insert Actual<br />Trip Stop Info</html>");
        insertButton.setBounds(1015,425, 125, 100);
        insertButton.setMargin(new Insets(10, 20, 10, 20));
        frame.getContentPane().add(insertButton);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tripN = JOptionPane.showInputDialog("Enter the Trip Number ID");
                String date = JOptionPane.showInputDialog("Enter the date");
                String sst = JOptionPane.showInputDialog("Enter the Scheduled Start Time");
                String stopN = JOptionPane.showInputDialog("Enter the Stop Number");
                String sat = JOptionPane.showInputDialog("Enter the Scheduled Arrival Time");
                String actualst = JOptionPane.showInputDialog("Enter the Actual Start Time");
                String actualat = JOptionPane.showInputDialog("Enter the Actual Arrival Time");
                String NumbOfPassengersIN = JOptionPane.showInputDialog("Enter the number of passengers in");
                String NumbOfPassengersOUT = JOptionPane.showInputDialog("Enter the number of passengers out");

                try {
                    db.addActualTripStopInfo(tripN,date,sst,stopN,sat,actualst,actualat,NumbOfPassengersIN,NumbOfPassengersOUT);
                    JOptionPane.showMessageDialog(null, "Successful");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "There was an error with the request");
                    throw new RuntimeException(ex);
                }

                //frame.dispose();
            }
        });


        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(5,5, 100, 40);
        refreshButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MainPage(startN, destN);
                    frame.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        frame.add(sp);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
