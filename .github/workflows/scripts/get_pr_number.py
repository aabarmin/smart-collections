import sys
import re

subject = sys.stdin.read()
matcher = re.search('(?<=#)\\d+', subject)
if matcher is None:
    print("Subject does not include the PR ID")
    exit(1)

print(matcher.group(0))