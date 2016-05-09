DROP DATABASE IF EXISTS SHOPPING;
CREATE DATABASE SHOPPING;
USE SHOPPING;

create table `email_template`(
	`id_email_template` int not null auto_increment,
    `code` nvarchar(100),
    `text` nvarchar(1000),
    `type` int,
    `subject` nvarchar(1000),
    `sender_name` nvarchar(100),
    `sender_email` nvarchar(100),
    primary key(`id_email_template`)
);

create table `customer_group`(
	`id_customer_group` int not null auto_increment,
    `code_customer_group` nvarchar(100),
    `list_score` int,
    primary key (`id_customer_group`)
);

create table `customer`(
	`id_customer` int not null auto_increment,
    `first_name` nvarchar(100),
    `last_name` nvarchar(100),
    `gender` int,
    `email` nvarchar(100) not null,
    `password` nvarchar(100) not null,
    `group_id` int,
    `score` int,
    `is_active` int not null,
    `create_at` nvarchar(100),
    `update_at` nvarchar(100),
    `date_log` nvarchar(100),
    `number_log` int,
    primary key (`id_customer`),
    constraint FK_CUSTOMER_CUSTOMER_GROUP foreign key (`group_id`) references `customer_group` (`id_customer_group`)
);
create table `customer_address` (
	`id_customer_address` int not null auto_increment,
    `customer_id` int not null,
    `create_at` nvarchar(100),
    `update_at` nvarchar(100),
    `street` nvarchar(1000),
    `city` nvarchar(1000),
    `first_name` nvarchar(100),
    `last_name` nvarchar(100),
    `phone` nvarchar(100),
    primary key(`id_customer_address`),
    constraint FK_ADDRESS_CUSTOMER foreign key(`customer_id`) references `customer`(`id_customer`)
);


create table `customer_log` (
	`id_customer_log` int not null auto_increment,
    `customer_id` int not null,
    `login_at` datetime,
    `logout_at` datetime,
    primary key (`id_customer_log`),
    constraint FK_CUSTOMER_LOG_CUSTOMER foreign key(`customer_id`) references `customer` (`id_customer`)
);

CREATE TABLE `list_rules` (
  `id_list_rule` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NOT NULL,
  `description` NVARCHAR(1000),
  PRIMARY KEY (`id_list_rule`)
);

CREATE TABLE `admin_roles`(
	`id_admin_role` INT NOT NULL AUTO_INCREMENT,
    `group_id` INT NOT NULL,
    `rule_id` INT NOT NULL,
    primary key (`id_admin_role`),
    constraint FK_ADMIN_ROLES_GROUP foreign key (`group_id`) references `customer_group` (`id_customer_group`),
    constraint FK_ADMIN_ROLES_LIST_RULE foreign key (`rule_id`) references `list_rules` (`id_list_rule`)
);

create table `admin_rules`(
	`id_admin_rule` int not null auto_increment,
    `customer_id` int not null,
    `admin_role_id` int not null,
    primary key(`id_admin_rule`),
    constraint FK_ADMIN_RULES_ADMIN_ROLES foreign key(`admin_role_id`) references `admin_roles` (`id_admin_role`),
    constraint FK_ADMIN_RULES_CUSTOMER foreign key (`customer_id`) references `customer` (`id_customer`)
);

create table `stock` (
	`id_stock` int not null auto_increment,
    `name_stock` nvarchar(100) not null,
    primary key (`id_stock`)
);

create table `category` (
	`id_category` int not null auto_increment,
    `name_category` nvarchar(100) not null,
    primary key (`id_category`)
);

create table `category_product` (
	`id_category_product` int not null auto_increment,
    `name_category_product` nvarchar(100) not null,
    `category_id` int,
    primary key (`id_category_product`),
    constraint FK_CATEGORY_PRODUCT_CATEGORY foreign key (`category_id`) references `category` (`id_category`)
);

create table `product` (
	`id_product` int not null auto_increment,
    `name` nvarchar(100) not null,
    `price` float not null,
    `is_new` int,
    `is_sale` int,
    `price_sale` float,
    `is_wishlist` int,
    `image` nvarchar(100) not null,
    `is_active` int,
    `category_product_id` int not null,
    primary key (`id_product`),
    constraint FK_PRODUCT_CATEGORY_PRODUCT foreign key (`category_product_id`) references `category_product` (`id_category_product`)
);

create table `product_information` (
	`id_product_information` int not null auto_increment,
    `product_id` int not null,
    `name` nvarchar(100),
    `value` nvarchar(100),
    primary key (`id_product_information`),
    constraint FK_PRODUCT_INFORMATION_PRODUCT foreign key(`product_id`) references `product` (`id_product`)
);

create table `product_colors_stock` (
	`id_product_color_stock` int not null auto_increment,
    `product_id` int not null,
    `key_color` nvarchar(100) not null,
    `value_color` nvarchar(100),
    `price` float,
    `count` int not null,
    `stock_id` int not null,
    primary key (`id_product_color_stock`),
    constraint FK_PRODUCT_COLORS_STOCK_PRODUCT foreign key (`product_id`) references `product` (`id_product`),
    constraint FK_PRODUCT_COLORS_STOCK_STOCK foreign key (`stock_id`) references `stock` (`id_stock`)
);

create table `product_pictues`(
	`id_product_picture` int not null auto_increment,
	`product_id` int not null,
    `path` nvarchar(100),
    primary key (`id_product_picture`),
    constraint FK_PRODUCT_PICTURES_PRODUC foreign key (`product_id`) references `product` (`id_product`)
);

