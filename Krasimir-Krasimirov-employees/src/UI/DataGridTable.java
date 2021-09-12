package UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;

public class DataGridTable extends JPanel {
    /**
     * Frame constants
     */
    private static final int FRAME_X = 250;
    private static final int FRAME_Y = 300;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 70;

    private static final int RESULT_GRID_ROWS_COUNT = 1;
    private static final int RESULT_GRID_COLS_COUNT = 0;
    private static final String[] COLUMN_NAMES = {"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"};
    private static final String FRAME_TITLE = ("Data table");

    public int empID1;
    public int empID2;
    public int projectID;
    public int daysWorked;

    public DataGridTable(int empID1, int empID2, int projectID, int daysWorked) {
        super(new GridLayout(RESULT_GRID_ROWS_COUNT, RESULT_GRID_COLS_COUNT));

        this.empID1 = empID1;
        this.empID2 = empID2;
        this.projectID = projectID;
        this.daysWorked = daysWorked;

        // create the table
        Object[][] data = {{String.valueOf(empID1), String.valueOf(empID2), String.valueOf(projectID), String.valueOf(daysWorked),}};
        final JTable table = new JTable(data, COLUMN_NAMES);
        table.setPreferredScrollableViewportSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private static void createAndShowGUI(int empID1, int empID2, int projectID, int daysWorked) {
        //Create and set up the window.
        JFrame frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DataGridTable newContentPane = new DataGridTable(empID1, empID2, projectID, daysWorked);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setLocation(FRAME_X, FRAME_Y);
        //Display the window.
        frame.pack();

        frame.setVisible(true);
    }

    public void fetchData() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(empID1, empID2, projectID, daysWorked);
            }
        });
    }
}