import yt_dlp
import argparse


def download_audio(track_name, artist_name, file_format='mp3'):

    # Search for the song on YouTube by formatting the query with track and artist

    query = f"{track_name} {artist_name} audio"

    # Download options configured for yt-dlp

    ydl_opts = {
        'format': 'bestaudio/best',
        'noplaylist': True,
        'outtmpl': f'{track_name}.{file_format}',
        'postprocessors': [{
            'key': 'FFmpegExtractAudio',
            'preferredcodec': file_format,
            'preferredquality': '192',
        }]
    }

    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        try:

            # Search on YouTube and download the first result

            ydl.download([f"ytsearch1:{query}"])
            print(f"Downloaded: {track_name}.{file_format}")

        except Exception as e:

            print(f"Error downloading {track_name}: {e}")


if __name__ == "__main__":

    # Set up the argument parser

    parser = argparse.ArgumentParser(description="Download audio from YouTube")

    # Add arguments for track name, artist name, and file format

    parser.add_argument("track_name", type=str, help="The name of the track")
    parser.add_argument("artist_name", type=str, help="The name of the artist")
    parser.add_argument("--format", type=str, default="mp3", help="The audio format (default: mp3)")

    # Parse the arguments

    args = parser.parse_args()

    # Call the function with the parsed arguments
    
    download_audio(args.track_name, args.artist_name, args.format)
