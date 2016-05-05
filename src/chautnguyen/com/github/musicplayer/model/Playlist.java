package chautnguyen.com.github.musicplayer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private List<Song> playlist;
    
    public Playlist() {
        playlist = new ArrayList<>();
    }
    
    /**
     * Loads as many song objects possible into the playlist based on
     * how many song file paths there are in the specified text file.
     * 
     * @param fin       The file (a text file) to be read.
     */
    public void loadSongs(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        
        while ((line = br.readLine()) != null) {
            playlist.add(new Song(line));
        }
        
        br.close();
    }
    
    /**
     * Returns the song at the index within the playlist.
     * 
     * @param index     the index of the song within the playlist.
     * @return          returns a Song object.
     */
    public Song get(int index) {
        return playlist.get(index);
    }
    
    /**
     * Returns a Song object.
     * 
     * @index currentSongIndex  The index of the song to be loaded.
     * @return      returns a song object from a playlist.
     */
    public Song getSong(int currentSongIndex) {
        return playlist.get(currentSongIndex);
    }
    
    /**
     * Returns the size of the playlist.
     * 
     * @return          returns the size of the playlist.
     */
    public int getCount() {
        return playlist.size();
    }
}