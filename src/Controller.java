import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

class Controller implements ActionListener {
    private Player player;
    private Model playlist;
    private View GUI;
    private int currentSongIndex;
    private int numberOfSongsLeft;
    private boolean isItPlaying;        // flag to change play icon to pause and vice versa

    public Controller() throws Exception {
        this.playlist = new Model();
        this.GUI = new View();

        addActionListeners();
        File file = new File("playlist.txt");
        playlist.loadSongs(file);
        numberOfSongsLeft = playlist.getCount();
        initializePlayer(0);
        isItPlaying = false;
    }

    public void initializePlayer(int index) throws Exception {
        File song = new File(playlist.get(index));
        player = Manager.createRealizedPlayer(song.toURI().toURL());
        currentSongIndex = index;
    }
    
    public void start() {
        player.start();
        GUI.setTitle(playlist.get(currentSongIndex));
        // GUI.currentlyPlaying.setText(playlist.get(currentSongIndex));
        numberOfSongsLeft--;
    }

    public void stopCurrentSong() {
        player.stop();
    }

    public boolean areThereNoMoreSongs() {
        if (currentSongIndex == playlist.getCount() - 1) {
            return true;
        } else {
            return false;
        }
    }

    public void endPlayer() {
        player = null;
    }

    public void back() throws Exception {
        if (currentSongIndex > 0) {
            stopCurrentSong();
            File song = new File(playlist.get(--currentSongIndex));
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            start();
            numberOfSongsLeft++;
        } else {
            System.out.println("Can't go back further.");
        }
    }

    public void skip() throws Exception {
        if (currentSongIndex < playlist.getCount() - 1) {
            stopCurrentSong();
            File song = new File(playlist.get(++currentSongIndex));
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            start();
            numberOfSongsLeft--;
        } else {
            System.out.println("No more songs to play.");
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

    public void removeSong(int index) {
        playlist.remove(index);
        numberOfSongsLeft--;
    }

    public int getNumberOfSongsLeft() {
        return numberOfSongsLeft;
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
                System.out.println("too far left");
            }
        }
        
        if ((((JButton) e.getSource()) == GUI.playButton)) {
            if (!isItPlaying) {
                start();

                try {
                    Image img = ImageIO.read(getClass().getResource("icons/pause.png"));
                    GUI.playButton.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    System.out.println("icons/pause.png not found");
                }
                
                isItPlaying = true;
            } else {
                stopCurrentSong();
                
                try {
                    Image img = ImageIO.read(getClass().getResource("icons/play.png"));
                    GUI.playButton.setIcon(new ImageIcon(img));
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
                System.out.println("too far right");
            }
        }
    }
}