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
        try {
            Controller musicPlayer = new Controller();

            System.out.println("======================");
            System.out.println("Loading songs...");
            System.out.println("======================");

            displayMenu();
            int index = getIndex();
            
            switch (index) {
                case 1: musicPlayer.printPlaylist(); break;
                case 2: break;
                case 3: musicPlayer.printPlaylist();
                Scanner scanner = new Scanner(System.in);
                System.out.print("Song index to be removed: ");
                int indexOfSong = scanner.nextInt();
                musicPlayer.removeSong(--indexOfSong);
                System.out.println("removed.");
                musicPlayer.printPlaylist();
                break;
                default: break;
            }

            musicPlayer.start();

            System.out.println("Number of songs left: " + musicPlayer.getNumberOfSongsLeft());

            Scanner s = new Scanner(System.in);
            String st;

            while (!musicPlayer.areThereNoMoreSongs()) {
                st = s.nextLine();

                if (musicPlayer.getNumberOfSongsLeft() > 0) {
                    if (st.equals("skip")) {
                        musicPlayer.skip();
                    }
                    if (st.equals("stop")) {
                        musicPlayer.endPlayer();
                        System.exit(0);
                    }
                }

                System.out.println("Number of songs left: " + musicPlayer.getNumberOfSongsLeft());
            }
            
            if (musicPlayer.getNumberOfSongsLeft() == 0) {
                st = s.nextLine();
                if (st.equals("stop")) {
                    musicPlayer.stopCurrentSong();
                    System.exit(0);
                }
            }
        } catch (CannotRealizeException e) {
            System.out.println("Unable to read file.");
        }
    }
}