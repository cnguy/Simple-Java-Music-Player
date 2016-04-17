import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Controller Class.
 */
class Controller implements ActionListener {
    private Player player;
    private Model playlist;
    private View GUI;
    private int currentSongIndex;
    private int numberOfSongsLeft;

    public Controller() throws Exception {
        this.playlist = new Model();
        this.GUI = new View();
        
        addActionListeners();
        File file = new File("playlist.txt");
        playlist.loadSongs(file);
        numberOfSongsLeft = playlist.getCount();
        initializePlayer(0);
    }

    public void initializePlayer(int index) throws Exception {
        File song = new File(playlist.get(index));
        player = Manager.createRealizedPlayer(song.toURI().toURL());
        currentSongIndex = index;
    }

    public Player getPlayer() {
        return player;
    }

    public void start() {
        player.start();
        printCurrentSong();
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
            player.stop();
            File song = new File(playlist.get(--currentSongIndex));
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            player.start();
            printCurrentSong();
            numberOfSongsLeft++;
        } else {
            System.out.println("Can't go back further.");
        }
    }
    
    public void skip() throws Exception {
        if (currentSongIndex < playlist.getCount() - 1) {
            player.stop();
            File song = new File(playlist.get(++currentSongIndex));
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            player.start();
            printCurrentSong();
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
            System.out.println("back");
            try {
                back();
            } catch (Exception ex) {
                System.out.println("too far left");
            }
        }
        if ((((JButton) e.getSource()) == GUI.playButton)) {
            System.out.println("play");
            start();
        }
        if ((((JButton) e.getSource()) == GUI.skipButton)) {
            System.out.println("skip");
            try {
                skip();
            } catch (Exception ex) {
                System.out.println("too far right");
            }
        }
    }
}