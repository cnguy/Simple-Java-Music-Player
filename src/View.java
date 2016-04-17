import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class View extends JFrame {
    GridLayout grid = new GridLayout(0, 3);
    JButton backButton, playButton, skipButton;
    
    public View() {
        super("music player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(getContentPane());
        pack();
        setVisible(true);
    }

    /**
     * Adds the panel along with its buttons to the pane.
     */
    public void addComponentsToPane(final Container pane) {
        final JPanel panel = new JPanel();
        panel.setLayout(grid);        
        panel.setPreferredSize(new Dimension(300, 100));

        backButton = new JButton("B");
        backButton.getPreferredSize();
        playButton = new JButton("P");
        playButton.getPreferredSize();
        skipButton = new JButton("S");
        skipButton.getPreferredSize();
        
        panel.add(backButton);
        panel.add(playButton);
        panel.add(skipButton);
        
        pane.add(panel);
    }
}