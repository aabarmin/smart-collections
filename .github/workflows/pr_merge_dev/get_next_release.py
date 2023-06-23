import sys

if len(sys.argv) != 3:
    print("Usage: python3 get_next_release.py <Current release> <Label>")
    exit(1)

current = sys.argv[1]
label = sys.argv[2]

parts = current.split(".")
if label == "major":
    parts[0] = str(int(parts[0]) + 1)
    parts[1] = "0"
    parts[2] = "0"

if label == "minor":
    parts[1] = str(int(parts[1]) + 1)
    parts[2] = "0"

if label == "patch":
    parts[2] = str(int(parts[2]) + 1)

print("v" + ".".join(parts))