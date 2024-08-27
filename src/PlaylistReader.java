import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.core5.http.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

public class PlaylistReader {

    // The ID of the playlist to be processed

    static String playlistId = retriveSpotifyPlaylistID("YOUR PLAYLIST LINK");
                                
    public static void main(String[] args)
            throws ParseException, SpotifyWebApiException, IOException, InterruptedException {

        // Start a Python process to retrieve the Spotify access token
        ProcessBuilder processBuilder = new ProcessBuilder("python", "main.py");
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        // Read the token from the Python script output

        String token = reader.readLine();
        System.out.println(token);  // Debugging: print the token to verify it isn't null
        reader.close();

        int offset = 0; // Offset for pagination
        Map<String, Integer> trackCountMap = new HashMap<>();       // Map to count track occurrences


        while (true) {

            // Initialize Spotify API with the access token

            SpotifyApi api = new SpotifyApi.Builder().setAccessToken(token).build();

            // Build a request to retrieve playlist items with a limit and offset

            GetPlaylistsItemsRequest getPlaylistRequest = api.getPlaylistsItems(playlistId).limit(1).offset(offset)
                    .build();

            offset += 1;

            try {

                // Execute the request to get playlist items

                Paging<PlaylistTrack> playlistTrackPaging = getPlaylistRequest.execute();
                PlaylistTrack[] items = playlistTrackPaging.getItems();

                if(items.length == 0){
                    break;
                }

                // Process each track in the response

                for (int i = 0; i < items.length; i++) {

                    Track track = (Track) items[i].getTrack();          // Get track details
                    String trackName = track.getName();                 // Get track name
                    ArtistSimplified[] artists = track.getArtists();    // Get track artists
                    String artistNames = getArtistsNames(artists);      // Format artist names
                    String trackKey = trackName + " - " + artistNames;  // Unique key for track

                    // Update the track occurrence count in the map

                    if (trackCountMap.containsKey(trackKey)) {
                        int count = trackCountMap.get(trackKey);
                        trackCountMap.put(trackKey, count + 1);
                    } else {
                        trackCountMap.put(trackKey, 1);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SpotifyWebApiException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int x = 0;

        // Print out tracks that are present multiple times

        for (Map.Entry<String, Integer> entry : trackCountMap.entrySet()) {
            String trackNameWithArtist = entry.getKey();
            int count = entry.getValue();

            if (count > 1) {
                x++;
                System.out.println(trackNameWithArtist + " ( " + count + " times)");
            }else{
                
            }
        }

        if (x == 0) {
            System.out.println("There are no identical songs in the playlist");
        }

    }

    // Helper method to format artist names

    private static String getArtistsNames(ArtistSimplified[] artists) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < artists.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(artists[i].getName());
        }
        return builder.toString();
    }
    
    // Method to retrive the spotify playlist ID by the playlist link

    public static String retriveSpotifyPlaylistID(String s){

        if (s.length() == 22) {
            return s;
        }else if (s.length() > 22) {

            int lastIndex = s.lastIndexOf("/") + 1;
            int index = s.lastIndexOf("?");

            return s.substring(lastIndex, index);
        }else{
            return "";
        }

    }

}
