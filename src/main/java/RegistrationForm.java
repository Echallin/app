import PreparedStatment.PreparedStatment;
import com.example.demo.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.DatabaseMetaData;

public class RegistrationForm extends JDialog {
    private JTextField registerTextField;
    private JTextField tfUser;
    private JTextField pfPassword;
    private JTextField pfConfimPassword;
    private JTextField tfHeight;
    private JTextField tfWeight;
    private JTextField tfAge;
    private JButton btnMale;
    private JButton btnFemale;
    private JButton btnConfirm;
    private JButton btnCancle;
    private JPanel registerPanel;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMaximumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = tfUser.getText();
        String height = tfHeight.getText();
        String weight = tfWeight.getText();
        String age = tfAge.getText();
        String password = String.valueOf(pfPassword.getText());
        String confirmPassword = String.valueOf(pfConfimPassword.getText());

        if (name.isEmpty() && height.isEmpty() && weight.isEmpty() && age.isEmpty() && confirmPassword.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;

    }

    if(!password.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(this,
                "Password does not match",
                "Try again",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    user= addUserToDatanase(name, password, height, weight, age);
    if (user != null) {
        dispose();
    }

    }

    public User user;
    private <DatabaseMetaData> User addUserToDatabase(String name, String password, String height, String weight, String age) {
        User User = null;
        final String DB_URL = "jdbc:mysql://localhost/phpmyadmin/index.php?route=/sql&db=products&table=register&pos=0";
        final String USERNAME = "root";
        final String PASSWODR = "";
        try{


            Connection conn = DriverManager.getConnection (DB_URL, USERNAME, PASSWODR);

            Statement stmt = conn.createStatment();
            String sql = "INSERT INTO register (name, password, height, weight, age)" +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatment preparedStatment = conn.prepareStatment(sql);
            preparedStatment.setString(1, name);
            preparedStatment.setString(2, password);
            preparedStatment.setString(3, height);
            preparedStatment.setString(4, weight);
            preparedStatment.setString(5, age);

            int addedRows = preparedStatment.executeUpdate();
            if (addedRows > 0) {
                user = new User ();
                user.name = name;
                user.password = password;
                user.height = height;
                user.weight = weight;
                user.age = age;
            }

            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args){
        RegistrationForm myForm = new RegistrationForm(null);
        User user = myForm.user;
        if (user != null){
            System.out.println("Succesfull registration" + user.name);
        }
        else {
            System.out.println("Registration fail");
        }
    }
}
