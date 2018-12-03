
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Jandre
 */
public class MemoryManagerCredits extends JFrame{
    private JFrame frame = this;
    public MemoryManagerCredits()
    {
        setTitle("Credits");
        setSize(300, 270);
        setEnabled(true);
        setResizable(false);
        setLayout(null);
        
        JLabel label1 = new JLabel("<html><center>A project created by<br> "
                + "Jude Andre and Joshua Papello<br>"
                + "Made for St. Joseph's College's<br>"
                + "COM-310 Course <br>"
                + "Ran by Sister Jane Fritz<br>"
                + "Created in 2018</center></html>", SwingConstants.CENTER);
        label1.setBounds(0, 0, 300, 180);
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        add(label1);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 160, 100, 50);
        exitButton.addActionListener(new ExitListener());
        add(exitButton);
        
        
        Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(center.width/2 - this.getWidth()/2, center.height/2 - this.getHeight()/2);
        setVisible(true);
    }
    
    public class ExitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
           frame.dispose();
        }
        
    }
}