create table `product_relation`(
	`id_product_relation` int not null auto_increment,
    `product_id` int not null,
	`product_id_relation` int not null,
    primary key(`id_product_relation`),
    constraint FK_PRODUCT_RELATION_PRODUCT foreign key (`product_id`) references `product` (`id_product`),
    constraint FK_PRODUCT_RELATION_PRODUCT_RELATION foreign key (`product_id_relation`) references `product` (`id_product`)
);

create table `product_comment`(
	`id_product_comment` int not null auto_increment,
    `product_id` int not null,
    `position` int not null,
    `nickname` nvarchar(100) not null,
    `parent_comment_id` int,
    `contents` nvarchar(100) not null,
    primary key (`id_product_comment`),
    constraint FK_PRODUCT_COMMENT_PRODUCT foreign key(`product_id`) references `product` (`id_product`),
    constraint FK_PRODUCT_COMMENT_PRODUCT_COMMENT foreign key(`parent_comment_id`) references `product_comment` (`id_product_comment`)
);
//----------------------------------


create table `carts`(
	`id_cart` int not null auto_increment,
    `customer_id` int,
    `create_at` nvarchar(100),
    `update_at` nvarchar(100),
    `count_total_products` int,
    `price_total_products` float,
    `product_ids` nvarchar(100),
    `product_names` nvarchar(100),
    `product_counts` nvarchar(100),
    `product_prices` nvarchar(100),
    primary key (`id_cart`),
    constraint FK_CARTS_CUSTOMER foreign key(`customer_id`) references `customer` (`id_customer`)
);

create table `orders`(
	`id_order` int not null auto_increment,
    `status` int not null,
    `customer_id` int not null,
    `cart_id` int not null,
    `address_id` int not null,
    `create_at` datetime,
    `update_at` datetime,
    `product_ids` nvarchar(100),
    `product_names` nvarchar(100),
    `product_counts` nvarchar(100),
    `product_prices` nvarchar(100),
    `product_total_count` int,
    `product_total_price` float,
    `key_sale_customer` int,
    `value_sale_customer` nvarchar(100),
    `shipping_price` float,
    `payment_total_customer` float,
    primary key (`id_order`),
    constraint FK_ORDERS_CUSTOMER foreign key(`customer_id`) references `customer` (`id_customer`),
    constraint FK_ORDERS_CUSTOMER_ADDRESS foreign key(`address_id`) references `customer_address` (`id_customer_address`),
    constraint FK_ORDERS_CARTS foreign key(`cart_id`) references `carts` (`id_cart`)
);

create table `order_payments`(
	`id_order_payment` int not null auto_increment,
	`order_id` int not null,
	`product_ids` nvarchar(100),
    `product_names` nvarchar(100),
    `product_counts` nvarchar(100),
    `product_prices` nvarchar(100),
    `product_total_count` int,
    `product_total_price` float,
    `price_sale_customer` float,
    `shipping_price` float,
    `payment_total_customer` float,
    primary key (`id_order_payment`),
    constraint FK_ORDER_PAYMENTS foreign key (`order_id`) references `orders` (`id_order`)
);

create table `tax_class`(
	`id_tax_class` int not null auto_increment,
    `name` nvarchar(100),
    primary key (`id_tax_class`)
);

create table `tax_rule`(
	`id_tax_rule` int not null auto_increment,
    `code` nvarchar(100),
    `prioriy` int,
    primary key(`id_tax_rule`)
);

create table `tax_rate`(
	`id_tax_rate` int not null auto_increment,
	`country_id` int,
    `region_id` int,
    `code` nvarchar(100),
    `rate` float,
    primary key(`id_tax_rate`)
);

create table `tax_price`(
	`id_tax_price` int not null auto_increment,
    `tax_rule_id` int,
    `tax_rate_id` int,
    `class_id` int,
    `price` float,
    `description` nvarchar(1000),
    primary key (`id_tax_price`),
    constraint FK_TAX_PRICE_TAX_CLASS foreign key(`class_id`) references `tax_class` (`id_tax_class`),
    constraint FK_TAX_PRICE_TAX_RATE foreign key(`tax_rate_id`) references `tax_rate` (`id_tax_rate`),
    constraint FK_TAX_PRICE_TAX_RULE foreign key(`tax_rule_id`) references `tax_rule` (`id_tax_rule`)
);

create table `sitemap`(
	`id_sitemap` int not null auto_increment,
    `type` nvarchar(100),
    `filename` nvarchar(100),
    `path` nvarchar(100),
    `time` date,
    primary key(`id_sitemap`)
);

create table `verification_token` (
	`id_verification_token` int not null auto_increment,
    `admin_id` int,
    `customer_id` int not null,
    `type` nvarchar(100),
    `token` nvarchar(100),
    `verifer` nvarchar(100),
    `create-at` datetime,
    `expity_at` datetime,
    primary key(`id_verification_token`),
    constraint FK_VERIFICATION_TOKEN_CUSTOMER foreign key(`customer_id`) references `customer` (`id_customer`)
);

create table `wishlist_item` (
	`id_wishlist_item` int not null auto_increment,
    `product_id` int,
    `name_wishlist` nvarchar(100),
    `price_wishlist` float,
    `count_wishlist` int,
    `description` nvarchar(100),
    primary key(`id_wishlist_item`),
    constraint FK_WISHLISH_ITEM_PRODUCT foreign key(`product_id`) references `product` (`id_product`)
);

create table `wishlist_shipping`(
	`id_wishlist_shipping` int not null auto_increment,
    `order_id` int not null,
    `wishlist_item_id` int not null,
    `create_at` date,
    `update_at` date,
    primary key(`id_wishlist_shipping`),
    constraint FK_WISHLIST_SHPPING_ODERS foreign key(`order_id`) references `orders` (`id_order`)
);

