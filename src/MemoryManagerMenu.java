import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 *
 * @author Jandre
 */
public class MemoryManagerMenu extends JMenuBar{
    JMenu helpMenu = new JMenu("Help");
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
        add(helpMenu);
    }
    
    public class AboutListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae) 
        {
            MemoryManagerAbout aboutFrame = new MemoryManagerAbout();
        }
    }
    
    public class CreditsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae) 
        {
            MemoryManagerCredits creditsFrame = new MemoryManagerCredits();
        }
    }
}
