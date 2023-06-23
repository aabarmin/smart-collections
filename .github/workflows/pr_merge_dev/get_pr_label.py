import requests
import sys

if len(sys.argv) != 5:
    print("Usage: python3 get_latest_release.py <Token> <Owner> <Repo> <PR ID>")
    exit(1)

token = sys.argv[1]
owner = sys.argv[2]
repository = sys.argv[3]
pr_id = sys.argv[4]

url = f"https://api.github.com/repos/{owner}/{repository}/pulls/{pr_id}"
response = requests.get(
    url=url, 
    headers={
        "Accept": "application/vnd.github+json", 
        "Authorization": f"Bearer {token}",
        "X-GitHub-Api-Version": "2022-11-28"
    }
)

if response.status_code != 200:
    print(f"Can't find PR with id {pr_id}")
    exit(1)

pr_info = response.json()
labels = pr_info['labels']
if len(labels) == 0:
    print(f"No labels associated with PR {pr_id}")
    exit(1)

allowed_labels = {"minor", "major", "patch"}
found_labels = set()

for label in labels: 
    if label['name'] in allowed_labels:
        found_labels.add(label['name'])

if len(found_labels) == 0:
    print(f"No labels like minor, major or patch associated to PR {pr_id}")
    exit(1)

if len(found_labels) > 1:
    print(f"More than one of mandatory labels are associated to pr {pr_id}")
    exit(1)

print(found_labels.pop())
