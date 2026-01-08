import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class FileLoginPage extends JFrame implements ActionListener,KeyListener {
    JTextField userField;
    JPasswordField passField;
    JButton loginButton, clearButton, registerButton;
    File userFile = new File("users.txt"); 
 	    FileLoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField();

        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");
        registerButton = new JButton("Register");

        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        registerButton.addActionListener(this);
	passField.addKeyListener(this);

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(loginButton); add(clearButton);
        add(registerButton);

        setSize(400, 200);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                  loginUser();          
	  }
        }
    public void keyTyped(KeyEvent e) {
        
    }
    public void keyReleased(KeyEvent e) {
         
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            loginUser();
        } else if (ae.getSource() == clearButton) {
            userField.setText("");	
            passField.setText("");
        } else if (ae.getSource() == registerButton) {
            registerUser();
        }
    }

        void loginUser() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + username);
		new budgetmanager(username);
		dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     void registerUser() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter fw = new FileWriter(userFile, true)) {
            fw.write(username + "," + password + "\n");
            JOptionPane.showMessageDialog(this, "User registered successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] arr) {
        new FileLoginPage();
    }
}
