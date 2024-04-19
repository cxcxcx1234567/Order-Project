create table orders
(
    id          bigint auto_increment
        primary key,
    status      varchar(25) default 'UNASSIGNED' not null,
    origin      varchar(255) charset utf8mb4     null,
    destination varchar(255) charset utf8mb4     null,
    distance    bigint                           null
)
    collate = utf8mb3_bin;