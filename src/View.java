import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class View extends JFrame {
    private GridLayout grid = new GridLayout(0, 3);
    JButton backButton, playButton, skipButton;
    
    public View() {
        super("music player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(getContentPane());
        pack();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Adds the panel along with its buttons to the pane.
     */
    private void addComponentsToPane(final Container pane) {
        final JPanel panel = new JPanel();
        panel.setLayout(grid);        
        panel.setPreferredSize(new Dimension(300, 100));

        initializeButtons();
        
        panel.add(backButton);
        panel.add(playButton);
        panel.add(skipButton);
        
        pane.add(panel);
    }
    
    private void initializeButtons() {      
        backButton = new JButton();
        addIcon(backButton, "icons/prev.png");
        backButton.getPreferredSize();
        
        playButton = new JButton();
        addIcon(playButton, "icons/play.png"); 
        playButton.getPreferredSize();
        
        skipButton = new JButton();
        addIcon(skipButton, "icons/next.png");
        skipButton.getPreferredSize();
    }
    
    private void addIcon(JButton button, String iconPath) {
        try {
            Image icon = ImageIO.read(getClass().getResource(iconPath));
            button.setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println(iconPath + " not found.");
        }
    }
}