//-- exception table
create table `exception`(
	`id_exception` int not null auto_increment,
    `time` nvarchar(100) not null,
    `method` nvarchar(100) not null,
    `content` nvarchar(10000) not null,
    primary key(`id_exception`)
);

//-- category token
create table `category_token`(
	`id_category_token` int not null auto_increment,
    `name` nvarchar(100) not null,
    primary key(`id_category_token`)
);

//-- token table
create table `token`(
	`id_token` int not null auto_increment,
    `category_token_id` int not null,
    `customer_id` int not null,
    `token` nvarchar(100) not null,
    `create_at` nvarchar(100) not null,
    `expiry_date` nvarchar(100) not null,
    primary key(`id_token`),
    constraint FK_TOKEN_CATEGORY_TOKEN foreign key(`category_token_id`) references `category_token`(`id_category_token`),
    constraint FK_TOKEN_CUSTOMER foreign key(`customer_id`) references `customer`(`id_customer`)
);
//-- Insert Category Token
insert into category_token values(1, 'email');
insert into category_token values(2, 'user');

//-- Groupd Table
insert into customer_group values(1, 'User', 0);
insert into customer_group values(2, 'Admin', 0);
insert into customer_group values(3, 'Saler', 0);
insert into customer_group values(4, 'Exporter', 0);
insert into customer_group values(5, 'Importer', 0);
insert into customer_group values(6, 'QA', 0);

//-- List Rules

insert into list_rules values(1, 'STAGE_ACCOUNT', 'Active and Not active account');
insert into list_rules values(2, 'ROLE_ACCOUNT', 'Change role account');

insert into list_rules values(3, 'READ_CATEGORY', 'Read category');
insert into list_rules values(4, 'UPDATE_CATEGORY', 'Update category');
insert into list_rules values(5, 'CREATE_CATEGORY', 'Create category');
insert into list_rules values(6, 'DELETE_CATEGORY', 'View category');

insert into list_rules values(7, 'READ_PRODUCT', 'Read Product');
insert into list_rules values(8, 'UPDATE_PRODUCT', 'Update Product');
insert into list_rules values(9, 'CREATE_PRODUCT', 'Create Product');
insert into list_rules values(10, 'DELETE_PRODUCT', 'View Product');

insert into list_rules values(11, 'READ_ORDER', 'Read Order');
insert into list_rules values(12, 'UPDATE_ORDER', 'Update Order');
insert into list_rules values(13, 'DELETE_ORDER', 'View Order');

insert into list_rules values(14, 'READ_STATISTICAL', 'View Statistical');

//-- Table Admin Roles
//-- Mỗi Roles sẽ có nhiều rules: Role cho Admin
insert into admin_roles values(1, 2, 1);
insert into admin_roles values(2, 2, 2);
insert into admin_roles values(3, 2, 3);
insert into admin_roles values(4, 2, 4);
insert into admin_roles values(5, 2, 5);
insert into admin_roles values(6, 2, 6);
insert into admin_roles values(7, 2, 7);
insert into admin_roles values(8, 2, 8);
insert into admin_roles values(9, 2, 9);
insert into admin_roles values(10, 2, 10);
insert into admin_roles values(11, 2, 11);
insert into admin_roles values(12, 2, 12);
insert into admin_roles values(13, 2, 13);
insert into admin_roles values(14, 2, 14);
//-- Role cho Saler
insert into admin_roles values(15, 3, 3);
insert into admin_roles values(16, 3, 7);
insert into admin_roles values(17, 3, 11);
//-- Role cho Exporter
insert into admin_roles values(18, 4, 3);
insert into admin_roles values(19, 4, 4);
insert into admin_roles values(20, 4, 7);
insert into admin_roles values(21, 4, 8);
insert into admin_roles values(22, 4, 11);
insert into admin_roles values(23, 4, 12);
//-- Role cho Importer
insert into admin_roles values(24, 5, 3);
insert into admin_roles values(25, 5, 4);
insert into admin_roles values(26, 5, 5);
insert into admin_roles values(27, 5, 6);
insert into admin_roles values(28, 5, 7);
insert into admin_roles values(29, 5, 8);
insert into admin_roles values(30, 5, 9);
insert into admin_roles values(31, 5, 10);
//-- Role cho QA
insert into admin_roles values(33, 6, 3);
insert into admin_roles values(34, 6, 7);

//-- Category
insert into category values(1, 'SmartPhone');
insert into category values(2, 'Laptop');
insert into category values(3, 'Tablet');
//-- Category Products
insert into category_product values(1, 'IPhone', 1);
insert into category_product values(2, 'SamSung', 1);
insert into category_product values(3, 'Nokia-Microsoft', 1);
insert into category_product values(4, 'SONY', 1);
insert into category_product values(5, 'HTC', 1);
insert into category_product values(6, 'OPPO', 1);
insert into category_product values(7, 'DELL', 2);
insert into category_product values(8, 'ACER', 2);
insert into category_product values(9, 'ASUS', 2);
insert into category_product values(10, 'HP-Compad', 2);
insert into category_product values(11, 'Lenovo', 2);
insert into category_product values(12, 'Apple', 3);
insert into category_product values(13, 'SamSung', 3);
insert into category_product values(14, 'Acer', 3);
insert into category_product values(15, 'Asus', 3);


