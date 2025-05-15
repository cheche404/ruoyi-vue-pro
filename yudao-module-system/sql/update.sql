ALTER TABLE `system_users`
    ADD COLUMN `cn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '通用名称',
    ADD COLUMN `sn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '姓氏',
    ADD COLUMN `display_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '显示名称',
    ADD COLUMN `user_principal_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户主体名称',
    ADD COLUMN `given_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '名字';

ALTER TABLE `system_users`
    ADD COLUMN `user_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户类型';

alter table system_users
    modify mobile varchar(64) default '' null comment '手机号码';
alter table system_users
    modify nickname varchar(64) not null comment '用户昵称';
alter table system_users
    modify username varchar(64) not null comment '用户账号';

alter table system_users
    modify cn varchar(64) default '' null comment '通用名称';

alter table system_users
    modify sn varchar(64) default '' null comment '姓氏';

alter table system_users
    modify display_name varchar(64) default '' null comment '显示名称';

alter table system_users
    modify user_principal_name varchar(64) default '' null comment '用户主体名称';

alter table system_users
    modify given_name varchar(64) default '' null comment '名字';



