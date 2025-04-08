# CMDB 对象表
DROP TABLE IF EXISTS `cmdb_model`;
CREATE TABLE `cmdb_model` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '模型id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模型名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模型编码',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父模型id',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模型描述',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '模型状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='CMDB模型表';

# CMDB 对象属性表
DROP TABLE IF EXISTS `cmdb_attribute`;
CREATE TABLE `cmdb_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '属性名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '属性编码',
  `model_id` bigint NOT NULL DEFAULT 0 COMMENT '所属对象id（关联cmdb_model表的id）',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序号',
  `attr_type` tinyint NOT NULL DEFAULT 0 COMMENT '属性类型（0:字符串, 1:整数, 2:日期, 3:布尔值, 4:浮点数等）',
  `validation_rule` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '属性校验规则（如正则表达式或长度限制）',
  `is_required` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否必填（0否 1是）',
  `is_editable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否能编辑（0否 1是）',
  `is_single_line` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否单行展示（0否，一行展示两个属性；1是，单独一行展示）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '属性描述',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_model_id` (`model_id`) COMMENT '所属对象id索引'
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='CMDB对象属性表';
