#!/bin/bash
COMMIT_MSG=$(git show -s --format=%s)

PR_ID=$(python3 ./get_pr_number.py "${COMMIT_MSG}")
echo "Latest PR is ${PR_ID}"

LATEST_RELEASE=$(python3 ./get_latest_release.py ${GITHUB_TOKEN} ${REPO_OWNER} ${REPO_NAME})
echo "Latest release is ${LATEST_RELEASE}"

LABEL=$(python3 ./get_pr_label.py ${GITHUB_TOKEN} ${REPO_OWNER} ${REPO_NAME} ${PR_ID})
echo "PR label is ${LABEL}"

NEXT_VERSION=$(python3 ./get_next_release.py ${LATEST_RELEASE} ${LABEL})
echo "Next version is ${NEXT_VERSION}"

NEXT_RELEASE="release-$NEXT_VERSION"
gh release create $NEXT_VERSION \
    --generate-notes \
    --latest \
    --target ${REPO_BRANCH} \
    --title ${NEXT_RELEASE}
