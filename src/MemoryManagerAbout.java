
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * @author Jandre
 */
public class MemoryManagerAbout extends JFrame{
    
    public MemoryManagerAbout() throws IOException
    {
        setTitle("About Memory Manager");
        setSize(500, 500);
        setEnabled(true);
        setResizable(false);
        setLayout(null);
        JEditorPane editorPane = new JEditorPane();
        URL aboutURL = MemoryManagerAbout.class.getResource("MemoryManagerAboutHTML.html");
        editorPane.setPage(aboutURL);
        
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0,0,497,420);
        add(scrollPane);
        
        JButton exitButton = new JButton("Close");
        
        Dimension center = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(center.width/2 - this.getWidth()/2, center.height/2 - this.getHeight()/2);
        setVisible(true);
    }
}
