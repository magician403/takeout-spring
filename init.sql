create database if not exists take_out;
use take_out;
create table if not exists employee
(
    user_id     bigint unsigned primary key comment '用户id',
    username    varchar(128) unique not null comment '用户名',
    name        varchar(32)         not null comment '员工姓名',
    phone       varchar(16)         not null comment '手机号',
    sex         tinyint unsigned    not null comment '性别,0为男,1为女',
    id_number   varchar(18)         not null comment '身份证号',
    create_time datetime            not null comment '创建时间',
    update_time datetime            not null comment '修改时间',
    create_user bigint unsigned     not null comment '创建人id',
    update_user bigint unsigned     not null comment '修改人id',
    `deleted`   tinyint unsigned    not null default 0 comment '是否删除'
)
    engine = Innodb
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

create table if not exists user
(
    user_id         bigint unsigned auto_increment primary key comment '用户id',
    username        varchar(128) unique not null comment '用户名',
    hashed_password varchar(128)        not null comment 'hash后的密码',
    `deleted`       tinyint unsigned    not null default 0 comment '是否删除'
)
    engine = Innodb
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

create table if not exists user_role
(
    user_id bigint unsigned comment '用户id',
    role    varchar(32) not null comment '角色',
    unique key uk__user_id__role (user_id, role)
)
    engine = Innodb
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;
