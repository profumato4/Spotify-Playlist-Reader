# Spotify Playlist Reader

![GitHub License](https://img.shields.io/github/license/profumato4/Spotify-Playlist-Reader)
[![Download](https://img.shields.io/github/downloads/profumato4/Spotify-Playlist-Reader/total)](https://github.com/profumato4/Spotify-Playlist-Reader/releases)
![GitHub Repo stars](https://img.shields.io/github/stars/profumato4/Spotify-Playlist-Reader)



Spotify Playlist Reader deals with analyzing the playlists (for the moment only the public ones) and showing the data obtained from the playlist in a table with different columns such as the number of times the song is present in the playlist, the duration of the song and more

https://github.com/user-attachments/assets/d0493a6e-1908-4879-ba1c-9b68fd7a311f


# How to Install it

### Easy Way

Just go the [releases](https://github.com/profumato4/Spotify-Playlist-Reader/releases), install the latest version of the .exe file and run it



### Hard Way

In this step you need to modify some lines of code to make the program work. 

First let's go to [developer spotify](https://developer.spotify.com/dashboard) in the dashboard and create a new app. Enter a name for your app and the various information required

Now go back to the dashboard and click on your app, then settings and copy your `client id` and your `client secret`

Now open the main.py file and replace the following lines with the `client id` and `client secret`

```python
client_id = "YOUR CLIENT ID"      # Replace with your client ID
client_secret = "YOUR CLIENT SECRET"  # Replace with your client secret
```

Save the file and run the java main file `App.java` in `src/gui` . 

# How to Use it

Once you’ve installed the `.exe`  file or run the software in an IDE, the first step is to get the playlist link or playlist ID. Before pressing enter or clicking on the "Done" button, you can adjust settings in the menu bar to customize your view.

### Menu Bar Option:

- **Option Menu:**

    - **Show Every song:** This checkbox, when checked, will display all songs in the table. If unchecked, only songs that appear in the playlist more than once will be shown. 

    - **Settings:** Modify the columns displayed in the table. By default, the columns are `Artist`, `Track`, and `Occurrences`. You can add or remove columns like `Album`, `Duration (min)`, and others.

    - **Go back to main menu:** This option returns you to the main screen.

- **Download Menu:** 

    - **Export Data:** Allows you to export playlist data to various formats (CSV, JSON, or XML).

    - **Export Audio:** (Currently unavailable) will enable downloading the audio files when implemented. 


# License
This software is released under the MIT license. See the file [LICENSE](https://github.com/profumato4/Spotify-Playlist-Reader/blob/master/LICENSE.MD) for further information.

# Questions or Problems?
If you have questions or encounter issues, please use [issue tracker](https://github.com/profumato4/Spotify-Playlist-Reader/issues) in the repository to report them.


