package main;

public class TrackData {

	private String trackName;
	private String artistNames;
	private int occurrence;

	public TrackData(String trackName, String artistNames, int occurrence) {
		this.trackName = trackName;
		this.artistNames = artistNames;
		this.occurrence = occurrence;
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
}
