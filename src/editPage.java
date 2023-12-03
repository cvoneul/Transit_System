import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.ECField;
import java.sql.SQLException;

public class editPage implements ActionListener {
    JButton[] buttons = new JButton[4];
    JFrame frame;

    public editPage()  {
        frame = new JFrame("Edit Options");
        frame.setSize(625,350);
        frame.setResizable(false);


        buttons[0] = new JButton("<html> Delete a <br /> Trip Offering </html>");
        buttons[1] = new JButton("<html> Add Trip <br /> Offerings </html>");
        buttons[2] = new JButton("Change Driver");
        buttons[3] = new JButton("Change Bus");


        //set up the buttons using an array to save lines of code
        int x = 20;
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds(x, 100, 120, 100);
            buttons[i].setMargin(new Insets(5,5,5,5));
            frame.getContentPane().add(buttons[i]);

            buttons[i].addActionListener(this);
            x += 150;
        }



       /* //home button
        JButton homeButton = new JButton("Back to Main Page");
        homeButton.setBounds(220,225, 180, 50);
        homeButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(homeButton);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MainPage("","");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });*/






        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DatabaseController db = new DatabaseController();

            if (e.getSource().equals(buttons[0])) // delete a trip offering
            {
                String tripIDStr = JOptionPane.showInputDialog("Enter the Trip number ID");
                String date = JOptionPane.showInputDialog("Enter the Date (Format:  yyyy-mm-dd)");
                String start = JOptionPane.showInputDialog("Enter the Scheduled Start Time (Format:  24:60:60)");

                db.deleteTripOffering(tripIDStr,date,start);

                JOptionPane.showMessageDialog(null, "Successfully Deleted");
            }
            else if(e.getSource().equals(buttons[1])) //add 1 or more trip offerings
            {
                String tripIDStr = JOptionPane.showInputDialog("Enter the Trip number ID");
                String date = JOptionPane.showInputDialog("Enter the Date (Format:  yyyy-mm-dd)");
                String start = JOptionPane.showInputDialog("Enter the Scheduled Start Time (Format:  24:60:60)");
                String arrival = JOptionPane.showInputDialog("Enter the Scheduled Arrival Time (Format:  24:60:60)");
                String driver = JOptionPane.showInputDialog("Enter the driver name");
                String busID = JOptionPane.showInputDialog("Enter the bus ID");

                db.insertDriver(driver, null);
                if (!db.busExists(busID)) {
                    db.addBus(busID,"null", "-1");
                }
                db.addTripOffering(tripIDStr, date, start, arrival, driver, busID);

                String message = "Successfully added trip offering. Would you like to add another?";
                int reply = JOptionPane.showConfirmDialog(null, message, null, JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION) {
                    actionPerformed(e);
                }
            }
            else if(e.getSource().equals(buttons[2])) //change driver
            {
                String tripIDStr = JOptionPane.showInputDialog("Enter the Trip number ID");
                String date = JOptionPane.showInputDialog("Enter the Date (Format:  yyyy-mm-dd)");
                String start = JOptionPane.showInputDialog("Enter the Scheduled Start Time (Format:  24:60:60)");
                String driver = JOptionPane.showInputDialog("Enter the new drivers name");

                db.updateDriver(tripIDStr, date, start, driver);

                JOptionPane.showMessageDialog(null, "Successfully updated Driver for trip #" + tripIDStr);

            }
            else //change busID
            {
                String tripIDStr = JOptionPane.showInputDialog("Enter the Trip number ID");
                String date = JOptionPane.showInputDialog("Enter the Date (Format:  yyyy-mm-dd)");
                String start = JOptionPane.showInputDialog("Enter the Scheduled Start Time (Format:  24:60:60)");
                String busID = JOptionPane.showInputDialog("Enter the new bus ID");

                db.updateBus(tripIDStr, date, start, busID);

                JOptionPane.showMessageDialog(null, "Successfully updated Bus ID for trip #" + tripIDStr);
            }

            frame.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "There was an error with your request");
            throw new RuntimeException(ex);
        }


    }
}
