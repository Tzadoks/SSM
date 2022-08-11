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

 Date: 11/08/2022 11:04:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_emp
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp`  (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`emp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_emp
-- ----------------------------
INSERT INTO `t_emp` VALUES (1, 'Marcus', 59, '男', 'dim@mail.com');
INSERT INTO `t_emp` VALUES (2, 'Helen', 55, '女', 'davish@gmail.com');
INSERT INTO `t_emp` VALUES (3, 'Rhonda', 45, '女', 'rthom@mail.com');
INSERT INTO `t_emp` VALUES (4, 'Josephine', 5, '女', 'josephinefo1993@gmail.com');
INSERT INTO `t_emp` VALUES (5, 'Angela', 43, '女', 'angelahol@gmail.com');
INSERT INTO `t_emp` VALUES (6, 'Kathleen', 30, '女', 'kathleenco@mail.com');
INSERT INTO `t_emp` VALUES (7, 'Jose', 37, '男', 'jjor1954@gmail.com');
INSERT INTO `t_emp` VALUES (8, 'Larry', 48, '男', 'larrreyes@gmail.com');
INSERT INTO `t_emp` VALUES (9, 'Anthony', 23, '男', 'gutierrezanthony@gmail.com');
INSERT INTO `t_emp` VALUES (10, 'Andrew', 33, '男', 'elliand8@gmail.com');
INSERT INTO `t_emp` VALUES (11, 'Herbert', 57, '男', 'spencerherb@mail.com');
INSERT INTO `t_emp` VALUES (12, 'Ethel', 21, '女', 'johnsoneth@gmail.com');
INSERT INTO `t_emp` VALUES (13, 'Charlotte', 49, '女', 'moore7@gmail.com');
INSERT INTO `t_emp` VALUES (14, 'Susan', 3, '女', 'gardsu@gmail.com');
INSERT INTO `t_emp` VALUES (15, 'Russell', 59, '男', 'russelln@gmail.com');
INSERT INTO `t_emp` VALUES (16, 'Alfred', 57, '男', 'salazaralfred@mail.com');
INSERT INTO `t_emp` VALUES (17, 'Theodore', 2, '男', 'reyes6@gmail.com');
INSERT INTO `t_emp` VALUES (18, 'Alfred', 25, '男', 'alfredmendo@gmail.com');
INSERT INTO `t_emp` VALUES (19, 'Eva', 14, '女', 'evar77@gmail.com');
INSERT INTO `t_emp` VALUES (20, 'Clarence', 12, '男', 'clboyd@gmail.com');
INSERT INTO `t_emp` VALUES (21, 'Jennifer', 14, '女', 'simjen@gmail.com');
INSERT INTO `t_emp` VALUES (22, 'Marvin', 36, '男', 'marjim@gmail.com');
INSERT INTO `t_emp` VALUES (23, 'Cindy', 13, '女', 'alvarezcindy@gmail.com');
INSERT INTO `t_emp` VALUES (24, 'Marie', 25, '女', 'florm@mail.com');
INSERT INTO `t_emp` VALUES (25, 'Fred', 36, '男', 'fdaniels@gmail.com');
INSERT INTO `t_emp` VALUES (26, 'Steven', 36, '男', 'ls115@mail.com');
INSERT INTO `t_emp` VALUES (27, 'Sean', 38, '男', 'gibssean6@gmail.com');
INSERT INTO `t_emp` VALUES (28, 'Eleanor', 44, '女', 'scotteleanor@gmail.com');
INSERT INTO `t_emp` VALUES (29, 'Elizabeth', 33, '女', 'elizabeth63@mail.com');
INSERT INTO `t_emp` VALUES (30, 'Judith', 44, '女', 'adamsju@mail.com');
INSERT INTO `t_emp` VALUES (31, 'Thelma', 20, '女', 'warrenth@gmail.com');
INSERT INTO `t_emp` VALUES (32, 'Craig', 11, '男', 'frcra@gmail.com');
INSERT INTO `t_emp` VALUES (33, 'Julie', 15, '女', 'juliestewart7@gmail.com');
INSERT INTO `t_emp` VALUES (34, 'Johnny', 1, '男', 'webb914@gmail.com');
INSERT INTO `t_emp` VALUES (35, 'Elizabeth', 47, '女', 'teliz316@gmail.com');
INSERT INTO `t_emp` VALUES (36, 'Manuel', 9, '男', 'crawfman@gmail.com');
INSERT INTO `t_emp` VALUES (37, 'Randy', 52, '男', 'randyharri@gmail.com');
INSERT INTO `t_emp` VALUES (38, 'Don', 55, '男', 'robertsdon69@gmail.com');
INSERT INTO `t_emp` VALUES (39, 'Rose', 37, '女', 'rosfer@gmail.com');
INSERT INTO `t_emp` VALUES (40, 'Esther', 37, '女', 'nguyenesther55@mail.com');
INSERT INTO `t_emp` VALUES (41, 'Keith', 38, '男', 'davkeith@gmail.com');
INSERT INTO `t_emp` VALUES (42, 'Sharon', 58, '女', 'shaj@mail.com');
INSERT INTO `t_emp` VALUES (43, 'Bobby', 34, '男', 'bobbywhite9@gmail.com');
INSERT INTO `t_emp` VALUES (44, 'Ethel', 11, '女', 'forde@gmail.com');
INSERT INTO `t_emp` VALUES (45, 'Harold', 50, '男', 'haroldcook@gmail.com');
INSERT INTO `t_emp` VALUES (46, 'Andrew', 27, '男', 'weaa@gmail.com');
INSERT INTO `t_emp` VALUES (47, 'Kathryn', 1, '女', 'owkathr226@gmail.com');
INSERT INTO `t_emp` VALUES (48, 'Jason', 20, '男', 'jason913@gmail.com');
INSERT INTO `t_emp` VALUES (49, 'Philip', 45, '男', 'schphilip@gmail.com');
INSERT INTO `t_emp` VALUES (50, 'Peggy', 12, '女', 'ramos803@gmail.com');

SET FOREIGN_KEY_CHECKS = 1;
