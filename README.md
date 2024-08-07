<p align="center">
    <img src="https://img.shields.io/badge/Cube_Cloud-1.0.0-green">
    <img src="https://img.shields.io/badge/Spring_Cloud-2023.0.1-blue">
    <img src="https://img.shields.io/badge/Spring_Cloud_Alibaba-2023.0.1.0-blue">
    <img src="https://img.shields.io/badge/license-MIT-green">
</p>

## 提示
因时间问题，前端正在开发中，预计将于2024年9月开放仓库

## 简介
- 基于Spring Boot、Spring Cloud、Spring Cloud Alibaba以及Spring Authorization Server的快速开发平台
- 全平台基于微服务架构
- 提供RBAC权限管理，支持多租户和数据行级权限
- 通过Spring Authorization Server提供基于OAuth2.0的单点登录
- 提供大量的公共类库和工具的二次封装，简化开发流程

## 核心服务
### Cube-Cloud-Gateway(网关)
- 基于Spring Cloud Gateway，提供网关服务
- 实现基于anji-plus登录验证码
- 提供通用验证码处理过滤器，支持通过配置对指定的接口启用验证码校验

### Cube-Cloud-ACS(认证中心服务)
- 基于Spring Authorization Server实现OAuth2.0协议
- 支持OAuth2.0协议的授权码模式、客户端模式
- 支持基于OAuth2.0扩展的密码模式、手机验证码模式

### Cube-Cloud-SAS(系统权限服务)
- 用户管理：提供对系统用户的配置
- 角色管理：提供对角色、角色功能权限以及角色数据权限的配置
- 菜单管理：提供对系统菜单和按钮的配置，支持配置权限标识
- 部门管理：提供部门配置，支持树级结构，同时数据权限基于部门
- 岗位管理：提供对用户岗位的管理
- 租户管理：用于对多租户的支持，支持对租户进行时间和功能授权

### Cube-Cloud-Log(日志服务)
- 支持对用户操作日志的记录
- 支持用户登录、注销操作的日志记录

### Cube-Cloud-Config(系统配置服务)
- 客户端管理：提供对OAuth2.0的Client管理
- 配置组管理：提供对配置组的管理
- 配置管理：提供对系统配置的管理，支持组

### Cube-Cloud-Sequence(统一发号器服务)
- 支持基于Redis/DB的自增序列
- 支持基于优化后的雪花算法
- 支持基于个性化规则定制的序列

## 快速开始

### 核心依赖
| 依赖                          | 版本         |
|-----------------------------|------------|
| Spring Boot                 | 3.3.0      |
| Spring Cloud                | 2023.0.1   |
| Spring Cloud Alibaba        | 2023.0.1.0 |
| Spring Authorization Server | 1.3.0      |
| Mybatis Plus                | 3.5.5      |

### 本地运行及调试
#### 本地运行前请自行安装以下服务
- redis
- postgresql
- nacos
- xxl-job-admin <img src="https://img.shields.io/badge/optional-yellow">

1.下载代码
```
git clone https://github.com/toskeyfine/cube-cloud.git
```
2.创建数据库
```
分别创建cube-cloud-sas、cube-cloud-log、cube-cloud-config库
```
3.导入数据库脚本
```
数据库脚本路径：
support/db/cube_cloud-sas.sql
support/db/cube_cloud-log.sql
support/db/cube_cloud-config.sql
```
3.导入nacos配置
```
nacos配置路径：support/nacos/cube_config.zip
```
4.使用IDE打开项目工程
<br>
5.分别运行cube-gateway、cube-acs、cube-sas、cube-log、cube-config、cube-sequence