//-- Products
insert into product values(1, 'IPhone 6s Plus 128GB',27490000.0, 0, 0, 0.0, 0, 'images/iphone/6splus/index.jpg', 1, 1);
insert into product values(2, 'IPhone 6s Plus 64GB',24690000.0, 0, 0, 0.0, 0, 'images/iphone/6splus/index.jpg', 1, 1);
insert into product values(3, 'IPhone 6 Plus 128GB',24790000.0, 0, 0, 0.0, 0, 'images/iphone/6plus/index.jpg', 1, 1);
insert into product values(4, 'IPhone 6 Plus 64GB',21590000.0, 0, 0, 0.0, 0, 'images/iphone/6plus/index.jpg', 1, 1);
insert into product values(5, 'IPhone 5s 16GB',7990000.0, 0, 0, 0.0, 0, 'images/iphone/5s/index.jpg', 1, 1);
insert into product values(6, 'IPhone 6 16GB',14990000.0, 0, 0, 0.0, 0, 'images/iphone/6/index.jpg', 1, 1);
insert into product values(7, 'IPhone 6 64GB',18490000.0, 0, 0, 0.0, 0, 'images/iphone/5s/index.jpg', 1, 1);

insert into product values(8, 'Samsung Galaxy S6 Edge Plus',19990000.0, 0, 0, 0.0, 0, 'images/samsung/s6_edge_plus/index.jpg', 1, 2);
insert into product values(9, 'Samsung Galaxy S6 Edge 32GB',14990000.0, 0, 0, 0.0, 0, 'images/samsung/s6_edge/index.jpg', 1, 2);
insert into product values(10, 'Samsung Galaxy S6 Edge 64GB',15990000.0, 0, 0, 0.0, 0, 'images/samsung/s6/index.jpg', 1, 2);
insert into product values(11, 'Samsung Galaxy S6 32GB',14490000.0, 0, 0, 0.0, 0, 'images/samsung/s6/index.jpg', 1, 2);
insert into product values(12, 'Note 4',9990000.0, 0, 0, 0.0, 0, 'images/samsung/note4/index.jpg', 1, 2);
insert into product values(13, 'A5 2016',8990000.0, 0, 0, 0.0, 0, 'images/samsung/a5/index.jpg', 1, 2);
insert into product values(14, 'A7',7490000.0, 0, 0, 0.0, 0, 'images/samsung/a7/index.jpg', 1, 2);


insert into product values(15, 'OPPO R7 Plus',11490000.0, 0, 0, 0.0, 0, 'images/oppo/r7_plus/index.jpg', 1, 6);
insert into product values(16, 'OPPO R7s',8990000.0, 0, 0, 0.0, 0, 'images/oppo/r7s/index.jpg', 1, 6);
insert into product values(17, 'OPPO F1',5990000.0, 0, 0, 0.0, 0, 'images/oppo/f1/index.jpg', 1, 6);
insert into product values(18, 'OPPO R7 Lite',5990000.0, 0, 0, 0.0, 0, 'images/oppo/r7_lite/index.jpg', 1, 6);
insert into product values(19, 'OPPO Mirror 5',4990000.0, 0, 0, 0.0, 0, 'images/oppo/mirror5/index.jpg', 1, 6);
insert into product values(20, 'OPPO NEO 7',3690000.0, 0, 0, 0.0, 0, 'images/oppo/neo7/index.jpg', 1, 6);
insert into product values(21, 'OPPO NEO 5 16GB',2690000.0, 0, 0, 0.0, 0, 'images/oppo/neo5/index.jpg', 1, 6);

insert into product values(22, 'Sony Xperia Z5 Premium Dual',17990000.0, 0, 0, 0.0, 0, 'images/sony/z5_premium_dual/index.jpg', 1, 4);
insert into product values(23, 'Sony Xperia Z5 Dual',13990000.0, 0, 0, 0.0, 0, 'images/sony/z5_dual/index.jpg', 1, 4);
insert into product values(24, 'Sony Xperia Z3 Plus',11990000.0, 0, 0, 0.0, 0, 'images/sony/z3_plus/index.jpg', 1, 4);
insert into product values(25, 'Sony Xperia M5 Dual',9490000.0, 0, 0, 0.0, 0, 'images/sony/m5_dual/index.jpg', 1, 4);
insert into product values(26, 'Sony Xperia C4 Dual',6190000.0, 0, 0, 0.0, 0, 'images/sony/c4_dual/index.jpg', 1, 4);

insert into product values(27, 'Microsoft Lumia 950XL',15990000.0, 0, 0, 0.0, 0, 'images/lumia/950xl/index.jpg', 1, 3);
insert into product values(28, 'Microsoft Lumia 950',13990000.0, 0, 0, 0.0, 0, 'images/lumia/950/index.jpg', 1, 3);
insert into product values(29, 'Microsoft Lumia 640XL',4590000.0, 0, 0, 0.0, 0, 'images/lumia/640xl/index.jpg', 1, 3);
insert into product values(30, 'Microsoft Lumia 640',3690000.0, 0, 0, 0.0, 0, 'images/lumia/640/index.jpg', 1, 3);
insert into product values(31, 'Microsoft Lumia 730',3690000.0, 0, 0, 0.0, 0, 'images/lumia/730/index.jpg', 1, 3);
insert into product values(32, 'Microsoft Lumia 535',2490000.0, 0, 0, 0.0, 0, 'images/lumia/535/index.jpg', 1, 3);
insert into product values(33, 'Microsoft Lumia 550',2990000.0, 0, 0, 0.0, 0, 'images/lumia/550/index.jpg', 1, 3);

insert into product values(34, 'HTC One A9',10990000.0, 0, 0, 0.0, 0, 'images/htc/a9/index.jpg', 1, 5);
insert into product values(35, 'HTC One M9s',8990000.0, 0, 0, 0.0, 0, 'images/htc/m9s/index.jpg', 1, 5);
insert into product values(36, 'HTC One M8 Eye',8290000.0, 0, 0, 0.0, 0, 'images/htc/m8_eye/index.jpg', 1, 5);
insert into product values(37, 'HTC Desire Eye',7490000.0, 0, 0, 0.0, 0, 'images/htc/desire_eye/index.jpg', 1, 5);
insert into product values(38, 'HTC One E9 Dual',6490000.0, 0, 0, 0.0, 0, 'images/htc/e9_dual/index.jpg', 1, 5);
insert into product values(39, 'HTC One E8 Dual',6390000.0, 0, 0, 0.0, 0, 'images/htc/e8_dual/index.jpg', 1, 5);

