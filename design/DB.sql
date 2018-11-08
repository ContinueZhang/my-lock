-- auto-generated definition
create table t_icbc
(
  id      int auto_increment
    primary key,
  balance decimal(16, 10) not null,
  user_id int             not null
);

-- auto-generated definition
create table t_icbc_detail
(
  id             int auto_increment
    primary key,
  amount         decimal(16, 10) not null,
  icbc_id        int             not null,
  time           datetime        not null,
  target_icbc_id int             not null
);

-- auto-generated definition
create table t_user
(
  id    int auto_increment
    primary key,
  name  varchar(200) not null,
  age   int          not null,
  hobby varchar(200) null
);

