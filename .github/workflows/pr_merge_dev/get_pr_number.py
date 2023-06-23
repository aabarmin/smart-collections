import sys
import re

if len(sys.argv) != 2:
    print("Usage: python3 get_pr_number.py \"Commit message with (#123)\"")
    print("Example: python3 get_pr_number.py \"Create script (#12)\"")
    exit(1)

subject = sys.argv[1]
matcher = re.search('(?<=#)\\d+', subject)
if matcher is None:
    print("Subject does not include the PR ID")
    exit(1)

print(matcher.group(0))