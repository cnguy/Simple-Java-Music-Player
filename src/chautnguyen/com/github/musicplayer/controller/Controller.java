package chautnguyen.com.github.musicplayer.controller;

import chautnguyen.com.github.musicplayer.model.Playlist;
import chautnguyen.com.github.musicplayer.model.PlaylistsContainer;
import chautnguyen.com.github.musicplayer.view.View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.GainControl;
import javax.media.Player;
import javax.media.Manager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements ActionListener, ChangeListener {
    private Player player;
    private PlaylistsContainer playlists;
    private View GUI;
    
    private int currentPlaylistIndex;
    private int currentSongIndex;
    private boolean isItPlaying;        // flag to change play icon to pause and vice versa

    /**
     * Default constructor. Loads the first song in the playlist (on top of the multiple initializations of the class members).
     */
    public Controller() throws Exception {
        this.playlists = new PlaylistsContainer();
        this.GUI = new View();

        addActionListeners();

        loadSongsIntoPlaylist("playlist1");
        loadSongsIntoPlaylist("playlist2");
        
        // volume slider change listener
        GUI.getVolumeSlider().addChangeListener(this);

        // loads the first song of the first playlist
        currentPlaylistIndex = 0;
        currentSongIndex = 0;
        initializePlayer(currentPlaylistIndex, currentSongIndex);
        isItPlaying = false;
    }
    
    /**
     * Adds a playlist, and loads the song names into that playlist.
     * 
     * @param       The name of the playlist. Ex: playlist1.txt
     */
    private void loadSongsIntoPlaylist(String playlistName) throws Exception {
        URL path = Controller.class.getResource("playlists/" + playlistName + ".txt");
        File file = new File(path.getFile());
        playlists.add(new Playlist());
        playlists.loadSongs(file, playlists.getNumberOfPlaylists() - 1);
    }
    
    /**
     * Loads a song into the player.
     * 
     * @param index     the index of the song (in the ArrayList) to be loaded.
     */
    private void initializePlayer(int currentPlaylistIndex, int currentSongIndex) throws Exception {
        File song = new File(Playlist.class.getResource(playlists.getPlaylist(currentPlaylistIndex).get(currentSongIndex)).getFile());
        player = Manager.createRealizedPlayer(song.toURI().toURL());
    }

    /**
     * Stops the player, loads the next desired song into the player, and starts the player.
     * back() and skip() uses this function. Just need to decrement or increment the currentSongIndex while calling this function.
     * 
     * @param index     the index of the song (in the ArrayList) to be loaded.
     */
    private void loadSongAndPlay(int currentPlaylistIndex, int currentSongIndex) throws Exception {
        stopCurrentSong();
        initializePlayer(currentPlaylistIndex, currentSongIndex);
        start();
    }

    private void addActionListeners() {
        GUI.getPrevPlaylistButton().addActionListener(this);
        GUI.getBackButton().addActionListener(this);
        GUI.getPlayButton().addActionListener(this);
        GUI.getSkipButton().addActionListener(this);
        GUI.getNextPlaylistButton().addActionListener(this);
    }
    
    /**
     * Checks if a button is clicked, and executes the appropriate method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        resetIcons(); // resets both the skip and back buttons
        
        if ((((JButton) e.getSource()) == GUI.getPrevPlaylistButton())) {
            try {
                prevPlaylist();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        
        if ((((JButton) e.getSource()) == GUI.getBackButton())) {
            try {
                back();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        // TODO: make it so the pause button becomes a play button
        // once the song is over
        if ((((JButton) e.getSource()) == GUI.getPlayButton())) {
            if (!isItPlaying) {
                start();
                changePlayToPause();
            } else {
                stopCurrentSong();
                changePauseToPlay();
            }
        }

        if ((((JButton) e.getSource()) == GUI.getSkipButton())) {
            try {
                skip();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        
        if ((((JButton) e.getSource()) == GUI.getNextPlaylistButton())) {
            try {
                nextPlaylist();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Starts the player to play the current song, and then sets the title of the music player to music/insertsongnamehere.wav.
     */
    private void start() {
        player.start();
        // The line below makes sure the song being played applies the current volume level of the slider.
        // For example, if the current song is muted, and the user presses skip or back, THAT song should be muted as well, or else the slider would not make sense.
        // This line is needed for that. Otherwise the next song coming up would always be played at the default volume.
        (player.getGainControl()).setLevel((float)GUI.getVolumeSlider().getValue() / 150.0f);
        GUI.setTitle(playlists.getPlaylist(currentPlaylistIndex).get(currentSongIndex));
        isItPlaying = true;
    }

    /**
     * Stops the player.
     */
    private void stopCurrentSong() {
        player.stop();
        isItPlaying = false;
    }
    
    private void prevPlaylist() throws Exception {
        if (currentPlaylistIndex == 0) {
            System.out.println("Cannot do that.");            
        } else {
            currentSongIndex = 0;    // reset song index
            loadSongAndPlay(--currentPlaylistIndex, currentSongIndex);
            changePlayToPause();
        }
    }      
    
    /**
     * Loads a song with the currentSongIndex (that was just decremented), if it exists, into the player.
     */
    private void back() throws Exception {
        if (currentSongIndex == 0) {    // Change button to display error symbol if user tries to press the back button when it's not possible to go back any further.
            changeBackToError();
        } else {    // In any other case, just load the previous song and immediately start the player.
            loadSongAndPlay(currentPlaylistIndex, --currentSongIndex);
            changePlayToPause();
        }
    }

    /**
     * Loads a song with the currentSongIndex (that was just incremented), if it exists, into the player.
     */
    private void skip() throws Exception {
        if (currentSongIndex >= playlists.getPlaylist(currentPlaylistIndex).getCount() - 2) {      // Change button to display error symbol if user tries to press the skip button when it's not possible to go any further.
            changeSkipToError();
        } else { // In any other case, just load the next song and immediately start the player.
            loadSongAndPlay(currentPlaylistIndex, ++currentSongIndex);
            changePlayToPause();
        }
    }
    
    private void nextPlaylist() throws Exception {
        if (currentPlaylistIndex == playlists.getNumberOfPlaylists() - 1) {
            System.out.println("No more playlists to skip.");
        } else {
            currentSongIndex = 0;   // reset song index
            loadSongAndPlay(++currentPlaylistIndex, currentSongIndex);
            changePlayToPause();
        }
    }
    /**
     * Changes the play icon to a pause icon.
     */
    private void changePlayToPause() {
        Image icon;
        try {
            icon = ImageIO.read(View.class.getResource("icons/pause.png"));
            GUI.getPlayButton().setIcon(new ImageIcon(icon));           
        } catch (IOException ex) {
            System.out.println("icons/pause.png not found.");
        }
    }       
    
    /**
     * Changes the pause icon to a play icon.
     */
    private void changePauseToPlay() {
        Image icon;
        try {
            icon = ImageIO.read(View.class.getResource("icons/play.png"));
            GUI.getPlayButton().setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println("icons/play.png not found.");
        }
    }      
    
    /**
     * Changes the back icon to an error icon. To be used when
     * user tries to go back past the very first song.
     */
    private void changeBackToError() {
        Image icon;
        try {
            icon = ImageIO.read(View.class.getResource("icons/error.png"));
            GUI.getBackButton().setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println("icons/back.png not found");
        }
    }
    
    /**
     * Changes the skip icon to an error icon. To be used when
     * user tries to skip past the very last song.
     */
    private void changeSkipToError() {
        Image icon;
        try {
            icon = ImageIO.read(View.class.getResource("icons/error.png"));
            GUI.getSkipButton().setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            System.out.println("icons/skip.png not found.");
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
    
    /**
     * Checks if the volume slider level is changed.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (player != null) {
            // jmf keeps catching exceptions despite the fact that volumnLevel is always in [0, 1.0]
            // so instead of dividing sliderValue by 100.0 (so that every slider value corresponds with the appropriate float value (1 = .01)
            // I'm dividing it by 150 (as long as the resulting volumnLevel is not 1.0 or near it)..
            (player.getGainControl()).setLevel((float)GUI.getVolumeSlider().getValue() / 150.0f); // gets slider value, converts it, and sets the level
        }
    }
}