package chautnguyen.com.github.musicplayer.controller;

import chautnguyen.com.github.musicplayer.model.Model;
import chautnguyen.com.github.musicplayer.view.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.Control;
import javax.media.GainControl;
import javax.media.Player;
import javax.media.Manager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements ActionListener, ChangeListener {
    private Player player;
    private Model playlist;
    private View GUI;

    private int currentSongIndex;
    private boolean isItPlaying;        // flag to change play icon to pause and vice versa

    /**
     * Default constructor. Loads the first song in the playlist (on top of the multiple initializations of the class members).
     */
    public Controller() throws Exception {
        this.playlist = new Model();
        this.GUI = new View();

        addActionListeners();

        // retrieves the file
        URL path = Controller.class.getResource("playlists/playlist1.txt");
        File file = new File(path.getFile());
        playlist.loadSongs(file); // loads songs into playlist, an ArrayList

        // volume slider change listener
        GUI.getVolumeSlider().addChangeListener(this);

        // loads the first song
        currentSongIndex = 0;
        initializePlayer(currentSongIndex);
        isItPlaying = false;
    }

    /**
     * Loads a song into the player.
     * 
     * @param index     the index of the song (in the ArrayList) to be loaded.
     */
    private void initializePlayer(int index) throws Exception {
        File song = new File(Model.class.getResource(playlist.get(index)).getFile());
        player = Manager.createRealizedPlayer(song.toURI().toURL());
    }

    /**
     * Stops the player, loads the next desired song into the player, and starts the player.
     * back() and skip() uses this function. Just need to decrement or increment the currentSongIndex while calling this function.
     * 
     * @param index     the index of the song (in the ArrayList) to be loaded.
     */
    private void loadSongAndPlay(int index) throws Exception {
        stopCurrentSong();
        initializePlayer(index);
        start();
        isItPlaying = true;
    }

    private void addActionListeners() {
        GUI.getBackButton().addActionListener(this);
        GUI.getPlayButton().addActionListener(this);
        GUI.getSkipButton().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        resetIcons();

        if ((((JButton) e.getSource()) == GUI.getBackButton())) {
            try {
                back();
            } catch (Exception ex) {
                // need exception
            }
        }
        // TODO: make it so the pause button becomes a play button
        // once the song is over
        if ((((JButton) e.getSource()) == GUI.getPlayButton())) {
            if (!isItPlaying) {
                start();
                isItPlaying = true;
                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                    GUI.getPlayButton().setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/pause.png not found");
                }
            } else {
                stopCurrentSong();
                isItPlaying = false; // fixed
                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/play.png"));
                    GUI.getPlayButton().setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/play.png not found");
                }
            }
        }

        if ((((JButton) e.getSource()) == GUI.getSkipButton())) {
            try {
                skip();
            } catch (Exception ex) {
                // need exception
            }
        }
    }
    
    /**
     * Starts the player to play the current song, and then sets the title of the music player to music/insertsongnamehere.wav.
     */
    private void start() {
        player.start();
        GUI.setTitle(playlist.get(currentSongIndex));
    }

    /**
     * Stops the player.
     */
    private void stopCurrentSong() {
        player.stop();
    }

    // must clean up this class heavily

    /**
     * Loads a song with the currentSongIndex (that was just decremented), if it exists, into the player.
     */
    private void back() throws Exception {
        if (currentSongIndex == 0) {    // Change button to display error symbol if user tries to press the back button when it's not possible to go back any further.
            try {
                Image icon = ImageIO.read(View.class.getResource("icons/error.png"));
                GUI.getBackButton().setIcon(new ImageIcon(icon));
            } catch (IOException ex) {
                System.out.println("icons/error.png not found");
            }            
        } else {    // In any other case, just load the previous song and immediately start the player.
            loadSongAndPlay(--currentSongIndex);
            try {
                Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                GUI.getPlayButton().setIcon(new ImageIcon(icon));
            } catch (IOException ex) {
                System.out.println("icons/pause.png not found");
            }
        }
    }

    /**
     * Loads a song with the currentSongIndex (that was just incremented), if it exists, into the player.
     */
    private void skip() throws Exception {
        if (currentSongIndex >= playlist.getCount() - 2) {      // Change button to display error symbol if user tries to press the skip button when it's not possible to go any further.
            try {
                Image icon = ImageIO.read(View.class.getResource("icons/error.png"));
                GUI.getSkipButton().setIcon(new ImageIcon(icon));
            } catch (IOException ex) {
                System.out.println("icons/error.png not found");
            }
        } else { // In any other case, just load the next song and immediately start the player.
            loadSongAndPlay(++currentSongIndex);
            try {
                Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                GUI.getPlayButton().setIcon(new ImageIcon(icon));
            } catch (IOException ex) {
                System.out.println("icons/pause.png not found");
            }            
        }
    }
    
    /**
     * Resets the skip and back button icons. This is used in case one of the buttons have an error icon.
     */
    private void resetIcons() {
        try {
            Image icon = ImageIO.read(View.class.getResource("icons/prev.png"));            
            GUI.getBackButton().setIcon(new ImageIcon(icon));            
        } catch (IOException ex) {
            System.out.println("icons/prev.png not found");
        }
        try {
            Image icon = ImageIO.read(View.class.getResource("icons/next.png"));
            GUI.getSkipButton().setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println("icons/next.png not found");
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if (player != null) {
            float sliderValue = (float)GUI.getVolumeSlider().getValue();
            // jmf keeps catching exceptions despite the fact that volumnLevel is always in [0, 1.0]
            // so instead of dividing sliderValue by 100.0 (so that every slider value corresponds with the appropriate float value (1 = .01)
            // I'm dividing it by 150 (as long as the resulting volumnLevel is not 1.0 or near it)..
            float volumnLevel = sliderValue / 150.0f;
            (player.getGainControl()).setLevel(volumnLevel);
        }
    }
}