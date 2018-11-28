
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;

/**
 *
 * @author Jandre
 */
public class MemoryManagerAbout extends JFrame{
    
    public MemoryManagerAbout()
    {
        setSize(500, 500);
        setEnabled(true);
        setResizable(false);
        setVisible(true);
        
        DefaultStyledDocument dsd = new DefaultStyledDocument();
        JTextPane textPane = new JTextPane(dsd);
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
    }
}
