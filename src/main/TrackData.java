package main;

public class TrackData {
    private String trackName;
    private String artistNames;
    private int occurrence;
    private String albumName;
    private double durationMinutes; 

    public TrackData(String trackName, String artistNames, int occurrence, String albumName, double durationMinutes) {
        this.trackName = trackName;
        this.artistNames = artistNames;
        this.occurrence = occurrence;
        this.albumName = albumName;
        this.durationMinutes = durationMinutes;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtistNames() {
        return artistNames;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public String getAlbumName() {
        return albumName;
    }

    public double getDurationMinutes() {
        return durationMinutes;
    }
}