//-- Products
insert into product values(40, 'Dell Inspiron 3552 N3050',67990000.0, 0, 0, 0.0, 0, 'images/dell/insprion_3552/index.jpg', 1, 7);
insert into product values(41, 'Dell Inspiron 3451 N3540',7190000.0, 0, 0, 0.0, 0, 'images/dell/insprion_3451/index.jpg', 1, 7);
insert into product values(42, 'Dell Inspiron 3452 N3700',7490000.0, 0, 0, 0.0, 0, 'images/dell/insprion_3452/index.jpg', 1, 7);
insert into product values(43, 'Dell Vostro 5480 5200U',16590000.0, 0, 0, 0.0, 0, 'images/dell/vostro_5480/index.jpg', 1, 7);
insert into product values(44, 'Dell Vostro 5459',17990000.0, 0, 0, 0.0, 0, 'images/dell/vostro_5459/index.jpg', 1, 7);

insert into product values(45, 'Acer ES1 311',5490000.0, 0, 0, 0.0, 0, 'images/acer_laptop/es1_311/index.jpg', 1, 8);
insert into product values(46, 'Acer ES1 431 N3050',5990000.0, 0, 0, 0.0, 0, 'images/acer_laptop/es1_431/index.jpg', 1, 8);
insert into product values(47, 'Acer ES1 431 N3700',6990000.0, 0, 0, 0.0, 0, 'images/acer_laptop/es1_431/index.jpg', 1, 8);
insert into product values(48, 'Acer Aspire Z1402',8790000.0, 0, 0, 0.0, 0, 'images/acer_laptop/aspire_z1402/index.jpg', 1, 8);
insert into product values(49, 'Acer Aspire E5',8990000.0, 0, 0, 0.0, 0, 'images/acer_laptop/aspire_e5/index.jpg', 1, 8);

insert into product values(50, 'Asus E402MA N2840',5990000.0, 0, 0, 0.0, 0, 'images/asus_laptop/e402ma/index.jpg', 1, 9);
insert into product values(51, 'Asus E402SA N3050',6290000.0, 0, 0, 0.0, 0, 'images/asus_laptop/e402sa/index.jpg', 1, 9);
insert into product values(52, 'Asus X553MA N3540',6990000.0, 0, 0, 0.0, 0, 'images/asus_laptop/x553ma/index.jpg', 1, 9);
insert into product values(53, 'Asus A540LA 4005U',9490000.0, 0, 0, 0.0, 0, 'images/asus_laptop/a540la/index.jpg', 1, 9);
insert into product values(54, 'Asus F454LA 4005U',9490000.0, 0, 0, 0.0, 0, 'images/asus_laptop/f454la/index.jpg', 1, 9);

insert into product values(55, 'HP 14 6200U',12990000.0, 0, 0, 0.0, 0, 'images/hp/14_6200/index.jpg', 1, 10);
insert into product values(56, 'HP 15 5200U',14290000.0, 0, 0, 0.0, 0, 'images/hp/15_5200/index.jpg', 1, 10);
insert into product values(57, 'HP Pavilion 14 6200U',14490000.0, 0, 0, 0.0, 0, 'images/hp/pavilion_14_6200/index.jpg', 1, 10);
insert into product values(58, 'HP Pavilion 15 6200U',15690000.0, 0, 0, 0.0, 0, 'images/hp/pavilion_15_6200/index.jpg', 1, 10);
insert into product values(59, 'HP Pavilion 15 6500U',18490000.0, 0, 0, 0.0, 0, 'images/hp/pavilion_15_6500/index.jpg', 1, 10);

insert into product values(60, 'Lenovo IdeaPad 100 14IBY N2840',5490000.0, 0, 0, 0.0, 0, 'images/lenovo/ideapad_n2840/index.jpg', 1, 11);
insert into product values(61, 'Lenovo IdeaPad 100 14IBY N3540',6490000.0, 0, 0, 0.0, 0, 'images/lenovo/ideapad_n3540/index.jpg', 1, 11);
insert into product values(62, 'Lenovo IdeaPad 100 15IBD 5005',9490000.0, 0, 0, 0.0, 0, 'images/lenovo/ideapad_5005/index.jpg', 1, 11);
insert into product values(63, 'Lenovo Yoga 500 14IBD 5020U',11690000.0, 0, 0, 0.0, 0, 'images/lenovo/yoga_5020u/index.jpg', 1, 11);
insert into product values(64, 'Lenovo ThinkPad E460 6200U',14990000.0, 0, 0, 0.0, 0, 'images/lenovo/thinkpad_6200u/index.jpg', 1, 11);

//-- Stock
insert into stock values(1, 'Nguyễn Thị Minh Khai');
insert into stock values(2, 'Nguyễn Kiệm');
insert into stock values(3, 'Quận 1');

