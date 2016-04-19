package chautnguyen.com.github.musicplayer.controller;

import chautnguyen.com.github.musicplayer.model.Model;
import chautnguyen.com.github.musicplayer.view.View;

import javax.media.Player;
import javax.media.Manager;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Controller implements ActionListener {
    private Player player;
    private Model playlist;
    private View GUI;
    
    private int currentSongIndex;
    private boolean isItPlaying;        // flag to change play icon to pause and vice versa

    public Controller() throws Exception {
        this.playlist = new Model();
        this.GUI = new View();

        addActionListeners();

        URL path = Controller.class.getResource("playlists/playlist1.txt");
        File file = new File(path.getFile());
        playlist.loadSongs(file);

        initializePlayer(0);
        isItPlaying = false;
    }

    private void initializePlayer(int index) throws Exception {
        File song = new File(Model.class.getResource(playlist.get(index)).getFile());
        player = Manager.createRealizedPlayer(song.toURI().toURL());
        currentSongIndex = index;
    }

    private void start() {
        player.start();
        GUI.setTitle(playlist.get(currentSongIndex));
    }

    private void stopCurrentSong() {
        player.stop();
    }
    
    // must clean up this class heavily
    private void back() throws Exception {
        if (!isItPlaying && currentSongIndex > 0) {
            // this block makes it so when the user backs while the player's paused, the UI stays the same
            File song = new File(Model.class.getResource(playlist.get(--currentSongIndex)).getFile());
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            GUI.setTitle(playlist.get(currentSongIndex));
        } else if (currentSongIndex > 0) {
            resetIcons();
            stopCurrentSong();          
            File song = new File(Model.class.getResource(playlist.get(--currentSongIndex)).getFile());
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            start();
        } else { // currentSongIndex == the front of the ArrayList
            resetIcons();
            try {
                Image icon = ImageIO.read(View.class.getResource("icons/error.png"));
                GUI.backButton.setIcon(new ImageIcon(icon));
            } catch (IOException ex) {
                System.out.println("icons/error.png not found");
            }
        }
    }

    private void skip() throws Exception {
        if (!isItPlaying && currentSongIndex < playlist.getCount() - 1) {
            // this block makes it so when the user skips while the player's paused, the UI stays the same
            File song = new File(Model.class.getResource(playlist.get(++currentSongIndex)).getFile());
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            GUI.setTitle(playlist.get(currentSongIndex));
        } else if (currentSongIndex < playlist.getCount() - 1) {
            resetIcons();
            stopCurrentSong();
            File song = new File(Model.class.getResource(playlist.get(++currentSongIndex)).getFile());
            // File song = new File(playlist.get(++currentSongIndex)); this will NOT work. must use the getResource function to return a file
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            start();
        } else { // currentSongIndex == the end of the ArrayList
            resetIcons();
            try {
                Image icon = ImageIO.read(View.class.getResource("icons/error.png"));
                GUI.skipButton.setIcon(new ImageIcon(icon));
            } catch (IOException ex) {
                System.out.println("icons/error.png not found");
            }
        }
    }

    public void printCurrentSong() {
        System.out.println("playing : " + playlist.get(currentSongIndex));
    }

    public void printPlaylist() {
        System.out.println("List of songs: ");
        System.out.println("======");
        playlist.printAll();
        System.out.println("======");
    }    

    private void addActionListeners() {
        GUI.backButton.addActionListener(this);
        GUI.playButton.addActionListener(this);
        GUI.skipButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((((JButton) e.getSource()) == GUI.backButton)) {
            try {
                back();
            } catch (Exception ex) {
                // need exception
            }
        }

        if ((((JButton) e.getSource()) == GUI.playButton)) {
            if (!isItPlaying) {
                resetIcons();
                start();

                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                    GUI.playButton.setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/pause.png not found");
                }
                isItPlaying = true;
            } else {
                resetIcons();
                stopCurrentSong();

                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/play.png"));
                    GUI.playButton.setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/play.png not found");
                }
                isItPlaying = false;
            }
        }

        if ((((JButton) e.getSource()) == GUI.skipButton)) {
            try {
                skip();
            } catch (Exception ex) {
                // need exception
            }
        }
    }

    public void resetIcons() {
        try {
            Image icon = ImageIO.read(View.class.getResource("icons/prev.png"));            
            GUI.backButton.setIcon(new ImageIcon(icon));            
        } catch (IOException ex) {
            System.out.println("icons/prev.png not found");
        }

        try {
            Image icon = ImageIO.read(View.class.getResource("icons/next.png"));
            GUI.skipButton.setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println("icons/next.png not found");
        }
    }
}