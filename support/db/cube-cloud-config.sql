/*
 Navicat PostgreSQL Data Transfer

 Source Server         : 腾讯云-pg
 Source Server Type    : PostgreSQL
 Source Server Version : 150003 (150003)
 Source Host           : 101.42.231.138:5432
 Source Catalog        : cube-cloud-config
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150003 (150003)
 File Encoding         : 65001

 Date: 20/06/2024 16:59:53
*/


-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config";
CREATE TABLE "public"."sys_config" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
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
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config_group";
CREATE TABLE "public"."sys_config_group" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
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
-- Records of sys_config_group
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_oauth2_client";
CREATE TABLE "public"."sys_oauth2_client" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "client_name" varchar(20) COLLATE "pg_catalog"."default",
  "client_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "client_secret" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "scopes" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "authorized_grant_types" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "redirect_uris" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "auto_approve" bool NOT NULL DEFAULT true,
  "access_token_validity" int8 NOT NULL,
  "refresh_token_validity" int8 NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(256) COLLATE "pg_catalog"."default",
  "del_flag" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0
)
;

-- ----------------------------
-- Records of sys_oauth2_client
-- ----------------------------
INSERT INTO "public"."sys_oauth2_client" VALUES ('1', 'cube-cloud', 'cube-cloud', 'sk-3a6659a84a0a41b18a5e1ce1b188eceb', 'all', 'authorization_code,refresh_token,password,sms', 'http://127.0.0.1:8000', 't', 3600, 86400, '1', '2024-06-20 11:08:58', NULL, NULL, NULL, '0');

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "public"."sys_config" ADD CONSTRAINT "sys_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_config_group
-- ----------------------------
ALTER TABLE "public"."sys_config_group" ADD CONSTRAINT "sys_config_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_oauth2_client
-- ----------------------------
ALTER TABLE "public"."sys_oauth2_client" ADD CONSTRAINT "sys_oauth2_client_pkey" PRIMARY KEY ("id");
