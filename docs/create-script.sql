create table destinations
(
    iddestination integer not null
        constraint destinations_pkey
            primary key,
    destination   text    not null,
    description   text    not null
);

alter table destinations
    owner to bovoyages;

create table sejour
(
    idsejour          integer                                                              not null
        constraint sejour_pkey
            primary key,
    datedepart        timestamp default '2004-10-19 10:23:54'::timestamp without time zone not null,
    dateretour        timestamp default '2020-01-01 12:00:00'::timestamp without time zone not null,
    prixhtdestination integer   default 0                                                  not null,
    iddestination     integer                                                              not null
        constraint fk_des
            references destinations
);

alter table sejour
    owner to bovoyages;

create table imagesdestinations
(
    image         varchar(50) not null
        constraint imagesdestinations_pk
            primary key,
    iddestination integer     not null
        constraint fk_desttt
            references destinations
);

alter table imagesdestinations
    owner to bovoyages;

create table users
(
    id       varchar not null
        constraint users_pk
            primary key,
    email    varchar,
    password varchar,
    nom      varchar,
    prenom   varchar,
    adresse  varchar
);

alter table users
    owner to bovoyages;

create table commandes
(
    id     varchar not null
        constraint commandes_pk
            primary key,
    "user" varchar
        constraint commandes_users_id_fk
            references users,
    payee  boolean
);

alter table commandes
    owner to bovoyages;

create table commande_item
(
    id          varchar not null
        constraint commander_pk
            primary key,
    commande    varchar not null
        constraint commander_commandes_id_fk
            references commandes,
    sejour      integer not null
        constraint commander_sejour_idsejour_fk
            references sejour,
    nbpersonnes integer
);

alter table commande_item
    owner to bovoyages;

