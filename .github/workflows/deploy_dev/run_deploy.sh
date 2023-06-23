#!/bin/bash

work_dir=$(pwd)

function prepare_local()
{
    cd $work_dir

    rm -rf ./bundle
    mkdir ./bundle
}

function prepare_credentials() 
{
    cd $work_dir/bundle

    rm -f ./ftp_credentials.json
    touch ./ftp_credentials.json

    echo "{" >> ./ftp_credentials.json
    echo "\"ftp_host\": \"${FTP_HOST}\", " >> ./ftp_credentials.json
    echo "\"ftp_username\": \"${FTP_USER}\", " >> ./ftp_credentials.json
    echo "\"ftp_password\": \"${FTP_PASSWORD}\"" >> ./ftp_credentials.json
    echo "}" >> ./ftp_credentials.json
}

function archive_build()
{
    cd $work_dir/bundle
    rm -f ./archive.zip

    touch ./archive_files.txt
    echo "app" >> ./archive_files.txt
    echo "bootstrap" >> ./archive_files.txt
    echo "config" >> ./archive_files.txt
    echo "database" >> ./archive_files.txt
    echo "public" >> ./archive_files.txt
    echo "resources" >> ./archive_files.txt
    echo "routes" >> ./archive_files.txt
    echo "vendor" >> ./archive_files.txt
    echo ".env" >> ./archive_files.txt

    cd ../backend
    rm -r archive.zip
    zip archive.zip -r -1 -@ < ../bundle/archive_files.txt

    cd ../bundle
    mv ../backend/archive.zip ./archive.zip
}

function archive_upload()
{
    cd $work_dir

    python3 ./.github/workflows/deploy_dev/ftp_upload.py \
        ./bundle/ftp_credentials.json \
        ./.github/workflows/deploy_dev/cleanup.php \
        "/"    

    curl -v "${REMOTE_BASE_URL}/cleanup.php"

    python3 ./.github/workflows/deploy_dev/ftp_upload.py \
        ./bundle/ftp_credentials.json \
        ./bundle/archive.zip \
        "/"
}

function archive_unpack()
{
    cd $work_dir

    python3 ./.github/workflows/deploy_dev/ftp_upload.py \
        ./bundle/ftp_credentials.json \
        ./.github/workflows/deploy_dev/unpack.php \
        "/"   

    curl -v "${REMOTE_BASE_URL}/unpack.php"
}

function cleanup_remote()
{
    cd $work_dir

    python3 ./.github/workflows/deploy_dev/ftp_remove.py \
        ./bundle/ftp_credentials.json \
        "/" \
        "archive.zip"

    python3 ./.github/workflows/deploy_dev/ftp_remove.py \
        ./bundle/ftp_credentials.json \
        "/" \
        "unpack.php"        
}

function cleanup_local()
{
    cd $work_dir
    rm -rf ./bundle
}

prepare_local
prepare_credentials

archive_build
archive_upload
archive_unpack

cleanup_remote
cleanup_local