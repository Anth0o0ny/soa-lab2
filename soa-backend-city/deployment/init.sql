-- Создание перечисления Climate
create type climate as enum (
    'MONSOON',
    'TROPICAL_SAVANNA',
    'HUMIDSUBTROPICAL',
    'HUMIDCONTINENTAL',
    'MEDITERRANIAN'
);

-- Создание перечисления Government
create type government as enum (
    'MONARCHY',
    'TELLUROCRACY',
    'TECHNOCRACY'
);

-- Создание перечисления StandardOfLiving
create type standard_of_living as enum (
    'VERY_HIGH',
    'HIGH',
    'LOW',
    'NIGHTMARE'
);

-- Создание таблицы coordinates
create table coordinates
(
    id bigserial primary key,
    x  float not null,
    y  float not null
);

-- Создание таблицы human
create table human
(
    id bigserial primary key,
    age bigint not null check (age > 0)
);

-- Создание таблицы city
create table city
(
    id bigserial primary key,
    name varchar(255) not null,
    coordinates_id bigint not null references coordinates (id),
    creation_date timestamp not null default current_timestamp,
    area integer not null check (area > 0),
    population integer not null check (population > 0),
    meters_above_sea_level bigint,
    climate climate not null,
    government government,
    standard_of_living standard_of_living not null,
    governor_id bigint references human (id)
);