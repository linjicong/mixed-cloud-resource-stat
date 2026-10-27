import json
import os
import urllib.request
import urllib.error

token = os.environ["APIFOX_TOKEN"]
project_id = "8973200"
spec_path = r"D:\project-java\mixed-cloud-resource-stat\docs\02-涉及文档\统一账单页-openapi.yaml"

with open(spec_path, encoding="utf-8") as f:
    spec_text = f.read()

body = {
    "input": spec_text,
    "options": {
        "endpointOverwriteBehavior": "AUTO_MERGE",
        "schemaOverwriteBehavior": "AUTO_MERGE",
    },
}

url = f"https://api.apifox.com/v1/projects/{project_id}/import-openapi?locale=zh-CN"
req = urllib.request.Request(
    url,
    data=json.dumps(body, ensure_ascii=False).encode("utf-8"),
    method="POST",
    headers={
        "X-Apifox-Api-Version": "2024-03-28",
        "Authorization": f"Bearer {token}",
        "Content-Type": "application/json",
    },
)
try:
    with urllib.request.urlopen(req, timeout=60) as resp:
        print("HTTP", resp.status)
        print(resp.read().decode("utf-8"))
except urllib.error.HTTPError as e:
    print("HTTPError", e.code)
    print(e.read().decode("utf-8", errors="replace")[:2000])
