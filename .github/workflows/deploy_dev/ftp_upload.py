import sys
import os
import json
from ftplib import FTP
from pathlib import Path

if len(sys.argv) != 4:
    print("Usage: python3 ftp_upload.py <path to credentials.json> <local file> <remote folder>")
    exit(1)

credentials_file_path = sys.argv[1]
local_file = sys.argv[2]
remote_folder = sys.argv[3]

if not os.path.exists(credentials_file_path):
    print("Credentials file does not exist")
    exit(1)

with open(credentials_file_path, 'r') as file: 
    credentials = json.load(file)

if not os.path.exists(local_file):
    print("Local file does not exist")
    exit(1)

with FTP(
    host=credentials['ftp_host'], 
    user=credentials['ftp_username'],
    passwd=credentials['ftp_password']
) as ftp:
    ftp.cwd(remote_folder)
    local_path = Path(local_file)
    with open(local_file, 'rb') as file:
        file_name = os.path.basename(local_path)
        print(f"Uploading {file_name} to {remote_folder}")
        ftp.storbinary(f"STOR {file_name}", file)