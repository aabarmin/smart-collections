#!/bin/bash

home_dir=$(pwd)

function frontend_build() 
{
    echo "Building the frontend"

    cd $home_dir

    cd ./frontend
    npm install
    npm run build

    echo "Done"
}

function frontend_copy()
{
    echo "Copy frontend to backend"

    cd $home_dir

    rm -rf ./backend/public/css
    rm -rf ./backend/public/js
    rm -f ./backend/public/favicon.ico

    cp -R ./frontend/build/static/css ./backend/public/css
    cp -R ./frontend/build/static/js ./backend/public/js
    cp ./frontend/build/favicon.ico ./backend/public/favicon.ico
    cp ./frontend/build/manifest.json ./backend/public/manifest.json
    cp ./frontend/build/logo192.png ./backend/public/logo192.png
    cp ./frontend/build/logo512.png ./backend/public/logo512.png

    echo "Done"
}

function frontend_integrate()
{
    echo "Integrate frontend and backend"

    cd $home_dir

    JS_FILE=$(ls -1 ./backend/public/js | grep "main" | grep ".js" | grep -v ".js.")
    CSS_FILE=$(ls -1 ./backend/public/css | grep "main" | grep ".css" | grep -v ".css.")

    TEMPLATE_FILE=$(pwd)/backend/resources/views/welcome.blade.php
    python3 $(pwd)/.github/workflows/deploy_dev/replace.py $TEMPLATE_FILE "<--js-resource-->" "{{ asset('js/${JS_FILE}') }}"
    python3 $(pwd)/.github/workflows/deploy_dev/replace.py $TEMPLATE_FILE "<--css-resource-->" "{{ asset('css/${CSS_FILE}') }}"

    echo "Done"
}

function backend_build() 
{
    echo "Building the backend"

    cd $home_dir
    cd ./backend
    composer install

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

frontend_build
frontend_copy
frontend_integrate

backend_cleanup