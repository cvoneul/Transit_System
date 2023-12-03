import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BusPage {
    JFrame frame;

    public BusPage() throws SQLException {
        frame = new JFrame("Bus Options");
        frame.setSize(350,350);
        frame.setResizable(false);


        JButton addButton = new JButton("Add Bus");
        addButton.setBounds(40,100, 120, 100);
        addButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(addButton);


        JButton DeleteButton = new JButton("Delete Bus");
        DeleteButton.setBounds(180,100, 120, 100);
        DeleteButton.setMargin(new Insets(5, 5, 5, 5));
        frame.getContentPane().add(DeleteButton);


        DatabaseController db = new DatabaseController();


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String model = JOptionPane.showInputDialog("Enter the model of the bus");
                String year = JOptionPane.showInputDialog("Enter the year of the bus");

                try {
                    String busId = db.addBus(model,year);
                    JOptionPane.showMessageDialog(null, "Successfully added bus " + busId);
                    frame.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busId = JOptionPane.showInputDialog("Enter the bus ID");

                try {
                    db.DeleteBus(busId);
                    JOptionPane.showMessageDialog(null, "Successfully deleted bus " + busId);
                    frame.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
