package chautnguyen.com.github.musicplayer.model;

import java.util.ArrayList;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Model {
    private ArrayList<String> playlist;

    public Model() {
        playlist = new ArrayList<String>();
    }
    
    public void loadSongs(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        
        while ((line = br.readLine()) != null) {
            playlist.add(line);
        }
        
        br.close();
    }
    
    public void add(String fileName) {
        playlist.add(fileName);
    }
    
    public String get(int index) {
        return playlist.get(index);
    }
    
    public int getCount() {
        return playlist.size();
    }
}