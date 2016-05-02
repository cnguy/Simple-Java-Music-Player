package chautnguyen.com.github.musicplayer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlaylistsContainer {
    private ArrayList<Playlist> playlistsContainer;
    
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
    
    public void loadSongs(File file, int currentPlaylistIndex) throws IOException {
        playlistsContainer.get(currentPlaylistIndex).loadSongs(file);
    }
    
    public static void main(String[] args) {
        PlaylistsContainer playlists = new PlaylistsContainer();
    }
}
