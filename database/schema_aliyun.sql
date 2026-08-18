-- 阿里云资源统计 - 建表 DDL
-- 生成日期: 2026-06-12

-- ==================== 核心资源 ====================

-- ECS 云服务器
CREATE TABLE IF NOT EXISTS `a_cloud_ecs` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `instance_type` varchar(255) DEFAULT NULL,
  `cpu` int DEFAULT NULL,
  `memory` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `image_id` varchar(255) DEFAULT NULL,
  `osname` varchar(255) DEFAULT NULL,
  `ostype` varchar(255) DEFAULT NULL,
  `instance_charge_type` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `expired_time` varchar(255) DEFAULT NULL,
  `internet_charge_type` varchar(255) DEFAULT NULL,
  `internet_max_bandwidth_out` int DEFAULT NULL,
  `network_type` varchar(255) DEFAULT NULL,
  `key_pair_name` varchar(255) DEFAULT NULL,
  `resource_group_id` varchar(255) DEFAULT NULL,
  `security_group_ids` json DEFAULT NULL,
  `public_ip_address` json DEFAULT NULL,
  `inner_ip_address` json DEFAULT NULL,
  `vpc_attributes` json DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- RDS 关系型数据库
CREATE TABLE IF NOT EXISTS `a_cloud_rds` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `dbinstance_id` varchar(255) DEFAULT NULL,
  `dbinstance_description` varchar(255) DEFAULT NULL,
  `dbinstance_type` varchar(255) DEFAULT NULL,
  `dbinstance_status` varchar(255) DEFAULT NULL,
  `engine` varchar(255) DEFAULT NULL,
  `engine_version` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `dbinstance_storage` int DEFAULT NULL,
  `dbinstance_class` varchar(255) DEFAULT NULL,
  `pay_type` varchar(255) DEFAULT NULL,
  `instance_network_type` varchar(255) DEFAULT NULL,
  `connection_mode` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `expire_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Redis 缓存
CREATE TABLE IF NOT EXISTS `a_cloud_redis` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `instance_status` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `capacity` bigint DEFAULT NULL,
  `instance_class` varchar(255) DEFAULT NULL,
  `engine_version` varchar(255) DEFAULT NULL,
  `network_type` varchar(255) DEFAULT NULL,
  `charge_type` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `node_type` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- VPC 专有网络
CREATE TABLE IF NOT EXISTS `a_cloud_vpc` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `vpc_id` varchar(255) DEFAULT NULL,
  `vpc_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `cidr_block` varchar(255) DEFAULT NULL,
  `vrouter_id` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- SLB 负载均衡
CREATE TABLE IF NOT EXISTS `a_cloud_slb` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `load_balancer_id` varchar(255) DEFAULT NULL,
  `load_balancer_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `address_type` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `master_zone_id` varchar(255) DEFAULT NULL,
  `slave_zone_id` varchar(255) DEFAULT NULL,
  `load_balancer_status` varchar(255) DEFAULT NULL,
  `internet_charge_type` varchar(255) DEFAULT NULL,
  `network_type` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `pay_type` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- OSS 对象存储
CREATE TABLE IF NOT EXISTS `a_cloud_oss` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `name` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `creation_date` varchar(255) DEFAULT NULL,
  `storage_class` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `extranet_endpoint` varchar(255) DEFAULT NULL,
  `intranet_endpoint` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- NAT 网关
CREATE TABLE IF NOT EXISTS `a_cloud_nat_gateway` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `nat_gateway_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `vpc_id` varchar(255) DEFAULT NULL,
  `instance_charge_type` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `expired_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 安全组
CREATE TABLE IF NOT EXISTS `a_cloud_security_group` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `security_group_id` varchar(255) DEFAULT NULL,
  `security_group_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `vpc_id` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `security_group_type` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- EIP 弹性IP
CREATE TABLE IF NOT EXISTS `a_cloud_eip` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `allocation_id` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `bandwidth` varchar(255) DEFAULT NULL,
  `internet_charge_type` varchar(255) DEFAULT NULL,
  `instance_type` varchar(255) DEFAULT NULL,
  `instance_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `allocation_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== 扩展资源 ====================

-- CDN
CREATE TABLE IF NOT EXISTS `a_cloud_cdn` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `domain_name` varchar(255) DEFAULT NULL,
  `cname` varchar(255) DEFAULT NULL,
  `domain_status` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `cdn_type` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `ssl_protocol` varchar(255) DEFAULT NULL,
  `sources` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- WAF Web应用防火墙
