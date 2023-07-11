#!/bin/bash

home_dir=$(pwd)
export PATH="/usr/local/share/nvm/versions/node/v20.4.0/bin/:${PATH}"

function xdebug_configure()
{
    echo "Configuring XDebug"

    echo "xdebug.discover_client_host=1" >> /usr/local/etc/php/conf.d/xdebug.ini

    echo "Done"
}

function install_dependencies()
{
    echo "Installing dependencies"

    composer install
    npm install

    echo "Done"
}

function create_env()
{
    echo "Creating .env file"

    rm -f .env
    touch .env

    echo "APP_ENV=local" >> .env
    echo "APP_DEBUG=true" >> .env
    echo "APP_KEY=" >> .env

    echo "" >> .env

    echo "DB_CONNECTION=sqlite" >> .env
    echo "DB_DATABASE=/var/www/html/database.sqlite" >> .env

    echo "" >> .env

    echo "GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID:not-set}" >> .env
    echo "GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET:not-set}" >> .env
    echo "GOOGLE_REDIRECT_URI=http://localhost:8080/login/google/callback" >> .env

    php artisan key:generate

    echo "Done"
}

function create_database()
{
    echo "Creating SQLite database"

    rm -f database.sqlite
    touch database.sqlite

    echo "Done"
}

function database_migrate()
{
    echo "Running database migrations"

    php artisan migrate

    echo "Done"
}

function apache_update()
{
    echo "Updating Apache config"

    sed -i '5d' /etc/apache2/ports.conf
    sed -i 's/:80/:8080/g' /etc/apache2/sites-available/000-default.conf
    sed -i 's/html/html\/public/g' /etc/apache2/sites-available/000-default.conf
    ln -s /etc/apache2/mods-available/rewrite.load /etc/apache2/mods-enabled/rewrite.load

    echo "Done"
}

function apache_start()
{
    echo "Starting Apache"

    apache2ctl start

    echo "Done"
}

function build_frontend()
{
    echo "Building the frontend"

    npm run build

    echo "Done"
}

function git_exception()
{
    echo "Configuring git exception"

    sudo -u vscode git config --global --add safe.directory /workspaces/vinyl-collection

    echo "Done"
}

# xdebug_configure
install_dependencies

create_env
create_database
database_migrate

apache_update
apache_start

build_frontend

git_exception