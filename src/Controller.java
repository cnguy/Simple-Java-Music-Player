import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Controller Class.
 */
class Controller {
    private Player player;
    Model playlist;
    private int currentSongIndex;

    public Controller() {
        playlist = new Model();
        playlist.loadSongs();
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
    
    public void skip() throws Exception {
        if (currentSongIndex < playlist.getCount()) {
            player.stop();
            File song = new File(playlist.get(currentSongIndex + 1));
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            player.start();
            currentSongIndex++;
        } else {
            player.stop();
            System.out.println("No more songs to play.");
        }
    }
    
    public void printPlaylist() {
        System.out.println("List of songs: ");
        playlist.printAll();
        System.out.println("======");
    }
}