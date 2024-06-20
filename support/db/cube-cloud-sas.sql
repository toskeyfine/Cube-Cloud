/*
 Navicat PostgreSQL Data Transfer

 Source Server         : 腾讯云-pg
 Source Server Type    : PostgreSQL
 Source Server Version : 150003 (150003)
 Source Host           : 101.42.231.138:5432
 Source Catalog        : cube-cloud-sas
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150003 (150003)
 File Encoding         : 65001

 Date: 20/06/2024 17:00:23
*/


-- ----------------------------
-- Table structure for rel_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."rel_role_menu";
CREATE TABLE "public"."rel_role_menu" (
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of rel_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."rel_user_role";
CREATE TABLE "public"."rel_user_role" (
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of rel_user_role
-- ----------------------------
INSERT INTO "public"."rel_user_role" VALUES ('1', '1');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept";
CREATE TABLE "public"."sys_dept" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "manager" varchar(64) COLLATE "pg_catalog"."default",
  "phone" varchar(20) COLLATE "pg_catalog"."default",
  "ordered" int4,
  "status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "tenant_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(256) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0
)
;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO "public"."sys_dept" VALUES ('1', '-1', '开发部', 'development', 'toskey', '010-88888888', 1, '1', '1', '1', '2024-06-20 11:02:56', NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "path" varchar(100) COLLATE "pg_catalog"."default",
  "component" varchar(100) COLLATE "pg_catalog"."default",
  "type" char(1) COLLATE "pg_catalog"."default" NOT NULL,
  "route_type" char(1) COLLATE "pg_catalog"."default",
  "target" varchar(20) COLLATE "pg_catalog"."default",
  "cached" char(1) COLLATE "pg_catalog"."default",
  "visible" char(1) COLLATE "pg_catalog"."default",
  "perms" varchar(20) COLLATE "pg_catalog"."default",
  "ordered" int4,
  "status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(256) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0
)
;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_post";
CREATE TABLE "public"."sys_post" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "dept_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "ordered" int4,
  "status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "tenant_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0
)
;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO "public"."sys_post" VALUES ('1', '项目经理', 'pm', '1', 1, '1', '1', '1', '2024-06-20 11:02:00', NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "user_limit" int4,
  "ordered" int2,
  "status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(256) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0,
  "tenant_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "public"."sys_role" VALUES ('1', '管理员', 'admin', 100, 1, '1', '1', '2024-06-20 11:00:31', NULL, NULL, NULL, '0', '1');

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_tenant";
CREATE TABLE "public"."sys_tenant" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "type" char(1) COLLATE "pg_catalog"."default" NOT NULL,
  "register_time" timestamp(6) NOT NULL,
  "ordered" int4,
  "status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "authorized_begin_time" timestamp(6),
  "authorized_end_time" timestamp(6),
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(256) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0
)
;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO "public"."sys_tenant" VALUES ('1', 'cube-cloud', 'cube-cloud', '1', '2024-06-20 11:00:56', 1, '1', '2024-06-20 11:01:00', '2099-06-20 11:01:01', '1', '2024-06-20 11:01:26', NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "username" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(10) COLLATE "pg_catalog"."default",
  "gender" char(1) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "mobile" varchar(14) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "user_type" char(1) COLLATE "pg_catalog"."default",
  "status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "dept_id" varchar(64) COLLATE "pg_catalog"."default",
  "post_id" varchar(64) COLLATE "pg_catalog"."default",
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(256) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0,
  "tenant_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "password_strength" char(1) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "public"."sys_user" VALUES ('1', 'admin', '$2a$10$HyIMIhHrqVJF5/qTuyhgW.r2PJg1yUQBlcrB7.CXYXULvKIzWtU8G', '管理员', '1', NULL, '18888888888', 'admin@cube.com', '1', '1', '1', '1', '1', '2024-06-20 11:04:55', NULL, NULL, NULL, '0', '1', '1');

-- ----------------------------
-- Primary Key structure for table rel_role_menu
-- ----------------------------
ALTER TABLE "public"."rel_role_menu" ADD CONSTRAINT "rel_role_menu_pkey" PRIMARY KEY ("role_id", "menu_id");

-- ----------------------------
-- Primary Key structure for table rel_user_role
-- ----------------------------
ALTER TABLE "public"."rel_user_role" ADD CONSTRAINT "rel_user_role_pkey" PRIMARY KEY ("user_id", "role_id");

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "sys_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_post
-- ----------------------------
ALTER TABLE "public"."sys_post" ADD CONSTRAINT "sys_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "sys_tenant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");
