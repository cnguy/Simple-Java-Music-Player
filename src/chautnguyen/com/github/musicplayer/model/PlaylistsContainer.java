package chautnguyen.com.github.musicplayer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsContainer {
    private List<Playlist> playlistsContainer;
    
    public PlaylistsContainer() {
        playlistsContainer = new ArrayList<Playlist>(); 
    }
    
    /**
     * Passes parameters to a playlist within playlistsContainer.
     * 
     * @param File      The File (playlist text file) to be read.
     * @param index     the index of the playlist.
     */
    public void loadSongs(File file, int index) throws IOException {
        playlistsContainer.get(index).loadSongs(file);
    }
    
    /**
     * Calls the playlist and song methods to return a File object.
     * 
     * @param   currentPlaylistIndex    playlist to be loaded.
     * @param   currentSongIndex        song of the above playlist to be loaded.
     * 
     * @return  returns a file directly.
     */
    public File getSong(int currentPlaylistIndex, int currentSongIndex) {
        return playlistsContainer.get(currentPlaylistIndex).getSong(currentSongIndex).getFile();
    }
    
    public void add(Playlist playlist) {
        playlistsContainer.add(playlist);
    }
    
    public Playlist getPlaylist(int index) {
        return playlistsContainer.get(index);
    }        
    
    public int getNumberOfPlaylists() {
        return playlistsContainer.size();
    }        
}
