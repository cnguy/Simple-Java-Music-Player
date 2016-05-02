# Simple-Java-MP3-Player
Simple Java MP3 player for fun. I got a lot farther since the beginning. :)</br>

Created using Swing and JMF. 

<b>How to load a playlist..</b></br>
  1. Within the "model" directory, create a "music" folder and place whatever song files you want in there (Note: Java doesn't support MP3s, or at least JMF doesn't.. so I converted the MP3s to WAVs.).</br>
  2. Within the "controller" directory, create a "playlists" folder.
    1. In there, create text files and name them whatever you want. For example: "anime_openings.txt", "blackbear_songs.txt.
    2. To register a song to a playlist, go into the appropriate playlist text file, and type on the first line "music/name_of_song.wav". To enter more, just make a new line and type "music/song_name.wav" again.
    3. Change the implementation within the Controller.java class so that it loads the correct names.</br>
    ```java
    42: loadSongsIntoPlaylist("anime_openings");
    43: loadSongsIntoPlaylist("blackbear_songs");
    ```
