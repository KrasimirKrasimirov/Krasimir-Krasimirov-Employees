package UI;

import Models.ComparisonResultInfo;
import TextParsing.EmployeeComparisonEngine;
import TextParsing.Interfaces.IComparisonEngine;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UI extends JFrame implements ActionListener {
    /**
     * Frame constants
     */
    private static final int FRAME_X = 250;
    private static final int FRAME_Y = 300;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 420;
    /**
     * UI elements text constants
     */
    private static final String WINDOW_TITLE = ("File Selection Window");
    private static final String SELECT_BUTTON_TEXT = ("Select file");
    private static final String JFILECHOOSER_FILTER_DESCRIPTION = ("Text File");
    /**
     * File extension types
     */
    private static final String TXT_EXTENSION = ("txt");
    private static final String CSV_EXTENSION = ("csv");

    final JFileChooser fileChooser = new JFileChooser();
    final FileNameExtensionFilter filter = new FileNameExtensionFilter(JFILECHOOSER_FILTER_DESCRIPTION, TXT_EXTENSION, CSV_EXTENSION);

    //region Methods
    public void initializeUI() {
        UI frame = new UI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(FRAME_X, FRAME_Y);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle(WINDOW_TITLE);
        frame.createGUI();
        frame.setVisible(true);
    }

    private void createGUI() {
        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        window.setBackground(Color.lightGray);

        JButton button = new JButton(SELECT_BUTTON_TEXT);
        button.addActionListener(this);
        window.add(button); // Adds Button to content pane of frame

        fileChooser.setFileFilter(filter);
    }

    public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooser.showOpenDialog(UI.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getName();
            if (filename.substring(filename.lastIndexOf("."), filename.length()).equals(".txt")) {
                File file = fileChooser.getSelectedFile();

                IComparisonEngine employeeComparisonEngine = new EmployeeComparisonEngine();

                ComparisonResultInfo comparisonResultInfo = employeeComparisonEngine.run(file.getAbsolutePath());

                DataGridTable dataGrid = new DataGridTable(comparisonResultInfo.empID1, comparisonResultInfo.empID2, comparisonResultInfo.projectID, comparisonResultInfo.daysWorked);
                dataGrid.fetchData();
            } else {
                System.out.println("Incorrect file format selected");
            }
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    //endregion
}