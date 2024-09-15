package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyPlaylistChecker {

	private static final String URL_PATTERN = "https:\\/\\/open\\.spotify\\.com\\/playlist\\/[a-zA-Z0-9]+(\\?.*)?";
    private static final String URI_PATTERN = "spotify:playlist:[a-zA-Z0-9]+";
    private static final String PLAYLIST_ID_PATTERN = "^[a-zA-Z0-9]{22}$";
    
    // Checks if the provided ID matches the pattern for a valid Spotify playlist ID
    
    public static boolean isSpotifyPlaylistId(String id) {
        Pattern pattern = Pattern.compile(PLAYLIST_ID_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }
    
    // Checks if the provided link matches the pattern for a valid Spotify playlist URL or URI
    
    public static boolean isSpotifyPlaylist(String link) {
        Pattern urlPattern = Pattern.compile(URL_PATTERN);
        Pattern uriPattern = Pattern.compile(URI_PATTERN);

        Matcher urlMatcher = urlPattern.matcher(link);
        Matcher uriMatcher = uriPattern.matcher(link);

        return urlMatcher.matches() || uriMatcher.matches();
    }
    
}