import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
/*
 * @author Jandre
 */
public class MemoryManagerFrame extends JFrame
{
    //Java Components
    ButtonPanel panel1 = new ButtonPanel();
    JScrollPane scrollPane;
    MemoryManagerDiagram panel2 = new MemoryManagerDiagram();
    FitPanel panel3 = new FitPanel();
    JTable segmentTable;
    ButtonGroup methods = new ButtonGroup();
    
    //Table Data
    String[] segmentTableNames  = {"Segment Base", "Segment Length", "Burst Time", "Segment #"};
    Object[][] segmentTableData = {{0, 0, 50, 100}, {1, 150, 75, 25}, {2, 175, 25, 200}};
    
    public MemoryManagerFrame()
    {
        this.setTitle("Memory Manager");
        this.setSize(800, 800);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel1.setBounds(25, 20, 740, 40);
        this.add(panel1);
        
        panel2.setBounds(25, 100, 300, 600);
        this.add(panel2);
        
        panel3.setBounds(100, 60, 740, 40);
        this.add(panel3);
        
        segmentTable = new JTable(segmentTableData, segmentTableNames);
        scrollPane = new JScrollPane(segmentTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(350, 100, 415, 600);
        this.add(scrollPane);
        
        this.setVisible(true);
    }
    
    public void updateTable()
    {
        Object[][] data;
        String[] names;
        data = segmentTableData;
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
    }
    
    public class ButtonPanel extends JPanel
    {
        JButton[] buttons;
        public ButtonPanel()
        {
            this.setLayout(new GridLayout(1, 5, 25, 0));
            
            buttons = new JButton[5];
            for(int i = 0; i < buttons.length; i++)
            {
                buttons[i] = new JButton();
                buttons[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
                buttons[i].setMargin(new Insets(0,0,0,0));
            }
            
            buttons[0].setText("Add New Segment");
            buttons[1].setText("<html><center>Add Random<br>Segment</html>");
            buttons[2].setText("Let Quantum Pass");
            buttons[3].setText("Compact");
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
        JRadioButton[] buttons;
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
            buttons[0].setEnabled(true);
            buttons[1].setText("Best Fit");
            buttons[2].setText("Worst Fit");
            
            for(int i = 0; i < buttons.length; i++)
            {
                add(buttons[i]);
            }
        }
    }
    
    public class ResetAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            segmentTableData = new Object[0][3];
            panel2.updateDrawingData(segmentTableData);
            updateTable();
        }
    }
}
