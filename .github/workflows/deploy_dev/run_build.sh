#!/bin/bash

home_dir=$(pwd)

function backend_build() 
{
    echo "Building the backend"

    cd $home_dir
    cd ./backend
    composer install

    npm install
    npm run build

    echo "Done"
}

function backend_prepare_env()
{
    echo "Preparing .env for backend"

    cd $home_dir
    cd ./backend

    rm -f .env
    touch .env

    echo "APP_NAME=Vinyl_Collection" >> .env
    echo "APP_ENV=production" >> .env
    echo "APP_KEY=${APP_KEY}" >> .env
    echo "APP_DEBUG=false" >> .env
    echo "APP_URL=${REMOTE_BASE_URL}" >> .env

    echo "DB_CONNECTION=${DB_CONNECTION:mysql}" >> .env
    echo "DB_HOST=${DB_HOST}" >> .env
    echo "DB_PORT=${DB_PORT}" >> .env
    echo "DB_DATABASE=${DB_DATABASE}" >> .env
    echo "DB_USERNAME=${DB_USERNAME}" >> .env
    echo "DB_PASSWORD=${DB_PASSWORD}" >> .env

    echo "LOG_CHANNEL=stack" >> .env
    echo "LOG_DEPRECATIONS_CHANNEL=null" >> .env
    echo "LOG_LEVEL=debug" >> .env

    echo "GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID}" >> .env
    echo "GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET}" >> .env
    echo "GOOGLE_REDIRECT_URI=${REMOTE_BASE_URL}" >> .env

    echo "Done"
}

function backend_prepare_secret()
{
    echo "Preparing secret for DB migration"

    cd $home_dir
    cd ./backend

    rm -f ./storage/app/secret_file.txt
    touch ./storage/app/secret_file.txt

    echo "${RANDOM}.${RANDOM}.${RANDOM}" >> ./storage/app/secret_file.txt

    echo "Done"
}

function backend_cleanup()
{
    echo "Removing unnecessary files"

    cd $home_dir
    cd ./backend

    rm -rf .devcontainer
    rm -rf tests
    rm -f .env.example
    rm -f .env.testing
    rm -f .editorconfig
    rm -f .gitattributes
    rm -f .gitignore
    rm -f composer.json
    rm -f composer.lock
    rm -f docker-compose.yml
    rm -f package.json
    rm -f phpunit.xml
    rm -f vite.config.js
    rm -f artisan
    rm -f README.md

    echo "Done"
}

backend_build
backend_prepare_env
backend_prepare_secret

backend_cleanup