CREATE TABLE IF NOT EXISTS `a_cloud_waf` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `pay_type` int DEFAULT NULL,
  `end_date` bigint DEFAULT NULL,
  `remain_day` int DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `subscription_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- KMS 密钥管理
CREATE TABLE IF NOT EXISTS `a_cloud_kms` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `key_id` varchar(255) DEFAULT NULL,
  `key_state` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `key_spec` varchar(255) DEFAULT NULL,
  `creation_date` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `delete_date` varchar(255) DEFAULT NULL,
  `material_expire_time` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ACK 容器服务
CREATE TABLE IF NOT EXISTS `a_cloud_ack` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `cluster_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `cluster_type` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `current_version` varchar(255) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL,
  `updated` varchar(255) DEFAULT NULL,
  `master_url` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- SLS 日志服务
CREATE TABLE IF NOT EXISTS `a_cloud_sls` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `project_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `last_modify_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- SMS 短信签名
CREATE TABLE IF NOT EXISTS `a_cloud_sms` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `sign_name` varchar(255) DEFAULT NULL,
  `audit_status` varchar(255) DEFAULT NULL,
  `create_date` varchar(255) DEFAULT NULL,
  `business_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== 第二批扩展资源 ====================

-- MongoDB 文档数据库
CREATE TABLE IF NOT EXISTS `a_cloud_mongodb` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `dbinstance_id` varchar(255) DEFAULT NULL,
  `dbinstance_description` varchar(255) DEFAULT NULL,
  `dbinstance_type` varchar(255) DEFAULT NULL,
  `dbinstance_status` varchar(255) DEFAULT NULL,
  `engine` varchar(255) DEFAULT NULL,
  `engine_version` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `dbinstance_storage` int DEFAULT NULL,
  `dbinstance_class` varchar(255) DEFAULT NULL,
  `charge_type` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `expire_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Kafka 消息队列
CREATE TABLE IF NOT EXISTS `a_cloud_kafka` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `service_status` varchar(255) DEFAULT NULL,
  `msg_retain` varchar(255) DEFAULT NULL,
  `topic_num_limit` int DEFAULT NULL,
  `topic_num_of_instance` int DEFAULT NULL,
  `paid_type` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `expired_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- RocketMQ 消息队列
CREATE TABLE IF NOT EXISTS `a_cloud_rocketmq` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `instance_status` varchar(255) DEFAULT NULL,
  `instance_type` int DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `independent_naming` tinyint(1) DEFAULT NULL,
  `topic_capacity` int DEFAULT NULL,
  `group_count` int DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 云硬盘 Disk
CREATE TABLE IF NOT EXISTS `a_cloud_disk` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `disk_id` varchar(255) DEFAULT NULL,
  `size` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `disk_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `instance_id` varchar(255) DEFAULT NULL,
  `encrypted` tinyint(1) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Elasticsearch 搜索分析
CREATE TABLE IF NOT EXISTS `a_cloud_elasticsearch` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `domain` varchar(255) DEFAULT NULL,
  `port` int DEFAULT NULL,
  `es_version` varchar(255) DEFAULT NULL,
  `node_amount` int DEFAULT NULL,
  `data_node_amount` int DEFAULT NULL,
  `data_node_spec` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 函数计算 FC
CREATE TABLE IF NOT EXISTS `a_cloud_fc` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `function_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `runtime` varchar(255) DEFAULT NULL,
  `handler` varchar(255) DEFAULT NULL,
  `timeout` int DEFAULT NULL,
  `memory_size` int DEFAULT NULL,
  `code_size` bigint DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `last_modified_time` varchar(255) DEFAULT NULL,
  `service_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 云监控 CMS
CREATE TABLE IF NOT EXISTS `a_cloud_cms` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `group_id` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `contact_groups` int DEFAULT NULL,
  `template_ids` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `service_id` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文件存储 NAS
CREATE TABLE IF NOT EXISTS `a_cloud_nas` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `file_system_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `storage_type` varchar(255) DEFAULT NULL,
  `metered_size` bigint DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `mount_target_count` int DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 弹性伸缩 ESS
CREATE TABLE IF NOT EXISTS `a_cloud_ess` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `scaling_group_id` varchar(255) DEFAULT NULL,
  `scaling_group_name` varchar(255) DEFAULT NULL,
  `active_scaling_configuration_id` varchar(255) DEFAULT NULL,
  `min_size` int DEFAULT NULL,
  `max_size` int DEFAULT NULL,
  `default_cooldown` int DEFAULT NULL,
  `total_capacity` int DEFAULT NULL,
  `active_capacity` int DEFAULT NULL,
  `lifecycle_state` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 主机安全 HSS
CREATE TABLE IF NOT EXISTS `a_cloud_hss` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `uuid` varchar(255) DEFAULT NULL,
  `host_name` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `os_name` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `auth_version` int DEFAULT NULL,
  `vul_status` varchar(255) DEFAULT NULL,
  `health_check_status` varchar(255) DEFAULT NULL,
  `asset_type` varchar(255) DEFAULT NULL,
  `instance_id` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 云审计 ActionTrail
CREATE TABLE IF NOT EXISTS `a_cloud_action_trail` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `trail_name` varchar(255) DEFAULT NULL,
  `home_region` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `sls_project_arn` varchar(255) DEFAULT NULL,
  `sls_write_role_arn` varchar(255) DEFAULT NULL,
  `oss_bucket_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- API 网关
