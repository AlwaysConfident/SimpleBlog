DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book`(
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `cover` VARCHAR(255) DEFAULT '',
    `title` VARCHAR(255) NOT NULL DEFAULT '',
    `author` VARCHAR(255) DEFAULT '',
    `date` VARCHAR(20) DEFAULT '',
    `press` VARCHAR(255) DEFAULT '',
    `abs` VARCHAR(255) DEFAULT NULL,
    `cid` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_book_category_on_cid` (`cid`),
    CONSTRAINT `fk_book_category_on_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

CREATE TABLE `admin_menu`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` VARCHAR(64) DEFAULT NULL,
  `name` VARCHAR(64) DEFAULT NULL,
  `name_zh` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `icon_cls` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `component` VARCHAR(64) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `admin_menu` VALUES ('1', '/admin', 'AdminIndex', '首页', 'el-icon-s-home', 'AdminIndex', '0');
INSERT INTO `admin_menu` VALUES ('2', '/admin/dashboard', 'DashboardAdmin', '运行情况', null, 'dashboard/admin/index', '1');
INSERT INTO `admin_menu` VALUES ('3', '/admin', 'User', '用户管理', 'el-icon-user', 'AdminIndex', '0');
INSERT INTO `admin_menu` VALUES ('4', '/admin', 'Content', '内容管理', 'el-icon-tickets', 'AdminIndex', '0');
INSERT INTO `admin_menu` VALUES ('5', '/admin', 'System', '系统配置', 'el-icon-s-tools', 'AdminIndex', '0');
INSERT INTO `admin_menu` VALUES ('6', '/admin/user/profile', 'Profile', '用户信息', null, 'user/UserProfile', '3');
INSERT INTO `admin_menu` VALUES ('7', '/admin/user/role', 'Role', '角色配置', null, 'user/Role', '3');
INSERT INTO `admin_menu` VALUES ('8', '/admin/content/book', 'BookManagement', '图书管理', null, 'content/BookManagement', '4');
INSERT INTO `admin_menu` VALUES ('9', '/admin/content/banner', 'BannerManagement', '广告管理', null, 'content/BannerManagement', '4');
INSERT INTO `admin_menu` VALUES ('10', '/admin/content/article', 'ArticleManagement', '文章管理', null, 'content/ArticleManagement', '4');

UPDATE `simpleblog`.`user` SET `name` = 'chen', `enabled` = '1' WHERE (`id` = '110');
SELECT * FROM simpleblog.admin_user_role;
UPDATE `simpleblog`.`admin_user_role` SET `rid` = '1' WHERE (`id` = '40');