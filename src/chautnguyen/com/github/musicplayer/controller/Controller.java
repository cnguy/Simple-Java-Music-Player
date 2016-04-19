package chautnguyen.com.github.musicplayer.controller;

import chautnguyen.com.github.musicplayer.model.Model;
import chautnguyen.com.github.musicplayer.view.View;

import javax.media.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Controller implements ActionListener {
    private Player player;
    private Model playlist;
    private View GUI;
    private int currentSongIndex;
    private int numberOfSongsLeft;
    private boolean isItPlaying;        // flag to change play icon to pause and vice versa

    public Controller() throws Exception {
        this.playlist = new Model();
        this.GUI = new View();

        addActionListeners();

        URL path = Controller.class.getResource("playlists/playlist1.txt");
        File file = new File(path.getFile());
        playlist.loadSongs(file);

        numberOfSongsLeft = playlist.getCount();
        initializePlayer(0);
        isItPlaying = false;
    }

    private void initializePlayer(int index) throws Exception {
        File song = new File(Model.class.getResource(playlist.get(index)).getFile());
        player = Manager.createRealizedPlayer(song.toURI().toURL());
        currentSongIndex = index;
    }

    private void start() {
        player.start();
        GUI.setTitle(playlist.get(currentSongIndex));
        numberOfSongsLeft--;
    }

    private void stopCurrentSong() {
        player.stop();
    }

    public boolean areThereNoMoreSongs() {
        if (currentSongIndex == playlist.getCount() - 1) {
            return true;
        } else {
            return false;
        }
    }

    private void endPlayer() {
        player = null;
    }

    private void back() throws Exception {
        if (currentSongIndex > 0) {
            stopCurrentSong();          
            File song = new File(Model.class.getResource(playlist.get(--currentSongIndex)).getFile());
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            start();
            numberOfSongsLeft++;
        } else {
            System.out.println("Can't go back further.");
        }
    }

    private void skip() throws Exception {
        if (currentSongIndex < playlist.getCount() - 1) {
            stopCurrentSong();
            File song = new File(Model.class.getResource(playlist.get(++currentSongIndex)).getFile());
            // File song = new File(playlist.get(++currentSongIndex)); this will NOT work. must use the getResource function to return a file
            player = Manager.createRealizedPlayer(song.toURI().toURL());
            start();
            numberOfSongsLeft--;
        } else {
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

    private void addActionListeners() {
        GUI.backButton.addActionListener(this);
        GUI.playButton.addActionListener(this);
        GUI.skipButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((((JButton) e.getSource()) == GUI.backButton)) {
            try {
                back();
            } catch (Exception ex) {
                System.out.println("too far left");
            }

            if (!isItPlaying) {
                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                    GUI.playButton.setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/back.png not found");
                }

                isItPlaying = true;
            }
        }

        if ((((JButton) e.getSource()) == GUI.playButton)) {
            if (!isItPlaying) {
                start();
                
                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                    GUI.playButton.setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/pause.png not found");
                }

                isItPlaying = true;
            } else {
                stopCurrentSong();
                
                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/play.png"));
                    GUI.playButton.setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/play.png not found");
                }

                isItPlaying = false;
            }
        }

        if ((((JButton) e.getSource()) == GUI.skipButton)) {
            try {
                skip();
            } catch (Exception ex) {
                System.out.println("too far right");
            }

            if (!isItPlaying) {
                try {
                    Image icon = ImageIO.read(View.class.getResource("icons/pause.png"));
                    GUI.playButton.setIcon(new ImageIcon(icon));
                } catch (IOException ex) {
                    System.out.println("icons/skip.png not found");
                }

                isItPlaying = true;
            }
        }
    }
}