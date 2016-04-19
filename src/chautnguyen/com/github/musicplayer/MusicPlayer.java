package chautnguyen.com.github.musicplayer;

import chautnguyen.com.github.musicplayer.controller.Controller;

import java.util.Scanner;
import javax.media.CannotRealizeException;

public class MusicPlayer {
    public static void displayMenu() {
        System.out.println("1. Display songs.");
        System.out.println("2. Add a song to the playlist.");
        System.out.println("3. Remove a song from the playlist.");
    }

    public static int getIndex() {
        int index;
        Scanner s = new Scanner(System.in);
        index = s.nextInt();
        return index;
    }

    public static void main(String[] args) throws Exception {
        Controller musicPlayer = new Controller();
    }
}