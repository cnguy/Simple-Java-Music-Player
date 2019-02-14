# Simple-Java-Music-Player
Simple Java Music player created with Java, Swing, and JMF. I had a lot of fun creating this.</br>

<b>How to setup the playlists for the music player to process and load..</b></br>
  1. Within the "../model" directory, create a "music" folder, create a folder and name it something easy since you'll be using it again. In that folder, create playlist subfolders, and place whatever song files you want in there.</br>Note: Java doesn't support MP3s, or at least JMF doesn't.. so I converted the MP3s to WAVs.).</br>In my case, I created two folders: <b>anime_playlist1</b> & <b>blackbear.</b></br>
  2. Within the "../controller" directory, create a "playlists" folder.
    1. In there, create text files and name them whatever you want. I suggest naming them the same as the folders. Thus, I named them as the following: "anime_playlist1.txt", "blackbear.txt".
    2. To register a song to a playlist, go into the appropriate playlist text file, and type on the first line "music/insert_corresponding_folder_name/song_name.wav". If you want to register more songs, just add a new line and repeat it. Because of the way the program is coded, you could type in duplicate addresses to get multiple songs so keep that in mind.
    </br> Examples below!</br>
    <b>anime_openings.txt</b></br>
        ```
        music/anime_playlist1/BnHA_op1.wav
        music/anime_playlist1/G_S2_ed3.wav
        music/anime_playlist1/G_S2_ed5.wav
        music/anime_playlist1/G_S2_op2.wav
        music/anime_playlist1/G_S2_op4.wav
        ```
    <b>blackbear.txt</b></br>
      ```
        music/blackbear/bb_nyla.wav
        music/blackbear/bb_90210.wav
      ```
  3. Change the implementation within the Controller.java class so that it has the correct names (without the .txt extension).</br>
    ```
    42: loadSongsIntoPlaylist("anime_playlist1");
    ```</br>
    ```
    43: loadSongsIntoPlaylist("blackbear");
    ```
