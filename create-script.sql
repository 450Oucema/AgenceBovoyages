create table vmtaille
(
    maxtypes integer
);

alter table vmtaille
    owner to maximalsound;

create table vmtable
(
    nom    varchar(32)      not null
        constraint vmtable_pkey
            primary key,
    prix   double precision not null,
    nombre integer
);

alter table vmtable
    owner to maximalsound;

create table destinations
(
    iddestination integer not null
        constraint destinations_pkey
            primary key,
    destination   text    not null,
    description   text    not null
);

alter table destinations
    owner to maximalsound;

create table datesvoyages
(
    idsejour          integer                                                              not null
        constraint datesvoyages_pkey
            primary key,
    datedepart        timestamp default '2004-10-19 10:23:54'::timestamp without time zone not null,
    dateretour        timestamp default '2020-01-01 12:00:00'::timestamp without time zone not null,
    prixhtdestination integer   default 0                                                  not null,
    iddestination     integer                                                              not null
        constraint fk_des
            references destinations
);

alter table datesvoyages
    owner to maximalsound;

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
    owner to maximalsound;

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
    owner to maximalsound;

create table commandes
(
    id       varchar not null
        constraint commandes_pk
            primary key,
    "idUser" varchar
        constraint commandes_users_id_fk
            references users,
    payee    boolean
);

alter table commandes
    owner to maximalsound;

create table commander
(
    "idCommander" varchar not null
        constraint commander_pk
            primary key,
    "idCommande"  varchar not null
        constraint commander_commandes_id_fk
            references commandes,
    "idVoyage"    integer not null
        constraint commander_datesvoyages_idsejour_fk
            references datesvoyages,
    "nbPersonnes" integer
);

alter table commander
    owner to maximalsound;


