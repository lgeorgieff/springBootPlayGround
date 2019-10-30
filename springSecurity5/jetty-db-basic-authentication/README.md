# mysql
docker image: https://hub.docker.com/_/mysql?tab=description

1. `docker run --tty --interactive --publish 3306:3306 --publish 33060:33060 -e MYSQL_ROOT_PASSWORD=123456 mysql:latest`
1. `docker run --tty --interactive mysql:latest /bin/bash`
1. `mysql -h 172.17.0.2 --user root -p`
1. `CREATE DATABASE spring_security;`
1. `USE spring_security;`
1. `CREATE TABLE users (username varchar(75) not null primary key, password varchar(150) not null, enabled boolean not null);`
1. `CREATE TABLE authorities (username varchar(75) not null, authority varchar(50) not null, constraint fk_authorities_users foreign key(username) references users(username));`
1. `INSERT INTO users(username, password, enabled) values('admin', '$2a$04$lcVPCpEk5DOCCAxOMleFcOJvIiYURH01P9rx1Y/pl.wJpkNTfWO6u', true);`
1. `INSERT INTO authorities(username, authority) values('admin', 'ROLE_ADMIN');`
1. `INSERT INTO users(username, password, enabled) values('user', '$2a$04$nbz5hF5uzq3qsjzY8ZLpnueDAvwj4x0U9SVtLPDROk4vpmuHdvG3a', true);`
1. `INSERT INTO authorities(username, authority) values('user', 'ROLE_USER');`
