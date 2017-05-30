/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : yuiz

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-05-26 22:00:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `sex` tinyint(2) DEFAULT '0' COMMENT '性别,0表示未设定,1表示男,2表示女',
  `email` varchar(40) DEFAULT '' COMMENT '电子邮箱地址',
  `telephone` varchar(15) DEFAULT NULL COMMENT '手机号',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
