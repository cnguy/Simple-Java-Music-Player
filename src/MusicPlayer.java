import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Controller Class.
 */
class MusicPlayer {
    private Player player;
    Model playlist;
    private int currentSongIndex;

    public MusicPlayer() {
        playlist = new Model();
    }

    public void addSongs() {
        playlist.add("music/BnHA_op1.wav");
        playlist.add("music/G_S2_ed3.wav");
        playlist.add("music/G_S2_ed5.wav");
        playlist.add("music/G_S2_op2.wav");
        playlist.add("music/G_S2_op4.wav");   
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

    public static void main(String args[]) throws Exception {
        try {         
            MusicPlayer player = new MusicPlayer();
            player.addSongs();
            player.initializePlayer(0);
            
            int numberOfSongsLeft = 5;
            
            player.start();
            numberOfSongsLeft--;
            
            System.out.println("Number of songs left: " + numberOfSongsLeft);

            Scanner s = new Scanner(System.in);
            String st;
            
            while (!player.areThereNoMoreSongs()) {
                st = s.nextLine();
                
                if (numberOfSongsLeft > 0) {
                    if (st.equals("skip")) {
                        player.skip();
                    }
                    if (st.equals("stop")) {
                        player.endPlayer();
                        System.exit(1);
                    }
                }
                
                if (numberOfSongsLeft == 0) {
                    if (st.equals("stop")) {
                        player.stopCurrentSong();
                    }
                }

                numberOfSongsLeft--;
                System.out.println("Number of songs left: " + numberOfSongsLeft);
            }
        } catch (CannotRealizeException e) {
            System.out.println("Unable to read file.");
        }
    }
}