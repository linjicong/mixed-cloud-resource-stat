# Apifox 全量接口同步脚本
# 用法（项目根目录）:
#   powershell -NoProfile -ExecutionPolicy Bypass -File scripts/apifox_sync.ps1
# Token 解析优先级: -Token 参数 > 环境变量 APIFOX_TOKEN > 本地文件 scripts/.apifox-token
# 说明:
#   - 读取 docs/02-涉及文档/全量接口-openapi.yaml，调用 Apifox 开放 API 导入项目
#   - 覆盖策略 AUTO_MERGE: 同名接口自动合并更新, 不删除项目内存量接口
#   - 每次同步结果追加记录到 docs/02-涉及文档/apifox-sync-log.md
param(
    [string]$Token = "",
    [string]$ProjectId = "8973200",
    [string]$SpecPath = "docs\02-涉及文档\全量接口-openapi.yaml"
)

$ErrorActionPreference = 'Stop'
$ProgressPreference = 'SilentlyContinue'

if (-not $Token) { $Token = $env:APIFOX_TOKEN }
if (-not $Token -and (Test-Path "scripts\.apifox-token")) {
    $Token = (Get-Content -Raw -Encoding UTF8 "scripts\.apifox-token").Trim()
}
if (-not $Token) {
    Write-Error "未提供 Apifox Token（-Token 参数 / 环境变量 APIFOX_TOKEN / scripts\.apifox-token 文件 三选一）"
    exit 1
}

if (-not (Test-Path $SpecPath)) {
    Write-Error "OpenAPI 规范文件不存在: $SpecPath（请在项目根目录运行）"
    exit 1
}

$spec = Get-Content -Raw -Encoding UTF8 $SpecPath
if (-not $spec -or -not $spec.Contains('openapi:')) {
    Write-Error "规范文件内容为空或缺少 openapi 声明: $SpecPath"
    exit 1
}

$payload = @{
    input   = $spec
    options = @{
        endpointOverwriteBehavior = 'AUTO_MERGE'
        schemaOverwriteBehavior   = 'AUTO_MERGE'
    }
} | ConvertTo-Json -Depth 5 -Compress

$headers = @{
    'X-Apifox-Api-Version' = '2024-03-28'
    'Authorization'         = "Bearer $Token"
}
$url = "https://api.apifox.com/v1/projects/$ProjectId/import-openapi?locale=zh-CN"

Write-Host "同步目标: 项目 $ProjectId"
Write-Host "规范文件: $SpecPath ($($spec.Length) 字符)"

try {
    $resp = Invoke-WebRequest -Uri $url -Method Post -Headers $headers `
        -ContentType 'application/json; charset=utf-8' `
        -Body ([System.Text.Encoding]::UTF8.GetBytes($payload)) -TimeoutSec 120
    $body = $resp.Content | ConvertFrom-Json
    $c = $body.data.counters
    if ($c.endpointFailed -eq 0 -and $c.schemaFailed -eq 0) {
        Write-Host "[OK] 同步成功: 接口 新建$($c.endpointCreated)/更新$($c.endpointUpdated)/忽略$($c.endpointIgnored), 模型 新建$($c.schemaCreated)/更新$($c.schemaUpdated)"
        $status = '成功'
    } else {
        Write-Host "[WARN] 部分失败: endpointFailed=$($c.endpointFailed) schemaFailed=$($c.schemaFailed)"
        $status = '部分失败'
    }
    $detail = "endpoint C/U/I=$($c.endpointCreated)/$($c.endpointUpdated)/$($c.endpointIgnored), schema C/U/I=$($c.schemaCreated)/$($c.schemaUpdated)/$($c.schemaIgnored)"
} catch {
    Write-Host "[FAIL] 同步失败: $($_.Exception.Message)"
    if ($_.ErrorDetails.Message) { Write-Host "响应: $($_.ErrorDetails.Message)" }
    $status = '失败'
    $detail = $_.Exception.Message
}

$logPath = "docs\02-涉及文档\apifox-sync-log.md"
$logHeader = "# Apifox 接口同步日志`n`n| 时间 | 项目 | 状态 | 详情 |`n|---|---|---|---|`n"
if (-not (Test-Path $logPath)) { Set-Content -Path $logPath -Value $logHeader -Encoding UTF8 }
Add-Content -Path $logPath -Value "| $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') | $ProjectId | $status | $detail |" -Encoding UTF8
Write-Host "日志已追加: $logPath"

if ($status -ne '成功') { exit 2 }