//-- Product Informations
//-- IP 6s plus 128gb
insert into product_information values(1,1, 'Màn hình:', 'LED-backlit IPS LCD, 5.5", Retina HD');
insert into product_information values(2,1, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(3,1, 'Camera sau:', '12 MP');
insert into product_information values(4,1, 'Camera trước:', '5 MP');
insert into product_information values(5,1, 'CPU:', 'Apple A9 2 nhân 64-bit, 1.8 GHz');
insert into product_information values(6,1, 'RAM:', '2 GB');
insert into product_information values(7,1, 'Bộ nhớ trong:', '128 GB');
insert into product_information values(8,1, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(9,1, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(10,1, 'Kết nối:', '4G LTE Cat 6');
insert into product_information values(11,1, 'Dung lượng pin:', '2750 mAh');
insert into product_information values(12,1, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(13,1, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');
//-- IP 6s plus 64gb
insert into product_information values(14,2, 'Màn hình:', 'LED-backlit IPS LCD, 5.5", Retina HD');
insert into product_information values(15,2, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(16,2, 'Camera sau:', '12 MP');
insert into product_information values(17,2, 'Camera trước:', '5 MP');
insert into product_information values(18,2, 'CPU:', 'Apple A9 2 nhân 64-bit, 1.8 GHz');
insert into product_information values(19,2, 'RAM:', '2 GB');
insert into product_information values(20,2, 'Bộ nhớ trong:', '64 GB');
insert into product_information values(21,2, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(22,2, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(23,2, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(24,2, 'Dung lượng pin:', '2750 mAh');
insert into product_information values(25,2, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(26,2, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');

//-- IP 6 plus 128gb
insert into product_information values(27,3, 'Màn hình:', 'LED-backlit IPS LCD, 5.5", Retina HD');
insert into product_information values(28,3, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(29,3, 'Camera sau:', '8 MP');
insert into product_information values(30,3, 'Camera trước:', '1.2 MP');
insert into product_information values(31,3, 'CPU:', 'Apple A8 2 nhân 64-bit, 1.4 GHz');
insert into product_information values(32,3, 'RAM:', '1 GB');
insert into product_information values(33,3, 'Bộ nhớ trong:', '128 GB');
insert into product_information values(34,3, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(35,3, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(36,3, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(37,3, 'Dung lượng pin:', '2915 mAh');
insert into product_information values(38,3, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(39,3, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');

//-- IP6 plus 64gb

insert into product_information values(40,4, 'Màn hình:', 'LED-backlit IPS LCD, 5.5", Retina HD');
insert into product_information values(41,4, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(42,4, 'Camera sau:', '8 MP');
insert into product_information values(43,4, 'Camera trước:', '1.2 MP');
insert into product_information values(44,4, 'CPU:', 'Apple A8 2 nhân 64-bit, 1.4 GHz');
insert into product_information values(45,4, 'RAM:', '1 GB');
insert into product_information values(46,4, 'Bộ nhớ trong:', '64 GB');
insert into product_information values(47,4, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(48,4, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(49,4, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(50,4, 'Dung lượng pin:', '2915 mAh');
insert into product_information values(51,4, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(52,4, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');

//-- IP 5s 16gb
insert into product_information values(53,5, 'Màn hình:', 'LED-backlit IPS LCD, 4", DVGA');
insert into product_information values(54,5, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(55,5, 'Camera sau:', '8 MP');
insert into product_information values(56,5, 'Camera trước:', '1.2 MP');
insert into product_information values(57,5, 'CPU:', 'Apple A8 2 nhân 64-bit, 1.3 GHz');
insert into product_information values(58,5, 'RAM:', '1 GB');
insert into product_information values(59,5, 'Bộ nhớ trong:', '16 GB');
insert into product_information values(60,5, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(61,5, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(62,5, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(63,5, 'Dung lượng pin:', '1560 mAh');
insert into product_information values(64,5, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(65,5, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');

//-- IP 6 16g
insert into product_information values(66,6, 'Màn hình:', 'LED-backlit IPS LCD, 4.7", Retina HD');
insert into product_information values(67,6, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(68,6, 'Camera sau:', '8 MP');
insert into product_information values(69,6, 'Camera trước:', '1.2 MP');
insert into product_information values(70,6, 'CPU:', 'Apple A8 2 nhân 64-bit, 1.4 GHz');
insert into product_information values(71,6, 'RAM:', '1 GB');
insert into product_information values(72,6, 'Bộ nhớ trong:', '16 GB');
insert into product_information values(73,6, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(74,6, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(75,6, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(76,6, 'Dung lượng pin:', '1810');
insert into product_information values(77,6, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(78,6, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');
//-- IP 6 64g
insert into product_information values(79,7, 'Màn hình:', 'LED-backlit IPS LCD, 4.7", Retina HD');
insert into product_information values(80,7, 'Hệ điều hành:', 'iOS 9');
insert into product_information values(81,7, 'Camera sau:', '8 MP');
insert into product_information values(82,7, 'Camera trước:', '1.2 MP');
insert into product_information values(83,7, 'CPU:', 'Apple A8 2 nhân 64-bit, 1.4 GHz');
insert into product_information values(84,7, 'RAM:', '1 GB');
insert into product_information values(85,7, 'Bộ nhớ trong:', '64 GB');
insert into product_information values(86,7, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(87,7, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(88,7, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(89,7, 'Dung lượng pin:', '1810');
insert into product_information values(90,7, 'Thiết kế:', '	Nguyên khối');
insert into product_information values(91,7, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay');

//-- samsung s6 edge
insert into product_information values(92,8, 'Màn hình:', 'Super AMOLED, 5.7", Quad HD');
insert into product_information values(93,8, 'Hệ điều hành:', 'Android 5.1 (Lollipop)');
insert into product_information values(94,8, 'Camera sau:', '16 MP');
insert into product_information values(95,8, 'Camera trước:', '5 MP');
insert into product_information values(96,8, 'CPU:', '	Exynos 7420 8 nhân 64-bit, 4 nhân 1.5 GHz Cortex-A53 & 4 nhân 2.1 GHz Cortex-A57');
insert into product_information values(97,8, 'RAM:', '3 GB');
insert into product_information values(98,8, 'Bộ nhớ trong:', '32 GB');
insert into product_information values(99,8, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(100,8, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(101,8, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(102,8, 'Dung lượng pin:', '3000 mAh');
insert into product_information values(103,8, 'Thiết kế:', 'Pin liền');
insert into product_information values(104,8, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Tương thích kính thực tế ảo Gear VR, Sạc pin không dây, Sạc pin nhanh');
//-- samsung s6 edge 32g
insert into product_information values(105,9, 'Màn hình:', 'Super AMOLED, 5.7", Quad HD');
insert into product_information values(106,9, 'Hệ điều hành:', 'Android 5.1 (Lollipop)');
insert into product_information values(107,9, 'Camera sau:', '16 MP');
insert into product_information values(108,9, 'Camera trước:', '5 MP');
insert into product_information values(109,9, 'CPU:', '	Exynos 7420 8 nhân 64-bit, 4 nhân 1.5 GHz Cortex-A53 & 4 nhân 2.1 GHz Cortex-A57');
insert into product_information values(110,9, 'RAM:', '3 GB');
insert into product_information values(111,9, 'Bộ nhớ trong:', '32 GB');
insert into product_information values(112,9, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(113,9, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(114,9, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(115,9, 'Dung lượng pin:', '2600 mAh');
insert into product_information values(116,9, 'Thiết kế:', 'Pin liền');
insert into product_information values(117,9, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Tương thích kính thực tế ảo Gear VR, Sạc pin không dây, Sạc pin nhanh');
//-- samsung s6 edge 64g
insert into product_information values(118,10, 'Màn hình:', 'Super AMOLED, 5.7", Quad HD');
insert into product_information values(119,10, 'Hệ điều hành:', 'Android 5.1 (Lollipop)');
insert into product_information values(120,10, 'Camera sau:', '16 MP');
insert into product_information values(121,10, 'Camera trước:', '5 MP');
insert into product_information values(122,10, 'CPU:', '	Exynos 7420 8 nhân 64-bit, 4 nhân 1.5 GHz Cortex-A53 & 4 nhân 2.1 GHz Cortex-A57');
insert into product_information values(123,10, 'RAM:', '3 GB');
insert into product_information values(124,10, 'Bộ nhớ trong:', '64 GB');
insert into product_information values(125,10, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(126,10, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(127,10, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(128,10, 'Dung lượng pin:', '2600 mAh');
insert into product_information values(129,10, 'Thiết kế:', 'Pin liền');
insert into product_information values(130,10, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Tương thích kính thực tế ảo Gear VR, Sạc pin không dây, Sạc pin nhanh');
//-- samsung s6 32g
insert into product_information values(131,11, 'Màn hình:', 'Super AMOLED, 5.7", Quad HD');
insert into product_information values(132,11, 'Hệ điều hành:', 'Android 5.1 (Lollipop)');
insert into product_information values(133,11, 'Camera sau:', '16 MP');
insert into product_information values(134,11, 'Camera trước:', '5 MP');
insert into product_information values(135,11, 'CPU:', 'Exynos 7420 8 nhân 64-bit, 4 nhân 1.5 GHz Cortex-A53 & 4 nhân 2.1 GHz Cortex-A57');
insert into product_information values(136,11, 'RAM:', '3 GB');
insert into product_information values(137,11, 'Bộ nhớ trong:', '32 GB');
insert into product_information values(138,11, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(139,11, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(140,11, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(141,11, 'Dung lượng pin:', '2550 mAh');
insert into product_information values(142,11, 'Thiết kế:', 'Pin liền');
insert into product_information values(143,11, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Tương thích kính thực tế ảo Gear VR, Sạc pin không dây, Sạc pin nhanh');
//-- note 4
insert into product_information values(144,12, 'Màn hình:', 'Super AMOLED, 5.7", Quad HD');
insert into product_information values(145,12, 'Hệ điều hành:', 'Android 4.4 (KitKat)');
insert into product_information values(146,12, 'Camera sau:', '16 MP');
insert into product_information values(147,12, 'Camera trước:', '3.7 MP');
insert into product_information values(148,12, 'CPU:', 'Exynos 7420 8 nhân 64-bit, 4 nhân 1.5 GHz Cortex-A53 & 4 nhân 2.1 GHz Cortex-A57');
insert into product_information values(149,12, 'RAM:', '3 GB');
insert into product_information values(150,12, 'Bộ nhớ trong:', '32 GB');
insert into product_information values(151,12, 'Hỗ trợ thẻ nhớ:', 'Không, Không');
insert into product_information values(152,12, 'Thẻ SIM:', '1 Sim, Nano SIM');
insert into product_information values(153,12, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(154,12, 'Dung lượng pin:', '3220 mAh');
insert into product_information values(155,12, 'Thiết kế:', 'Pin liền');
insert into product_information values(156,12, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Sạc pin nhanh');
//-- a5 2016
insert into product_information values(157,13, 'Màn hình:', 'Super AMOLED, 5.2", Quad HD');
insert into product_information values(158,13, 'Hệ điều hành:', 'Android 5.1 (Lollipop)');
insert into product_information values(159,13, 'Camera sau:', '13 MP');
insert into product_information values(160,13, 'Camera trước:', '5 MP');
insert into product_information values(161,13, 'CPU:', 'Exynos 7580 8 nhân 64-bit, 1.6 GHz');
insert into product_information values(162,13, 'RAM:', '2 GB');
insert into product_information values(163,13, 'Bộ nhớ trong:', '16 GB');
insert into product_information values(164,13, 'Hỗ trợ thẻ nhớ:', 'MicroSD, 128 GB');
insert into product_information values(165,13, 'Thẻ SIM:', '2 SIM 2 sóng, Nano SIM');
insert into product_information values(166,13, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(167,13, 'Dung lượng pin:', '2900 mAh');
insert into product_information values(168,13, 'Thiết kế:', 'Nguyên khối');
insert into product_information values(169,13, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Sạc pin nhanh');
//-- a7
insert into product_information values(170,14, 'Màn hình:', 'Super AMOLED, 5.2", Quad HD');
insert into product_information values(171,14, 'Hệ điều hành:', 'Android 5.1 (Lollipop)');
insert into product_information values(172,14, 'Camera sau:', '13 MP');
insert into product_information values(173,14, 'Camera trước:', '5 MP');
insert into product_information values(174,14, 'CPU:', 'Exynos 7580 8 nhân 64-bit, 1.6 GHz');
insert into product_information values(175,14, 'RAM:', '3 GB');
insert into product_information values(176,14, 'Bộ nhớ trong:', '16 GB');
insert into product_information values(177,14, 'Hỗ trợ thẻ nhớ:', 'MicroSD, 128 GB');
insert into product_information values(178,14, 'Thẻ SIM:', '2 SIM 2 sóng, Nano SIM');
insert into product_information values(179,14, 'Kết nối:', 'WiFi, 3G, 4G LTE Cat 6');
insert into product_information values(180,14, 'Dung lượng pin:', '3300 mAh');
insert into product_information values(181,14, 'Thiết kế:', 'Nguyên khối');
insert into product_information values(182,14, 'Chức năng đặc biệt:', 'Mở khóa bằng vân tay, Sạc pin nhanh');

//-- Color ip 6s plus 128gb
insert into product_colors_stock values(1,1, 'W', 'Trắng', 27490000.0, 10, 1);
insert into product_colors_stock values(2,1, 'B', 'Đen', 27490000.0, 10, 1);
insert into product_colors_stock values(3,1, 'P', 'Hồng', 27490000.0, 10, 1);
insert into product_colors_stock values(4,1, 'Y', 'Gold', 27490000.0, 10, 1);
//-- color ip 6s plus 64gb
insert into product_colors_stock values(5,2, 'W', 'Trắng', 24690000.0, 10, 1);
insert into product_colors_stock values(6,2, 'B', 'Đen', 24690000.0, 10, 1);
insert into product_colors_stock values(7,2, 'P', 'Hồng', 24690000.0, 10, 1);
insert into product_colors_stock values(8,2, 'Y', 'Gold', 24690000.0, 10, 1);
//-- color ip 6 plus 128gb
insert into product_colors_stock values(9,3, 'W', 'Trắng', 24790000.0, 10, 1);
insert into product_colors_stock values(10,3, 'B', 'Đen', 24790000.0, 10, 1);
insert into product_colors_stock values(11,3, 'Y', 'Gold', 24790000.0, 10, 1);
//-- color ip 6 plus 64gb
insert into product_colors_stock values(12,4, 'W', 'Trắng', 21590000.0, 10, 1);
insert into product_colors_stock values(13,4, 'B', 'Đen', 21590000.0, 10, 1);
insert into product_colors_stock values(14,4, 'Y', 'Gold', 21590000.0, 10, 1);
//-- ip 5s 
insert into product_colors_stock values(15,5, 'W', 'Trắng', 7990000.0, 10, 1);
insert into product_colors_stock values(16,5, 'B', 'Đen', 7990000.0, 10, 1);
insert into product_colors_stock values(17,5, 'Y', 'Gold', 7990000.0, 10, 1);
//-- ip 6 16g
insert into product_colors_stock values(18,6, 'W', 'Trắng', 14990000.0, 10, 1);
insert into product_colors_stock values(19,6, 'B', 'Đen', 14990000.0, 10, 1);
insert into product_colors_stock values(20,6, 'Y', 'Gold', 14990000.0, 10, 1);
//-- ip 6 64g
insert into product_colors_stock values(21,7, 'W', 'Trắng', 18490000.0, 10, 1);
insert into product_colors_stock values(22,7, 'B', 'Đen', 18490000.0, 10, 1);
insert into product_colors_stock values(23,7, 'Y', 'Gold', 18490000.0, 10, 1);

//-- samsung s6 edge plus 
insert into product_colors_stock values(24,8, 'W', 'Trắng', 19990000.0, 10, 1);
insert into product_colors_stock values(25,8, 'B', 'Đen', 19990000.0, 10, 1);
insert into product_colors_stock values(26,8, 'Y', 'Gold', 19990000.0, 10, 1);
insert into product_colors_stock values(27,8, 'G', 'Bạc', 19990000.0, 10, 1);
//-- samsung s6 edge 32gb
insert into product_colors_stock values(28,8, 'W', 'Trắng', 14990000.0, 10, 1);
insert into product_colors_stock values(29,8, 'B', 'Đen', 14990000.0, 10, 1);
insert into product_colors_stock values(30,8, 'Y', 'Gold', 14990000.0, 10, 1);
insert into product_colors_stock values(31,8, 'G', 'Bạc', 14990000.0, 10, 1);
//-- samsung s6 edge 64gb
insert into product_colors_stock values(32,9, 'W', 'Trắng', 15990000.0, 10, 1);
insert into product_colors_stock values(33,9, 'B', 'Đen', 15990000.0, 10, 1);
insert into product_colors_stock values(34,9, 'Y', 'Gold', 15990000.0, 10, 1);
insert into product_colors_stock values(35,9, 'G', 'Bạc', 15990000.0, 10, 1);
//-- samsung s6 32gb
insert into product_colors_stock values(36,10, 'W', 'Trắng', 14490000.0, 10, 1);
insert into product_colors_stock values(37,10, 'B', 'Đen', 14490000.0, 10, 1);
insert into product_colors_stock values(38,10, 'Y', 'Gold', 14490000.0, 10, 1);
insert into product_colors_stock values(39,10, 'G', 'Bạc', 14490000.0, 10, 1);



