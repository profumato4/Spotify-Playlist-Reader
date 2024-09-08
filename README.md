# Spotify Playlist Reader

![GitHub License](https://img.shields.io/github/license/profumato4/Spotify-Playlist-Reader)
[![Download](https://img.shields.io/github/downloads/profumato4/Spotify-Playlist-Reader/total)](https://github.com/profumato4/Spotify-Playlist-Reader/releases/tag/1.0)
![GitHub Repo stars](https://img.shields.io/github/stars/profumato4/Spotify-Playlist-Reader)



Spotify Playlist Reader deals with analyzing the playlists (for the moment only the public ones) and showing the data obtained from the playlist in a table with different columns such as the number of times the song is present in the playlist, the duration of the song and more

https://github.com/user-attachments/assets/4c9a571c-56bd-43ed-b72d-a3667d729394

You can't see it from the video but by clicking on option you can see more columns or fewer columns or go back to the first page

# How to use it

### Easy way

Just go the [releases](https://github.com/profumato4/Spotify-Playlist-Reader/releases), install the latest version of the .exe file and run it



### Hard way

In this step you need to modify some lines of code to make the program work. 

First let's go to [developer spotify](https://developer.spotify.com/dashboard) in the dashboard and create a new app. Enter a name for your app and the various information required

Now go back to the dashboard and click on your app, then settings and copy your `client id` and your `client secret`

Now open the main.py file and replace the following lines with the `client id` and `client secret`

```python
client_id = "YOUR CLIENT ID"      # Replace with your client ID
client_secret = "YOUR CLIENT SECRET"  # Replace with your client secret
```

Save the file and run the java main file `App.java` in `src/gui` . 

# License
This software is released under the MIT license. See the file [LICENSE](https://github.com/profumato4/Spotify-Playlist-Reader/blob/master/LICENSE.MD) for further information.

# Questions or problems?
If you have questions or encounter issues, please use [issue tracker](https://github.com/profumato4/Spotify-Playlist-Reader/issues) in the repository to report them.


