
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//@Author Jude Andre

public class MemoryManagerDiagram extends Canvas{
    private int[][] segmentData = {{0, 0, 50}, {1, 150, 25}, {2, 200, 200}}; //Stores the segment base, length, snd number, respectively.
    
    public MemoryManagerDiagram()
    {
        this.setSize(new Dimension(300, 600));
    }
    
    public void paint(Graphics g)
    {
        FontMetrics fm = g.getFontMetrics();
        for(int i = 0; i < segmentData.length; i++)
        {
            int lower = segmentData[i][1];
            int height = segmentData[i][2];
            int number = segmentData[i][0];
            g.setColor(Color.lightGray);
            g.fillRect(50, lower + 50, 200, height);
            g.setColor(Color.gray);
            g.drawRect(50, lower + 50, 200, height);
            g.setColor(Color.black);
            g.drawString(Integer.toString(lower), 45 - fm.stringWidth(Integer.toString(lower)), lower + 55);
            g.drawString(Integer.toString(lower + height), 45 - fm.stringWidth(Integer.toString(lower + height)), lower + height + 55);
            g.drawString("Segment " + number, 150 - (fm.stringWidth("Segment " + i) / 2), 50 + ((2 * lower + height) / 2) + (fm.getHeight() / 2));
        }
        
        //this.setBackground(Color.white);
        g.setColor(Color.BLACK);
        g.drawLine(50, 50, 250, 50);
        g.drawLine(250, 550, 250, 50);
        g.drawLine(250, 550, 50, 550);
        g.drawLine(50, 50, 50, 550);
        g.drawString(Integer.toString(0), 45 - fm.stringWidth(Integer.toString(0)), 55);
        g.drawString(Integer.toString(500), 45 - fm.stringWidth(Integer.toString(500)), 500 + 55);
    }
    
    public void updateDrawingData(Object[][] data)
    {
        segmentData = new int[data.length][3];
        
        for(int i = 0; i < data.length; i++)
        {
            segmentData[i][0] = (Integer)(data[i][0]);
            segmentData[i][1] = (Integer)(data[i][1]);
            segmentData[i][2] = (Integer)(data[i][2]);
        }
        repaint();
    }
}
