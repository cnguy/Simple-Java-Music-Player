package chautnguyen.com.github.musicplayer.model;

import java.io.File;

public class Song {
    private File song;
    
    /**
     * Overloaded constructor, creates a File object and puts it into
     * song.
     * 
     * @param   The path of the file to be loaded.
     */
    public Song(String songPath) {        
        song = new File(Song.class.getResource(songPath).getFile());
    }
    
    /**
     * Returns an actual File object.
     * 
     * @return      song, a File object.
     */
    public File getFile() {
        return song;
    }
}
