package chautnguyen.com.github.musicplayer.model;

import java.io.File;

public class Song {
    private File song;
    
    public Song(String songPath) {
        // File song = new File(Playlist.class.getResource(playlists.getPlaylist(currentPlaylistIndex).get(currentSongIndex)).getFile());
        song = new File(Song.class.getResource(songPath).getFile());
    }
    
    public File getFile() {
        return song;
    }
}
