create table organization
(
    id                  bigint       not null,
    created_at          datetime(6),
    updated_at          datetime(6),
    email_endpoint      varchar(255),
    name                varchar(255) not null unique,
    organization_status enum ('APPROVED','DENIED','REQUESTED'),
    organization_type   enum ('COMPANY','ETC','HIGH_SCHOOL','UNIVERSITY'),
    deleted             bit          not null comment 'Soft-delete indicator',
    primary key (id)
);
create table organization_seq
(
    next_val bigint
);
insert into organization_seq
values (1);
create table member
(
    id              bigint       not null,
    created_at      datetime(6),
    updated_at      datetime(6),
    email           varchar(255),
    github_id       varchar(255) not null unique,
    github_token    varchar(255),
    name            varchar(255),
    profile_image   varchar(255),
    refresh_token   varchar(255),
    wallet_address  varchar(255),
    deleted         bit          not null comment 'Soft-delete indicator',
    organization_id bigint references organization (id),
    primary key (id)
);
create table member__auth_step
(
    member_id bigint not null references member (id),
    _auth_step enum ('EMAIL','GITHUB','KLIP')
);
create table member__roles
(
    member_id bigint not null references member (id),
    _roles enum ('ROLE_ADMIN','ROLE_USER')
);
create table member_seq
(
    next_val bigint
);
insert into member_seq
values (1);
create table blockchain
(
    id                bigint       not null,
    created_at        datetime(6),
    updated_at        datetime(6),
    address           varchar(255) not null unique,
    amount            decimal(38, 0),
    contribution_type enum ( 'COMMIT', 'ISSUE', 'PULL_REQUEST', 'CODE_REVIEW'),
    member_id         bigint       not null,
    transaction_hash  varchar(255),
    deleted           bit          not null comment 'Soft-delete indicator',
    primary key (id)
);
create table blockchain_seq
(
    next_val bigint
);
insert into blockchain_seq
values (1);
create table contribution
(
    id                bigint  not null,
    created_at        datetime(6),
    updated_at        datetime(6),
    amount            integer not null,
    contribution_type enum ( 'COMMIT', 'ISSUE', 'PULL_REQUEST', 'CODE_REVIEW'),
    member_id         bigint  not null,
    year              integer not null,
    deleted           bit     not null comment 'Soft-delete indicator',
    primary key (id)
);
create table contribution_seq
(
    next_val bigint
);
insert into contribution_seq
values (1);
create table email
(
    id         bigint  not null,
    created_at datetime(6),
    updated_at datetime(6),
    code       integer not null,
    member_id  bigint  not null,
    deleted    bit     not null comment 'Soft-delete indicator',
    primary key (id)
);
create table email_seq
(
    next_val bigint
);
insert into email_seq
values (1);
create table git_org_member_seq
(
    next_val bigint
);
insert into git_org_member_seq
values (1);
create table git_org_seq
(
    next_val bigint
);
insert into git_org_seq
values (1);
create table git_repo_member_seq
(
    next_val bigint
);
insert into git_repo_member_seq
values (1);
create table git_repo_seq
(
    next_val bigint
);
insert into git_repo_seq
values (1);
create table git_org
(
    id            bigint       not null,
    created_at    datetime(6),
    updated_at    datetime(6),
    name          varchar(255) not null unique,
    profile_image varchar(255),
    deleted       bit          not null comment 'Soft-delete indicator',
    primary key (id)
);
create table git_org_member
(
    id         bigint not null,
    created_at datetime(6),
    updated_at datetime(6),
    member_id  bigint not null,
    deleted    bit    not null comment 'Soft-delete indicator',
    git_org_id bigint references git_org (id),
    primary key (id)
);
create table git_repo
(
    id         bigint not null,
    created_at datetime(6),
    updated_at datetime(6),
    name       varchar(255),
    deleted    bit    not null comment 'Soft-delete indicator',
    primary key (id)
);
create table git_repo_spark_line
(
    git_repo_id bigint not null references git_repo (id),
    spark_line  integer
);
create table git_repo_member
(
    id          bigint not null,
    created_at  datetime(6),
    updated_at  datetime(6),
    additions   integer,
    commits     integer,
    deletions   integer,
    deleted     bit    not null comment 'Soft-delete indicator',
    git_repo_id bigint references git_repo (id),
    member_id   bigint references member (id),
    primary key (id)
);
