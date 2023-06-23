#!/bin/bash

function frontend_build() 
{
    echo "Building the frontend"

    cd ./frontend
    npm install
    npm run build

    echo "Done"
}

function frontend_copy()
{
    echo "Copy frontend to backend"

    rm -rf ./backend/resources/css
    rm -rf ./backend/resources/js
    rm -f ./backend/public/favicon.ico

    cp -R ./frontend/build/static/css ./backend/resources/css
    cp -R ./frontend/build/static/js ./backend/resources/js
    cp ./frontend/build/favicon.ico ./backend/public/favicon.ico
    cp ./frontend/build/manifest.json ./backend/public/manifest.json

    echo "Done"
}

function frontend_integrate()
{
    echo "Integrate frontend and backend"

    JS_FILE=$(ls -1 ./backend/resources/js/ | grep "main" | grep ".js" | grep -v ".js.")
    CSS_FILE=$(ls -1 ./backend/resources/css | grep "main" | grep ".css" | grep -v ".css.")

    TEMPLATE_FILE=$(pwd)/backend/resources/views/welcome.blade.php
    python3 $(pwd)/.github/workflows/deploy_dev/replace.py $TEMPLATE_FILE "<--js-resource-->" "{{ asset('js/${JS_FILE}') }}"
    python3 $(pwd)/.github/workflows/deploy_dev/replace.py $TEMPLATE_FILE "<--css-resource-->" "{{ asset('css/${CSS_FILE}') }}"

    echo "Done"
}

# frontend_build
# frontend_copy
frontend_integrate