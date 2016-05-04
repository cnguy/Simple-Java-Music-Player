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
    
    public void add(Playlist playlist) {
        playlistsContainer.add(playlist);
    }
    
    public Playlist getPlaylist(int index) {
        return playlistsContainer.get(index);
    }
    
    public int getNumberOfPlaylists() {
        return playlistsContainer.size();
    }
    
    public void loadSongs(File file, int index) throws IOException {
        playlistsContainer.get(index).loadSongs(file);
    }
}
