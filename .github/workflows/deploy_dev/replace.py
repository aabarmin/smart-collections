import sys

if len(sys.argv) != 4:
    print("Usage: python3 replace.py <File> <source> <target>")
    exit(1)

path = sys.argv[1]
source = sys.argv[2]
target = sys.argv[3]

with open(path, 'r') as file: 
    filedata = file.read()

filedata = filedata.replace(source, target)

with open(path, 'w') as file:
    file.write(filedata)