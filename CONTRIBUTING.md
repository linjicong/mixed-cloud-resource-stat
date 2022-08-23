完整流程
fork本项目；
克隆(clone)你 fork 的项目到本地；
新建分支(branch)并检出(checkout)新分支；
添加本项目到你的本地 git 仓库作为上游(upstream)；
进行修改，若你的修改包含方法或函数的增减，请记得修改单元测试文件；
变基（衍合 rebase）你的分支到上游 master 分支；
push 你的本地仓库到 GitHub；
提交 pull request；
等待 CI 验证（若不通过则重复 5~7，GitHub 会自动更新你的 pull request）；
等待管理员处理，并及时 rebase 你的分支到上游 master 分支（若上游 master 分支有修改）。
若有必要，可以 git push -f 强行推送 rebase 后的分支到自己的 fork

绝对不可以使用 git push -f 强行推送修改到上游
