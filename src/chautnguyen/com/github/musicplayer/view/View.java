package chautnguyen.com.github.musicplayer.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class View extends JFrame {
    private GridLayout grid = new GridLayout(0, 6);
    private JButton prevPlaylistButton, backButton, playButton, skipButton, nextPlaylistButton;
    private JSlider volumeSlider;
    
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
        panel.setPreferredSize(new Dimension(500, 75));

        initializeComponents();
        
        panel.add(prevPlaylistButton);
        panel.add(backButton);
        panel.add(playButton);
        panel.add(skipButton);
        panel.add(nextPlaylistButton);
        panel.add(volumeSlider);
        
        pane.add(panel);
    }
        
    private void initializeComponents() {
        // any way to do this in a cleaner fashion? TODO
        
        backButton = new JButton();
        addIcon(backButton, "icons/prev.png");
        backButton.getPreferredSize();                               
        
        playButton = new JButton();
        addIcon(playButton, "icons/play.png");
        playButton.getPreferredSize();
        
        skipButton = new JButton();
        addIcon(skipButton, "icons/next.png");
        skipButton.getPreferredSize();
        
        prevPlaylistButton = new JButton("PP");
        prevPlaylistButton.getPreferredSize();
        
        nextPlaylistButton = new JButton("NP");
        nextPlaylistButton.getPreferredSize();
        
        volumeSlider = new JSlider();
        volumeSlider.getPreferredSize();
    }
    
    private void addIcon(JButton button, String iconPath) {
        try {
            Image icon = ImageIO.read(View.class.getResource(iconPath));
            button.setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println(iconPath + " not found.");
        }
    }
    
    public JButton getPrevPlaylistButton() {
        return prevPlaylistButton;
    }
    
    public JButton getBackButton() {
        return backButton;
    }
    
    public JButton getPlayButton() {
        return playButton;
    }
    
    public JButton getSkipButton() {
        return skipButton;
    }
    
    public JButton getNextPlaylistButton() {
        return nextPlaylistButton;
    }
    
    public JSlider getVolumeSlider() {
        return volumeSlider;
    }
}