package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.formdev.flatlaf.json.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

public class PlaylistReader {

	// The ID of the playlist to be processed
	public static String link = "";
	public static String playlistId;

	public static void playlistID() {
		playlistId = retriveSpotifyPlaylistID(link);
	}

	public static List<TrackData> readPlaylist()
			throws ParseException, SpotifyWebApiException, IOException, InterruptedException, org.apache.hc.core5.http.ParseException {

		List<TrackData> trackList = new ArrayList<>(); // List to store track data

		if (!playlistId.isEmpty()) {
			// Start a Python process to retrieve the Spotify access token
			ProcessBuilder processBuilder = new ProcessBuilder("python", "main.py");
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			// Read the token from the Python script output
			String token = reader.readLine();
			System.out.println(token); // Debugging: print the token to verify it isn't null
			reader.close();

			int offset = 0; // Offset for pagination
			Map<String, Integer> trackCountMap = new HashMap<>(); // Map to count track occurrences

			while (true) {
				// Initialize Spotify API with the access token
				SpotifyApi api = new SpotifyApi.Builder().setAccessToken(token).build();

				// Build a request to retrieve playlist items with a limit and offset
				GetPlaylistsItemsRequest getPlaylistRequest = api.getPlaylistsItems(playlistId).limit(100)
						.offset(offset).build();

				offset += 100;

				try {
					// Execute the request to get playlist items
					Paging<PlaylistTrack> playlistTrackPaging = getPlaylistRequest.execute();
					PlaylistTrack[] items = playlistTrackPaging.getItems();

					if (items.length == 0) {
						break;
					}

					// Process each track in the response
					for (int i = 0; i < items.length; i++) {
						Track track = (Track) items[i].getTrack(); // Get track details
						String trackName = track.getName(); // Get track name
						ArtistSimplified[] artists = track.getArtists(); // Get track artists
						String artistNames = getArtistsNames(artists); // Format artist names
						AlbumSimplified album = track.getAlbum(); // Get album details
						String albumName = album.getName(); // Get album name
						long durationMs = track.getDurationMs(); // Get track duration in milliseconds
						double durationMinutes = durationMs / 60000.0; // Convert duration to minutes

						String trackKey = trackName + " - " + artistNames; // Unique key for track

						// Update the track occurrence count in the map
						if (trackCountMap.containsKey(trackKey)) {
							int count = trackCountMap.get(trackKey);
							trackCountMap.put(trackKey, count + 1);
						} else {
							trackCountMap.put(trackKey, 1);
						}

						// Add track data to the list
						trackList.add(new TrackData(trackName, artistNames, trackCountMap.get(trackKey), albumName,
								durationMinutes));
					}

				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SpotifyWebApiException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return trackList;
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
	public static String retriveSpotifyPlaylistID(String s) {
		if (s.length() == 22 && SpotifyPlaylistChecker.isSpotifyPlaylistId(s)) {
			return s;
		} else if (s.length() > 22 && SpotifyPlaylistChecker.isSpotifyPlaylist(s)) {
			int lastIndex = s.lastIndexOf("/") + 1;
			int index = s.lastIndexOf("?");
			return s.substring(lastIndex, index);
		} else {
			return "";
		}
	}
}
