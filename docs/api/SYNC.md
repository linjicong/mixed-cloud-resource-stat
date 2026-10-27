# API 文档同步源

本目录是 Apifox 接口文档的**唯一同步源**，请勿随意移动或改名以下文件（Apifox 定时同步按固定 URL 拉取）：

| 文件 | 用途 |
|---|---|
| `openapi.yaml` | 全量接口 OpenAPI 3.0.1 规范（Apifox 定时同步数据源） |
| `apifox-sync-log.md` | 本地脚本手动同步时的结果日志 |

## Apifox 在线文档定时同步（推荐，主链路）

Apifox 项目「接口管理 → 设置模块/项目设置 → 导入数据 → 定时同步（在线文档 URL）」配置：

- 数据源 URL：
  `https://raw.githubusercontent.com/linjicong/mixed-cloud-resource-stat/master/docs/api/openapi.yaml`
- 开启定时同步后，Apifox 按所选周期自动拉取该 URL 并更新项目接口

因此**日常新接口上线只需把 `openapi.yaml` 更新并提交到 master**，Apifox 会自动同步，无需任何本地操作。

## 更新 openapi.yaml 的两种方式

1. **手动**：新增/修改 Controller 后同步更新本文件（注意：flow mapping 中含冒号的中文值必须加引号，否则 Apifox 返回 422）
2. **脚本生成**（辅助）：仓库根目录执行 `.\scripts\apifox_sync.ps1` 可直接推送本地文件到 Apifox（需 `scripts\.apifox-token` 本地凭据文件，该文件已 gitignore）

## 校验

提交前建议本地校验 YAML 合法性：

```
python -c "import yaml; yaml.safe_load(open('docs/api/openapi.yaml', encoding='utf-8'))"
```
