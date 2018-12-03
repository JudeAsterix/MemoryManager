import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
/**
 *
 * @author Jandre
 */
public class MemoryManagerMenu extends JMenuBar{
    JMenu helpMenu = new JMenu("Help");
    JMenu exitMenu = new JMenu("Exit");
    public MemoryManagerMenu()
    {
        JMenuItem helpAbout = new JMenuItem("About");
        helpAbout.getAccessibleContext().setAccessibleDescription("Learn about how to use and assumptions.");
        helpAbout.addActionListener(new AboutListener());
        JMenuItem helpCredits = new JMenuItem("Credits");
        helpCredits.getAccessibleContext().setAccessibleDescription("Learn more about who made this application");
        helpCredits.addActionListener(new CreditsListener());
        helpMenu.add(helpAbout);
        helpMenu.add(helpCredits);
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitListener());
        exitMenu.add(exitItem);
        
        add(helpMenu);
        add(exitMenu);
    }
    
    public class AboutListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae) 
        {
            try {
                MemoryManagerAbout aboutFrame = new MemoryManagerAbout();
            } catch (IOException ex) {
                Logger.getLogger(MemoryManagerMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public class CreditsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae) 
        {
            MemoryManagerCredits creditsFrame = new MemoryManagerCredits();
        }
    }
    
    public class ExitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Integer i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Close Application?", JOptionPane.YES_NO_OPTION);
            if(i == 0)
            {
                System.exit(0);
            }
        }
    }
}
