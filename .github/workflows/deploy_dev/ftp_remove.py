import sys
import os
import json
from ftplib import FTP
from pathlib import Path

if len(sys.argv) != 4:
    print("Usage: python3 ftp_remove.py <path to credentials.json> <remote folder> <remote file>")
    exit(1)

credentials_file_path = sys.argv[1]
remote_folder = sys.argv[2]
remote_file = sys.argv[3]

if not os.path.exists(credentials_file_path):
    print("Credentials file does not exist")
    exit(1)

with open(credentials_file_path, 'r') as file: 
    credentials = json.load(file)

with FTP(
    host=credentials['ftp_host'], 
    user=credentials['ftp_username'],
    passwd=credentials['ftp_password']
) as ftp:
    print(f"Removing file {remote_file} in {remote_folder}")
    ftp.cwd(remote_folder)
    ftp.delete(remote_file)