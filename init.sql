create database take_out;
use take_out;
create table category
(
    id          bigint unsigned auto_increment primary key,
    type        tinyint unsigned   not null comment '类型1菜品分类2套餐分类',
    name        varchar(64) unique not null comment '分类名称',
    sort        int unsigned       not null comment '顺序',
    status      tinyint unsigned   not null comment '分类状态0禁用1启用',
    create_time datetime           not null,
    update_time datetime           not null,
    create_user bigint unsigned    not null,
    update_user bigint unsigned    not null
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table dish
(
    id          bigint unsigned auto_increment primary key,
    name        varchar(64) unique           not null comment '菜品名称',
    category_id bigint unsigned              not null comment '菜品分类id',
    price       decimal(10, 2)               not null comment '菜品价格',
    image       varchar(256)                 not null comment '图片',
    description varchar(256)                 not null comment '描述信息',
    status      tinyint unsigned default '1' not null comment '0停售1起售',
    create_time datetime                     not null,
    update_time datetime                     not null,
    create_user bigint unsigned              not null,
    update_user bigint unsigned              not null,
    index idx_categoryid (category_id)
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table dish_flavor
(
    id      bigint unsigned auto_increment primary key,
    dish_id bigint unsigned not null comment '菜品',
    name    varchar(32)     null comment '口味名称',
    value   varchar(255)    null comment '口味数据list'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;
create table user
(
    id              bigint unsigned auto_increment primary key,
    username        varchar(128) unique not null comment '用户名',
    hashed_password varchar(128)        not null comment 'hash后的密码',
    status          tinyint unsigned    not null default 1 comment '0正常1异常',
    deleted         tinyint unsigned             default '0' not null comment '是否删除'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;
create table employee
(
    id          bigint unsigned auto_increment primary key,
    user_id     bigint unsigned unique       not null comment '用户id',
    name        varchar(32)                  not null comment '员工姓名',
    phone       varchar(16)                  not null comment '手机号',
    sex         tinyint unsigned             not null comment '性别,0为男,1为女',
    id_number   varchar(18)                  not null comment '身份证号',
    create_time datetime                     not null comment '创建时间',
    update_time datetime                     not null comment '修改时间',
    create_user bigint unsigned              not null comment '创建人id',
    update_user bigint unsigned              not null comment '修改人id',
    deleted     tinyint unsigned default '0' not null comment '是否删除'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table order_detail
(
    id             bigint unsigned auto_increment primary key,
    name           varchar(32)     not null comment '名字',
    image          varchar(255)    not null comment '图片',
    order_id       bigint unsigned not null comment '订单id',
    dish_id        bigint unsigned not null comment '菜品id',
    set_meal_id    bigint unsigned not null comment '套餐id',
    dish_flavor_id bigint unsigned not null comment '口味',
    number         int unsigned    not null default '1' comment '数量',
    amount         decimal(10, 2)  not null comment '金额'

)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table `order`
(
    id                      bigint unsigned auto_increment primary key,
    number                  bigint unsigned unique     not null comment '订单号',
    status                  tinyint unsigned default 1 not null comment '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
    user_id                 bigint unsigned            not null comment '下单用户',
    address_book_id         bigint unsigned            not null comment '地址id',
    order_time              datetime                   not null comment '下单时间',
    checkout_time           datetime                   not null comment '结账时间',
    pay_method              tinyint unsigned           not null comment '支付方式 1微信,2支付宝',
    pay_status              tinyint unsigned default 0 not null comment '支付状态 0未支付 1已支付 2退款',
    amount                  decimal(10, 2)             not null comment '实收金额',
    remark                  varchar(128) comment '备注',
    phone                   varchar(16)                not null comment '手机号',
    address                 varchar(256)               not null comment '地址',
    username                varchar(32)                not null comment '用户名',
    consignee               varchar(32)                not null comment '收货人',
    cancel_reason           varchar(255) comment '订单取消原因',
    rejection_reason        varchar(255) comment '订单拒绝原因',
    cancel_time             datetime comment '订单取消时间',
    estimated_delivery_time datetime         default null comment '预计送达时间',
    delivery_status         tinyint unsigned default 1 not null comment '配送状态  1立即送出  0选择具体时间',
    delivery_time           datetime         default null comment '送达时间',
    pack_amount             decimal(10, 2)   default null comment '打包费',
    tableware_number        int unsigned     default null comment '餐具数量',
    tableware_status        tinyint unsigned           not null default 1 comment '餐具数量状态  1按餐量提供  0选择具体数量'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table set_meal
(
    id          bigint unsigned auto_increment primary key,
    category_id bigint unsigned            not null comment '菜品分类id',
    name        varchar(32) unique         not null comment '套餐名称',
    price       decimal(10, 2)             not null comment '套餐价格',
    status      tinyint unsigned default 1 not null comment '售卖状态 0:停售 1:起售',
    description varchar(256)               null comment '描述信息',
    image       varchar(256)               null comment '图片',
    create_time datetime                   null comment '创建时间',
    update_time datetime                   null comment '更新时间',
    create_user bigint unsigned            null comment '创建人',
    update_user bigint unsigned            null comment '修改人'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table set_meal_dish
(
    id          bigint unsigned auto_increment primary key,
    set_meal_id bigint unsigned        not null comment '套餐id',
    dish_id     bigint unsigned        not null comment '菜品id',
    copies      int unsigned default 1 not null null comment '菜品份数'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table shopping_cart
(
    id             bigint unsigned auto_increment primary key,
    name           varchar(64)            null comment '商品名称',
    image          varchar(2556)          null comment '图片',
    user_id        bigint unsigned        not null comment '主键',
    dish_id        bigint unsigned        null comment '菜品id',
    set_meal_id    bigint unsigned        null comment '套餐id',
    dish_flavor_id varchar(64)            not null comment '口味',
    number         int unsigned default 1 not null comment '数量',
    amount         decimal(10, 2)         not null comment '金额',
    create_time    datetime               not null comment '创建时间'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;

create table user_role
(
    id      bigint unsigned auto_increment primary key,
    user_id bigint unsigned not null,
    role    varchar(32)     not null comment '角色'
)
    collate = utf8mb4_unicode_ci
    auto_increment = 1;
CREATE TABLE `address_book`
(
    `id`            bigint unsigned primary key AUTO_INCREMENT COMMENT '主键',
    `user_id`       bigint unsigned  not null comment '用户id',
    `consignee`     varchar(50)      not null comment '收货人',
    `sex`           tinyint unsigned not null comment '性别',
    `phone`         varchar(11)      not null comment '手机号',
    `province_code` varchar(12)      not null comment '省级区划编号',
    `province_name` varchar(32)      not null comment '省级名称',
    `city_code`     varchar(12)      not null comment '市级区划编号',
    `city_name`     varchar(32)      not null comment '市级名称',
    `district_code` varchar(12)      not null comment '区级区划编号',
    `district_name` varchar(32)      not null comment '区级名称',
    `detail`        varchar(256)     not null comment '详细地址',
    `label`         varchar(128)     not null comment '标签',
    `is_default`    tinyint unsigned not null default '0' COMMENT '默认 0 否 1是'
) collate = utf8mb4_unicode_ci
  auto_increment = 1;