CREATE TABLE IF NOT EXISTS `a_cloud_api_gateway` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `api_group_id` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `sub_domain` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `base_path` varchar(255) DEFAULT NULL,
  `created_time` varchar(255) DEFAULT NULL,
  `modified_time` varchar(255) DEFAULT NULL,
  `billing_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 物联网 IoT
CREATE TABLE IF NOT EXISTS `a_cloud_iot` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `product_key` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `node_type` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 视频直播 Live
CREATE TABLE IF NOT EXISTS `a_cloud_live` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `domain_name` varchar(255) DEFAULT NULL,
  `domain_status` varchar(255) DEFAULT NULL,
  `cname` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `live_domain_type` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `ssl_protocol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 大数据 E-MapReduce
CREATE TABLE IF NOT EXISTS `a_cloud_emr` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `cluster_type` varchar(255) DEFAULT NULL,
  `charge_type` varchar(255) DEFAULT NULL,
  `running_time` bigint DEFAULT NULL,
  `master_node_total` int DEFAULT NULL,
  `core_node_total` int DEFAULT NULL,
  `software_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== 第三批扩展资源 ====================

-- 视频点播 VOD
CREATE TABLE IF NOT EXISTS `a_cloud_vod` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `video_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `cover_url` varchar(512) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `modify_time` varchar(255) DEFAULT NULL,
  `storage_location` varchar(255) DEFAULT NULL,
  `cate_id` int DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 容器镜像 ACR
CREATE TABLE IF NOT EXISTS `a_cloud_acr` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `instance_specification` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `modified_time` varchar(255) DEFAULT NULL,
  `instance_type` varchar(255) DEFAULT NULL,
  `namespace_count` int DEFAULT NULL,
  `repo_count` int DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- DDoS 高防
CREATE TABLE IF NOT EXISTS `a_cloud_ddos` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `ip_type` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `expire_time` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `instance_type` varchar(255) DEFAULT NULL,
  `bandwidth` bigint DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- SSL 证书
CREATE TABLE IF NOT EXISTS `a_cloud_ssl` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `cert_id` varchar(255) DEFAULT NULL,
  `cert_name` varchar(255) DEFAULT NULL,
  `issuer` varchar(255) DEFAULT NULL,
  `fingerprint` varchar(255) DEFAULT NULL,
  `valid_start_time` varchar(255) DEFAULT NULL,
  `valid_end_time` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `sans` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 云防火墙
CREATE TABLE IF NOT EXISTS `a_cloud_cloud_firewall` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `expire_time` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `instance_type` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据安全中心 DSC
CREATE TABLE IF NOT EXISTS `a_cloud_dsc` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `expire_time` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `edition` varchar(255) DEFAULT NULL,
  `engine_num` int DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- PolarDB 云原生数据库
CREATE TABLE IF NOT EXISTS `a_cloud_polar_db` (
  `pk` bigint NOT NULL AUTO_INCREMENT,
  `conf_name` varchar(255) DEFAULT NULL,
  `conf_provider` varchar(255) DEFAULT NULL,
  `conf_region` varchar(255) DEFAULT NULL,
  `stat_time` bigint DEFAULT NULL,
  `stat_date` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT 0,
  `dbcluster_id` varchar(255) DEFAULT NULL,
  `dbcluster_description` varchar(255) DEFAULT NULL,
  `dbcluster_status` varchar(255) DEFAULT NULL,
  `dbcluster_network_type` varchar(255) DEFAULT NULL,
  `engine` varchar(255) DEFAULT NULL,
  `engine_version` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `zone_id` varchar(255) DEFAULT NULL,
  `pay_type` varchar(255) DEFAULT NULL,
  `dbnode_class` varchar(255) DEFAULT NULL,
  `dbnode_number` int DEFAULT NULL,
  `dbnode_storage` bigint DEFAULT NULL,
  `creation_time` varchar(255) DEFAULT NULL,
  `expire_time` varchar(255) DEFAULT NULL,
  `tags` json DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
