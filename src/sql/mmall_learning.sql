--数据库初始化脚本

--创建数据库
create database mmall_learning character set utf8;
--使用数据库
use mmall_learning;



-- 用户表
create table mmall_user(
  id int(11) NOT NULL auto_increment comment '用户id',
  username varchar(50) not null comment '用户名',
  password varchar(50) not null comment '用户密码，MD5加密',
  email varchar(50) default null comment '用户邮箱',
  phone varchar(50) default null comment '用户手机号',
  question varchar(100) default null comment '找回密码问题',
  answer varchar(100) default null comment '找回密码问题的答案',
  role int(4) not null comment '角色0-管理员1-普通用户',
  create_time datetime not null comment '创建时间',
  update_time datetime not null comment '上次更新时间',
  primary key (id),
  unique key user_name_unique (username) using btree
)ENGINE=InnoDB Auto_increment=21 DEFAULT charset=utf8;


-- 分类表
create table mmall_category(
  id int(11) NOT NULL auto_increment comment '类别id',
  parenty int(11) default NULL comment '父类别id当id=0时说明是根节点,一级类别',
  category_name varchar(50) default null comment '类别名称',
  status tinyint(1) default '1' comment '类别状态1-正常2-已废弃',
  sort_order int(4) default null comment '排序编号,同类展示顺序,数值相同则自然排序',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id)
)ENGINE=InnoDB Auto_increment=100032 DEFAULT charset=utf8;

-- 产品表
create table mmall_product(
  id int(11) NOT NULL auto_increment comment '商品id',
  category_id int(11) NOT NULL comment '分类id,对应mmall_category表的主键',
  product_name varchar(100) not null comment '商品名称',
  subtitle varchar(200) default null comment '商品副标题',
  main_image varchar(500) default null comment '产品主图,url相对路径',
  sub_image text comment '图片地址,json格式,扩展使用',
  detail text comment '商品详情',
  price decimal(20,2) not null comment '价格,单位-元保留两位小数,整数位18位',
  stock int(11) not null comment '库存数量',
  status int(6) default '1' comment '商品状态,1-在售,2-下架,3-删除',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id)
)ENGINE=InnoDB Auto_increment=26 DEFAULT charset=utf8;

-- 购物车表
create table mmall_cart(
  id int(11) NOT NULL auto_increment ,
  user_id int(11) NOT NULL,
  product_id int(11) default NULL comment '商品id',
  quantity int(11) default null comment '数量',
  checked int(11) default null comment '是否选择,1=已勾选,0=未勾选',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id),
  key user_id_index (user_id) using btree
)ENGINE=InnoDB Auto_increment=121 DEFAULT charset=utf8;


-- 支付信息表
create table mmall_pay_info(
  id int(11) NOT NULL auto_increment ,
  user_id int(11) NOT NULL comment '用户id',
  order_no bigint(11) default NULL comment '订单号',
  pay_platform int(10) default null comment '支付平台:1-支付宝;2-微信',
  platform_number varchar(200) default null comment '支付宝支付流水号',
  platform_status varchar(20) default null comment '支付宝支付状态',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id)
)ENGINE=InnoDB Auto_increment=53 DEFAULT charset=utf8;


-- 订单表
create table mmall_order(
  id int(11) NOT NULL auto_increment comment '订单id',
  order_no bigint(20) default NULL comment '订单号',
  user_id int(11) default NULL comment '用户id',
  shipping_id int(11) default NULL,
  payment decimal(20,2) default NULL comment '实际付款金额,单位-元保留两位小数,整数位18位',
  payment_type int(4) default null comment '支付类型:1-在线支付',
  postage int(10) default null comment '运费,单位元',
  status int(10) default null comment '订单状态:0-已取消,10-未付款,20-已付款,30-已发货,40-交易成功,60-交易关闭',
  payment_time datetime default null comment '支付时间',
  send_time datetime default null comment '发货时间',
  end_time datetime default null comment '交易完成时间',
  close_time datetime default null comment '交易关闭时间',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id),
  UNIQUE key order_no_index (order_no) using btree
)ENGINE=InnoDB Auto_increment=103 DEFAULT charset=utf8;


-- 订单明细表
create table mmall_order_item(
  id int(11) NOT NULL auto_increment comment '订单子表id',
  user_id int(11) default NULL comment '用户id',
  order_no bigint(20) default NULL comment '订单号',
  product_id int(11) default null comment '商品id',
  product_name varchar(100) default null comment '商品名称',
  product_image varchar(500) default null comment '商品图片',
  current_unit_price decimal(20,2) default null comment '生成订单时的商品单价,单位-元保留两位小数,整数位18位',
  quantity int(10) default  null comment '商品数量',
  total_price decimal(20,2) default null comment '商品总价,单位-元保留两位小数,整数位18位',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id),
  key order_no_index (order_no) using btree,
  key order_no_user_id_index (user_id,order_no) using btree
)ENGINE=InnoDB Auto_increment=103 DEFAULT charset=utf8;


-- 收货地址表
create table mmall_shipping(
  id int(11) not null auto_increment comment '地址id',
  user_id int(11) default null comment '用户id',
  receiver_name varchar(20) default null comment '收货人姓名',
  receiver_phone varchar(20) default null comment '收货固定电话',
  receiver_mobile varchar(20) default null comment '收货移动电话',
  receiver_province varchar(20) default null comment '收货省份',
  receiver_city varchar(20) default null comment '收货城市',
  receiver_district varchar(20) default null comment '收货区/县',
  receiver_address varchar(200) default null comment '收货详细地址',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '上次更新时间',
  primary key (id)
)ENGINE=InnoDB Auto_increment=32 default  charset=utf8;
