/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : ssm

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 11/08/2022 11:04:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `book_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `price` int NULL DEFAULT NULL COMMENT '价格',
  `stock` int UNSIGNED NULL DEFAULT NULL COMMENT '库存（无符号）',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, '斗破苍 穹', 80, 97);
INSERT INTO `t_book` VALUES (2, '斗罗大陆', 50, 100);

SET FOREIGN_KEY_CHECKS = 1;
