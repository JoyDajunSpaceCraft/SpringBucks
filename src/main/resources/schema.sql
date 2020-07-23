drop table t_user if exists;
drop table t_reward if exists;
drop table tcoffee if exists;
-- 注意 Mysql数据库 如果是要定义 expiryDate 要转换成 expiry_date 否则数据库不识别

create table t_coffee (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    name varchar(255),
    price bigint,
    primary key (id)
);

create table t_user (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    password varchar(255),
    username varchar(255),
    phone bigint not null,
    state integer not null,
    primary key (id)
);

create table t_reward (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    discount integer,
    expiry_Date timestamp,
    reward_Name varchar(255),
    reward_State integer not null,
    user_id integer,
    primary key(id)
);
