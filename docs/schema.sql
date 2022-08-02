drop table if exists `cloud_conf`;
CREATE TABLE `cloud_conf` (
   `pk` int(10) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `name` varchar(50) NOT NULL COMMENT '供应商名称',
   `provider` varchar(50) NOT NULL COMMENT '供应商标识',
   `region` varchar(50) NOT NULL COMMENT '区域',
   `access_key` varchar(50) NOT NULL COMMENT 'Access Key',
   `secret_key` varchar(50) DEFAULT NULL COMMENT 'Secret Access Key',
   `enable` tinyint(1) DEFAULT 1 NULL COMMENT '是否启用',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '云厂商配置';

drop table if exists `h_cloud_ecs`;
CREATE TABLE `h_cloud_ecs` (
    `pk` int(10) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` varchar(50) NOT NULL COMMENT 'ID',
    `name` varchar(50) DEFAULT NULL COMMENT '名称',
    `availability_zone` varchar(20) DEFAULT NULL COMMENT '可用区',
    `status` varchar(20) DEFAULT NULL COMMENT '状态',
    `flavor_name` varchar(20) DEFAULT NULL COMMENT '规格名称',
    `flavor_vcpus` varchar(10) DEFAULT NULL COMMENT 'cpu核数',
    `flavor_ram` varchar(20) DEFAULT NULL COMMENT '内存',
    `os_type` varchar(50) DEFAULT NULL COMMENT '操作系统类型',
    `image_name` varchar(200) DEFAULT NULL COMMENT '镜像名称',
    `private_ip` varchar(50) DEFAULT NULL COMMENT '内网IP',
    `public_ip` varchar(50) DEFAULT NULL COMMENT '公网IP',
    `charge_mode` varchar(50) DEFAULT NULL COMMENT '计费模式',
    `order_id` varchar(100) DEFAULT NULL COMMENT '订单ID',
    `cost_tag` varchar(50) DEFAULT NULL COMMENT '成本标签',
    `department` varchar(50) DEFAULT NULL COMMENT '所属部门',
    `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
    `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
    `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
    `auto_terminate_time` varchar(50) DEFAULT NULL COMMENT '自动释放时间',
    `description` varchar(100) DEFAULT NULL COMMENT '描述',
    `stat_date` varchar(50) DEFAULT NULL COMMENT '统计日期',
    PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '华为云-弹性云服务器';

drop table if exists `h_cloud_rds`;
CREATE TABLE `h_cloud_rds` (
    `pk` int(10) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` varchar(50) NOT NULL COMMENT 'ID',
    `name` varchar(50) DEFAULT NULL COMMENT '名称',
    `region` varchar(20) DEFAULT NULL COMMENT '可用区',
    `status` varchar(20) DEFAULT NULL COMMENT '状态',
    `flavor_ref` varchar(50) DEFAULT NULL COMMENT '规格名称',
    `cpu` varchar(10) DEFAULT NULL COMMENT 'cpu核数',
    `mem` varchar(20) DEFAULT NULL COMMENT '内存',
    `type` varchar(50) DEFAULT NULL COMMENT '类型',
    `version` varchar(200) DEFAULT NULL COMMENT '版本',
    `private_ip` varchar(50) DEFAULT NULL COMMENT '内网IP',
    `public_ip` varchar(50) DEFAULT NULL COMMENT '公网IP',
    `port` int(10) DEFAULT NULL COMMENT '端口',
    `charge_mode` varchar(50) DEFAULT NULL COMMENT '计费模式',
    `order_id` varchar(100) DEFAULT NULL COMMENT '订单ID',
    `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
    `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
    `expiration_time` varchar(50) DEFAULT NULL COMMENT '过期时间',
    `cost_tag` varchar(50) DEFAULT NULL COMMENT '成本标签',
    `department` varchar(50) DEFAULT NULL COMMENT '所属部门',
    `stat_date` varchar(50) DEFAULT NULL COMMENT '统计日期',
    PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '华为云-云数据库';

drop table if exists `h_cloud_dcs`;
CREATE TABLE `h_cloud_dcs` (
   `pk` int(10) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `id` varchar(50) NOT NULL COMMENT 'ID',
   `name` varchar(50) DEFAULT NULL COMMENT '名称',
   `region` varchar(20) DEFAULT NULL COMMENT '可用区',
   `status` varchar(20) DEFAULT NULL COMMENT '状态',
   `flavor_ref` varchar(50) DEFAULT NULL COMMENT '规格名称',
   `cpu` varchar(10) DEFAULT NULL COMMENT 'cpu核数',
   `mem` varchar(20) DEFAULT NULL COMMENT '内存',
   `type` varchar(50) DEFAULT NULL COMMENT '类型',
   `version` varchar(200) DEFAULT NULL COMMENT '版本',
   `private_ip` varchar(50) DEFAULT NULL COMMENT '内网IP',
   `public_ip` varchar(50) DEFAULT NULL COMMENT '公网IP',
   `port` int(10) DEFAULT NULL COMMENT '端口',
   `charge_mode` varchar(50) DEFAULT NULL COMMENT '计费模式',
   `order_id` varchar(100) DEFAULT NULL COMMENT '订单ID',
   `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
   `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
   `expiration_time` varchar(50) DEFAULT NULL COMMENT '过期时间',
   `cost_tag` varchar(50) DEFAULT NULL COMMENT '成本标签',
   `department` varchar(50) DEFAULT NULL COMMENT '所属部门',
   `stat_date` varchar(50) DEFAULT NULL COMMENT '统计日期',
   PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '华为云-云数据库';
