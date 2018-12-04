import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
/*
| @author Jandre
|`This class stores the JFrame of the application. The application includes:
|   -3 Panels (Button Panel, Radio Button Panel, Diagram Panel)
|   -2 Tables and Scroll Panes (Segment Table, Queue Table)
|   -2 Labels for each table
*/
public class MemoryManagerFrame extends JFrame
{
    //Memory Manager
    MemoryManager memoryManager = new MemoryManager();
    
    //Java Components
    ButtonPanel panel1 = new ButtonPanel();                         //The component that holds the buttons
    JScrollPane segmentScrollPane, queueScrollPane;                 //These scroll panes hold the segment and queue tables
    MemoryManagerDiagram panel2 = new MemoryManagerDiagram();       //The canvas component that holds the diagram
    FitPanel panel3 = new FitPanel();                               //The component that holds the radio buttons
    JTable segmentTable, queueTable;                                //The segment table stores data of the processes in memory; the queue table stores data in the waiting queue.
    JLabel segmentTableLabel, queueTableLabel;                      //The labels indicate to the user which tables are which
    MemoryManagerMenu menu = new MemoryManagerMenu();               //The menu is used for miscellaneous things
    
    //Segment Table Data
    String[] segmentTableNames  = {"Segment #", "Segment Base", "Segment Length"};    //These are the names of the segment table
    Object[][] segmentTableData = {};          //This is the segment table's data
    
    //Queue Table Data
    String[] queueTableNames  = {"Segment #", "Segment Length"};      //These are the names of the queue table
    Object[][] queueTableData = {};                                                                 //This is the queue table's data
    int numberOfSegments = 3;
    
