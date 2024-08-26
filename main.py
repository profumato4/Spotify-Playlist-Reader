import requests

# Spotify API token endpoint and credentials

url = "https://accounts.spotify.com/api/token"
client_id = "aa9a2adff30e4101a80c81d4e40f26ba"      # Replace with your client ID
client_secret = "78c6f8c81e2e4abeac108bed620a7f37"  # Replace with your client secret

# Payload for the token request

payload = {
    "grant_type": "client_credentials",
    "client_id": client_id,
    "client_secret": client_secret
}

# Send a POST request to get the access token

response = requests.post(url, data=payload)

# Check if the request was successful

if response.status_code == 200:
    data = response.json()
    access_token = data["access_token"]
    # Print the access token for use in the Java application
    print(access_token)
else:
    print("Response status : ", response.status_code)