try:

    import yt_dlp
    import argparse
    import os
    import re

except ModuleNotFoundError:

    from subprocess import call

    modules = ["yt_dlp"]
    call("pip install " + ' '.join(modules), shell=True)

finally:

    def sanitize_filename(name):

        """
        Replace or remove characters that are not allowed in filenames.
        This helps to prevent issues with paths and file creation.
        """

        return re.sub(r'[<>:"/\\|?*]', '_', name).strip()

    def download_audio(track_name, artist_name, file_format='mp3', output_folder='.', track_duration=None):

        # Sanitize track and artist names to avoid issues with file paths

        safe_track_name = sanitize_filename(track_name)
        safe_artist_name = sanitize_filename(artist_name)

        # Search for the song on YouTube by formatting the query with track and artist

        query = f"{safe_track_name} {safe_artist_name}"

        # Ensure the output path is correctly set to the specified folder
        output_path = os.path.join(output_folder, f"{safe_track_name}.{file_format}")
        output_path = os.path.normpath(output_path)

        # Download options configured for yt-dlp

        ydl_opts = {
            'format': 'bestaudio/best',
            'noplaylist': True,
            'outtmpl': output_path,
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
                print(f"Downloaded: {track_name}.{file_format}.{output_path}")

            except Exception as e:

                print(f"Error downloading {track_name}: {e}")


    if __name__ == "__main__":

        # Set up the argument parser

        parser = argparse.ArgumentParser(description="Download audio from YouTube")

        # Add arguments for track name, artist name, and file format

        parser.add_argument("track_name", type=str, help="The name of the track")
        parser.add_argument("artist_name", type=str, help="The name of the artist")
        parser.add_argument("--format", type=str, default="mp3", help="The audio format (default: mp3)")
        parser.add_argument("--output", type=str, default=".", help="The output folder (default: current directory)")

    #    parser.add_argument("--duration", type=float, help="The track duration in minutes")

        # Parse the arguments

        args = parser.parse_args()

        # Call the function with the parsed arguments

        download_audio(args.track_name, args.artist_name, args.format, args.output)