    public MemoryManagerFrame()                                                                     //Initializes the application
    {
        this.setTitle("Memory Manager");                                                            //The Title of the app is "Memory Manager"
        this.setSize(800, 775);                                                                     //The dimension of the app is 800 x 750
        this.setResizable(false);                                                                   //The app is not resizable
        this.setLayout(null);                                                                       //This allows any components to be placed anywhere with no restrictions
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                        //This allows the program to stop when the app is closed.
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
        Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(center.width/2 - this.getWidth()/2, center.height/2 - this.getHeight()/2);
        
        this.setJMenuBar(menu);
        
        /*
        |   This section creates the panels. The first chunk creates the button panel, the second creates the diagram and the third creates the radio button panel.
        |   The first line determines where the scroll pane will be placed.
        |   The second line adds the scroll pane to the application.
        */
        
        panel1.setBounds(25, 20, 740, 40);
        this.add(panel1);
        
        panel2.setBounds(25, 115, 300, 600);
        this.add(panel2);
        
        panel3.setBounds(100, 60, 740, 40);
        this.add(panel3);
        
        /*
        |   The next section creates the tables. The first chunk creates the segment table and the second creates the queue table.
        |   The first line creates the table based on the data and the table column names, respectively
        |   The second line create the scroll pane that holds the table, with parameters that never allows the JScrollPane to create a scrollbar.
        |   The third line determines where the scroll pane will be placed.
        |   The fourth line adds the scroll pane to the application.
        */
        
        segmentTable = new JTable(segmentTableData, segmentTableNames);
        segmentScrollPane = new JScrollPane(segmentTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        segmentScrollPane.setBounds(350, 120, 415, 280);
        this.add(segmentScrollPane);
        this.segmentTableLabel = new JLabel("Segment Table");
        this.segmentTableLabel.setBounds(500, 100, 415, 15);
        this.add(segmentTableLabel);
        
        queueTable = new JTable(queueTableData, queueTableNames);
        queueScrollPane = new JScrollPane(queueTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        queueScrollPane.setBounds(350, 425, 415, 280);
        this.add(queueScrollPane);
        this.queueTableLabel = new JLabel("Queue Table");
        this.queueTableLabel.setBounds(510, 405, 415, 15);
        this.add(queueTableLabel);
        
        updateTable(1);
        updateTable(2);
        this.setVisible(true);
    }
    
    /*
    |   The updateTable method is called whenever the data of either table is updated.
    */
    
    public void updateTable(int num) // If num = 1, edit the segment table; if num = 2, edit the queue table.
    {
        Object[][] data;
        String[] names;
        
        if(num == 1)
        {
            data = memoryManager.outputTableData(1);
            names = segmentTableNames;
            
            segmentTable.setModel(new AbstractTableModel() {
                public int getRowCount() {
                    return data.length;
                }

                public int getColumnCount() {
                    return names.length;
                }

                public String getColumnName(int column)
                {
                    return names[column];
                }

                public Object getValueAt(int i, int i1) {
                    return data[i][i1];
                }
            });
            
            ((AbstractTableModel)(segmentTable.getModel())).fireTableDataChanged();
            panel2.updateDrawingData(memoryManager.outputTableData(1));
        }
        else
        {
            data = memoryManager.outputTableData(2);
            names = queueTableNames;
            
           queueTable.setModel(new AbstractTableModel() {
                public int getRowCount() {
                    return data.length;
                }

                public int getColumnCount() {
                    return names.length;
                }

                public String getColumnName(int column)
                {
                    return names[column];
                }

                public Object getValueAt(int i, int i1) {
                    return data[i][i1];
                }
            });
            
            ((AbstractTableModel)(queueTable.getModel())).fireTableDataChanged();
        }
    }
    
    public class ButtonPanel extends JPanel
    {
        JButton[] buttons;
        public ButtonPanel()
        {
            this.setLayout(new GridLayout(1, 6, 25, 0));
            
            buttons = new JButton[5];
            for(int i = 0; i < buttons.length; i++)
            {
                buttons[i] = new JButton();
                buttons[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
                buttons[i].setMargin(new Insets(0,0,0,0));
            }
            
            buttons[0].setText("Add New Segment");
            buttons[0].addActionListener(new AddSegmentAction());
            buttons[1].setText("<html><center>Add Random<br>Segment</html>");
            buttons[1].addActionListener(new AddSegmentAction());
            buttons[2].setText("Compact");
            buttons[2].addActionListener(new ChangeSegmentsAction());
            buttons[3].setText("Remove Segment");
            buttons[3].addActionListener(new RemoveSegmentAction());
            buttons[4].setText("Reset");
            buttons[4].addActionListener(new ResetAction());
            
            for(int i = 0; i < buttons.length; i++)
            {
                add(buttons[i]);
            }
        }
    }
    
    public class FitPanel extends JPanel
    {
        public ButtonGroup methods = new ButtonGroup();
        public JRadioButton[] buttons;
        public FitPanel()
        {
            this.setLayout(new GridLayout(1, 3, 25, 0));
            buttons = new JRadioButton[3];
            
            for(int i = 0; i < buttons.length; i++)
            {
                buttons[i] = new JRadioButton();
                buttons[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
            }
            
            buttons[0].setText("First Fit");
            buttons[1].setText("Best Fit");
            buttons[2].setText("Worst Fit");
            
            for(int i = 0; i < buttons.length; i++)
            {
                methods.add(buttons[i]);
                add(buttons[i]);
            }
            
            buttons[0].setSelected(true);
        }
    }
    
    public class ResetAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            Integer i = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset? \nYou won't be able to recover any processes.", "Reset?", JOptionPane.YES_NO_OPTION);
            if(i == 0)
            {
                memoryManager.reset();
                updateTable(1);
                updateTable(2);
            }
        }
    }
    
    public class AddSegmentAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == panel1.getComponent(0))
            {
                Integer i;
                String s = JOptionPane.showInputDialog("Please give the length of the process.");
                if(s != null)
                {
                    try
                    {
                        i  = Integer.parseInt(s);
                        if(i > 500 || i < 10)
                        {
                            JOptionPane.showMessageDialog(null, "Error: Innappropriate number was input. Number must be an integer between 10-500.");
                        }
                        else
                        {
                            Process p = new Process(memoryManager.getNumberOfProcessesInMemory(), i);
                            if(panel3.buttons[0].isSelected())
                            {
                                if(memoryManager.firstFit(p))
                                {
                                    updateTable(1);
                                }
                                else
                                {
                                    updateTable(2);
                                }
                            }
                            else if(panel3.buttons[1].isSelected())
                            {
                                if(memoryManager.bestFit(p))
                                {
                                    updateTable(1);
                                }
                                else
                                {
                                    updateTable(2);
                                }
                            }
                            else if(panel3.buttons[2].isSelected())
                            {
                                if(memoryManager.worstFit(p))
                                {
                                    updateTable(1);
                                }
                                else
                                {
                                    updateTable(2);
                                }
                            }
                        }
                    }
                    catch(NumberFormatException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error: Whatever was input was not a number.");
                    }
                }
            }
            else if(e.getSource() == panel1.getComponent(1))
            {
                Process p = new Process();
                Random rand = new Random();
                p.setSize(rand.nextInt(240) + 10);
                p.setID(memoryManager.getNumberOfProcessesInMemory());
                JOptionPane.showMessageDialog(null, "A process with length " + p.getSize() + " has been added.");
                if(panel3.buttons[0].isSelected())
                {
                    if(memoryManager.firstFit(p))
                    {
                        updateTable(1);
                    }
                    else
                    {
                        updateTable(2);
                    }
                }
                else if(panel3.buttons[1].isSelected())
                {
                    if(memoryManager.bestFit(p))
                    {
                        updateTable(1);
                    }
                    else
                    {
                        updateTable(2);
                    }
                }
                else if(panel3.buttons[2].isSelected())
                {
                    if(memoryManager.worstFit(p))
                    {
                        updateTable(1);
                    }
                    else
                    {
                        updateTable(2);
                    }
                }
            }
        }
    }
    
    public class ChangeSegmentsAction implements ActionListener
    {
        
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == panel1.getComponent(2)) //Compact
            {
                memoryManager.compact();
                updateTable(1);
            }
        }
    }
    
    public class RemoveSegmentAction implements ActionListener
    {
        public void actionPerformed(ActionEvent ae) {
            Object[] list = new Object[memoryManager.getNumberOfProcessesInMemory()];
            
            for(int i = 0; i < list.length; i++)
            {
                list[i] = i;
            }
            
            Integer num = (Integer)(JOptionPane.showInputDialog(null, "Please specify which process to delete.", "Remove Process", JOptionPane.INFORMATION_MESSAGE, null, list, 1));
            
            if(num != null)
            {
                memoryManager.removeProcess(num);
                updateTable(1);
                updateTable(2);
            }
        }
    }
}