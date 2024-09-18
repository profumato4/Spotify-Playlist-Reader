import yt_dlp

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
    download_audio("King Kunta", "Kendrick Lamar", "mp3")
