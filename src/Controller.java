import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Controller Class.
 */
class Controller {
    private Player player;
    private Model playlist;
    private int currentSongIndex;
    private int numberOfSongsLeft;
    
    public Controller() throws IOException {
        playlist = new Model();
        File file = new File("playlist.txt");
        playlist.loadSongs(file);
        numberOfSongsLeft = playlist.getCount();
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
    
    public void skip() throws Exception {
        if (currentSongIndex < playlist.getCount()) {
            player.stop();
            File song = new File(playlist.get(currentSongIndex + 1));
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            player.start();
            printCurrentSong();
            currentSongIndex++;
            numberOfSongsLeft--;
        } else {
            player.stop();
            numberOfSongsLeft--;
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
}