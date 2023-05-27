package src;
import javax.swing.*;

import src.Exceptions.AddressOutOfBounds;
import src.Exceptions.IncorrectRegisterValue;
import src.Exceptions.NonExistingRegister;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    private JTextField fileNameField;
    private JTextArea outputArea;

    public GUI() {
        // Set up the window
        setTitle("My Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Set up the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Add the file name field
        JPanel fileNamePanel = new JPanel();
        fileNamePanel.setLayout(new BorderLayout());
        JLabel fileNameLabel = new JLabel("File Name:");
        fileNamePanel.add(fileNameLabel, BorderLayout.WEST);
        fileNameField = new JTextField();
        fileNamePanel.add(fileNameField, BorderLayout.CENTER);
        contentPane.add(fileNamePanel, BorderLayout.NORTH);

        // Add the run button
        JPanel buttonPanel = new JPanel();
        JButton runButton = new JButton("Run");
        runButton.addActionListener(this);
        buttonPanel.add(runButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Add the output area
        outputArea = new JTextArea();
        contentPane.add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
// Get the file name from the text field
        String fileName = fileNameField.getText();

        // Call the executeProgram method with the file name
        String output ="";
        try {
            output = executeProgram(fileName);
        } catch (NumberFormatException | IOException | NonExistingRegister | AddressOutOfBounds
                | IncorrectRegisterValue e1) {
            // TODO Auto-generated catch block
           System.out.println("Fe error fe action performed");
        }

        // Display the output in the output area
        outputArea.setText(output);
    }

    public String executeProgram(String fileName) throws NumberFormatException, FileNotFoundException, IOException, NonExistingRegister, AddressOutOfBounds, IncorrectRegisterValue {
        // Your existing code to execute the program with the given file name
        // ...
       CPU c = new CPU();
       c.executeProgram(fileName);

        // Return the output as a string
        return CPU.s;
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}

