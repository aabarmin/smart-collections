import requests
import sys

if len(sys.argv) != 4:
    print("Usage: python3 get_latest_release.py <Token> <Owner> <Repo>")
    exit(1)

token = sys.argv[1]
owner = sys.argv[2]
repository = sys.argv[3]

url = f"https://api.github.com/repos/{owner}/{repository}/releases/latest"
response = requests.get(
    url=url, 
    headers={
        "Accept": "application/vnd.github+json", 
        "Authorization": f"Bearer {token}",
        "X-GitHub-Api-Version": "2022-11-28"
    }
)

if response.status_code != 200:
    print("0.0.0")
    exit(1)

release = response.json()
tag = release['tag_name']

if tag[0] == "v":
    print(tag[1:])
    exit(0)

print(tag)
