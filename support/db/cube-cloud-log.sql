/*
 Navicat PostgreSQL Data Transfer

 Source Server         : 腾讯云-pg
 Source Server Type    : PostgreSQL
 Source Server Version : 150003 (150003)
 Source Host           : 101.42.231.138:5432
 Source Catalog        : cube-cloud-log
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150003 (150003)
 File Encoding         : 65001

 Date: 20/06/2024 17:00:11
*/


-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS "public"."log_login";
CREATE TABLE "public"."log_login" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "type" char(1) COLLATE "pg_catalog"."default" NOT NULL,
  "grant_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "client_ip" varchar(30) COLLATE "pg_catalog"."default",
  "result" char(1) COLLATE "pg_catalog"."default" NOT NULL,
  "reason" varchar(256) COLLATE "pg_catalog"."default",
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
-- Records of log_login
-- ----------------------------

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS "public"."log_operation";
CREATE TABLE "public"."log_operation" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "title" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(100) COLLATE "pg_catalog"."default",
  "module" varchar(20) COLLATE "pg_catalog"."default",
  "log_type" char(1) COLLATE "pg_catalog"."default" NOT NULL,
  "request_remote_host" varchar(30) COLLATE "pg_catalog"."default",
  "request_user_agent" varchar(20) COLLATE "pg_catalog"."default",
  "request_uri" varchar(100) COLLATE "pg_catalog"."default",
  "request_method" varchar(10) COLLATE "pg_catalog"."default",
  "request_params" text COLLATE "pg_catalog"."default",
  "execute_time" int8,
  "ex_msg" text COLLATE "pg_catalog"."default",
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
-- Records of log_operation
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table log_login
-- ----------------------------
ALTER TABLE "public"."log_login" ADD CONSTRAINT "log_login_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table log_operation
-- ----------------------------
ALTER TABLE "public"."log_operation" ADD CONSTRAINT "log_operation_pkey" PRIMARY KEY ("id");
