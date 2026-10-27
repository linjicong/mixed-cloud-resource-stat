# API 文档同步源（Apifox 定时导入）

本目录是 Apifox 接口文档的**同步源**，请勿随意移动或改名以下文件（Apifox 按固定 URL/路径拉取）：

| 文件 | 用途 |
|---|---|
| `openapi.yaml` | 全量接口 OpenAPI 3.0.1 规范（当前 6 模块 / 14 路径 / 17 操作 / 25 模型） |
| `apifox-sync-log.md` | 本地脚本手动同步时的结果日志 |

## 一、Apifox 定时导入配置（主链路，只做一次）

在 Apifox 客户端（需 **管理员权限**，版本建议 ≥ 2.7.29）：

**入口：`项目设置` → `导入数据（自动同步）` → 切换到 `定时导入` 选项卡 → 新建数据源**

- 数据类型：OpenAPI / Swagger
- 数据源 URL（已验证 Apifox 侧可正常读取，返回完整 YAML）：
  `https://raw.githubusercontent.com/linjicong/mixed-cloud-resource-stat/master/docs/api/openapi.yaml`
- 同步频率：按需（如每 6 小时 / 每天）
- 冲突处理：**智能合并**（保留接口内手工补充的中文名、备注、Mock 规则、响应示例）
- 资源清理：**不删除**（避免误删项目内其它手工维护的接口）
- 目标目录：可选同步到指定目录；迭代分支默认主分支
- 保存后先点一次 **立即导入** 验证链路，之后即自动定时执行

> 若 `raw.githubusercontent.com` 访问不稳定，可改用 CDN 镜像地址（内容等价）：
> `https://cdn.jsdelivr.net/gh/linjicong/mixed-cloud-resource-stat@master/docs/api/openapi.yaml`

> 备选：Apifox 也支持 `项目设置 → 绑定数据源 → Git 仓库` 直接绑 GitHub 仓库与文件路径，
> 无需拼 raw URL，效果相同（适合仓库为私有、需要授权的场景）。

## 二、日常新接口如何同步

配好定时导入后，**只需让 `docs/api/openapi.yaml` 保持更新并提交到 master**，Apifox 会按周期自动拉取，无需任何本地操作。

更新 yaml 的两种方式：

1. **手动**：新增/修改 Controller 后同步维护本文件。
   ⚠️ 坑：YAML flow mapping（`{ }` 花写法）中含冒号/空格的中文值必须加单引号，否则 Apifox 报 `422 Input is not a valid OpenAPI document`。
2. **自动**：本工作区已配置每日 10:00 的 WorkBuddy 自动化「维护 OpenAPI 同步源」，它会扫描 Controller 源码、比对差异、更新本文件并 push（无变化则跳过）。

## 三、兜底：本地手动推送（可选）

不等定时任务、想立即生效时，在仓库根目录执行：

```powershell
.\scripts\apifox_sync.ps1                    # token 自动读 scripts\.apifox-token
.\scripts\apifox_sync.ps1 -Token "afxp_xxx"  # 或临时指定
```

- 脚本走 Apifox 开放 API 直接导入（`AUTO_MERGE`，不删存量），结果追加到 `apifox-sync-log.md`
- `scripts\.apifox-token` 为本地凭据，已加入 `.gitignore`，不会入库
- token 获取：Apifox → 头像 → 账号设置 → API Access Tokens（需对应项目权限）

## 四、提交前校验

```bash
python -c "import yaml; yaml.safe_load(open('docs/api/openapi.yaml', encoding='utf-8')); print('OK')"
```
