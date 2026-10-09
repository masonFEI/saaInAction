# saaInAction

尚硅谷 **周阳《Spring AI Alibaba（SAA）》** 课程的学习工程 —— 每个章节对应一个可独立运行的模块，配套 [笔记.md](笔记.md) 记录理论沉淀。

> 学习笔记性质，代码以「跑通知识点」为目标，不追求工程完备性。

## 技术栈

| 组件 | 版本 |
| --- | --- |
| JDK | 21 |
| Spring Boot | 3.5.5 |
| Spring AI | 1.0.0 |
| Spring AI Alibaba | 1.0.0.2 |
| 向量/记忆存储 | Redis Stack（RedisSearch） |
| 本地推理 | Ollama（`qwen3:4b`） |

模型主要走 **阿里云百炼 DashScope**（`qwen-plus` / `qwen-max` / `deepseek-v3` / `deepseek-r1`），另有文生图、文生语音与向量模型；模块 17 演示以 **OpenAI 兼容协议**接入 MiniMax 模型服务。

## 模块地图

父工程 `saaAtguiguV1` 统一管理依赖版本，各模块独立启动，端口从 8001 递增。

| 模块 | 端口 | 核心知识点 | 测试入口 |
| --- | --- | --- | --- |
| [SAA-01HelloWorld](saaAtguiguV1/SAA-01HelloWorld) | 8001 | `ChatModel` 基础调用 `call()` / `stream()` | `/hello/dochat`、`/hello/streamchat` |
| [SAA-02Ollama](saaAtguiguV1/SAA-02Ollama) | 8001 | Ollama 私有化部署，零成本调用本地模型 | `/ollama/chat` |
| [SAA-03ChatModelChatClient](saaAtguiguV1/SAA-03ChatModelChatClient) | 8003 | `ChatModel`（原子 API）vs `ChatClient`（Fluent API） | `/chatmodel/dochat`、`/chatclientV2/chatClient` |
| [SAA-04StreamingOutput](saaAtguiguV1/SAA-04StreamingOutput) | 8004 | SSE 流式输出、一套系统多模型共存（deepseek + qwen） | `/stream/chatflux1` ~ `/chatflux4` |
| [SAA-05ChatPrompt](saaAtguiguV1/SAA-05ChatPrompt) | 8005 | 提示词四大角色：System / User / Assistant / Tool | `/prompt/chat` ~ `/prompt/chat5` |
| [SAA-06PromptTemplate](saaAtguiguV1/SAA-06PromptTemplate) | 8006 | `PromptTemplate` / `SystemPromptTemplate` 占位符与外部模板文件 | `/prompttemplate/chat` ~ `/chat5` |
| [SAA-07StructuredOutput](saaAtguiguV1/SAA-07StructuredOutput) | 8007 | 结构化输出：`record` + `.entity()` 直接映射为对象 | `/structuredoutput/chat` |
| [SAA-08Persistent](saaAtguiguV1/SAA-08Persistent) | 8008 | Chat Memory 持久化：`MessageChatMemoryAdvisor` + Redis | `/chatmemory/chat` |
| [SAA-09Text2Image](saaAtguiguV1/SAA-09Text2Image) | 8009 | 文生图 `wan2.2-t2i-flash` | `/t2i/image` |
| [SAA-10Text2Voice](saaAtguiguV1/SAA-10Text2Voice) | 8010 | 文生语音 `cosyvoice-v2`（音色 longyingcui） | `/t2v/voice` |
| [SAA-11Embed2Vector](saaAtguiguV1/SAA-11Embed2Vector) | 8011 | 文本向量化 Embedding + Redis Stack 相似度检索 | `/text2embed`、`/embed2Vector/search` |
| [SAA-12RAG4AiOps](saaAtguiguV1/SAA-12RAG4AiOps) | 8012 | RAG 检索增强生成：`RetrievalAugmentationAdvisor` 运维知识库问答 | `/rag4aiops` |
| [SAA-13ToolCalling](saaAtguiguV1/SAA-13ToolCalling) | 8013 | Tool Calling / Function Calling，`@Tool` 注解方法 | `/toolcall/chat` |
| [SAA-14LocalMcpServer](saaAtguiguV1/SAA-14LocalMcpServer) | 8014 | 自建 MCP Server（以天气查询服务为例） | 启动后暴露 SSE 端点 |
| [SAA-15LocalMcpClient](saaAtguiguV1/SAA-15LocalMcpClient) | 8015 | MCP Client 调用本地 MCP Server | `/mcpclient/chat` |
| [SAA-16ClientCallBaiduMcpServer](saaAtguiguV1/SAA-16ClientCallBaiduMcpServer) | 8016 | MCP Client 调用百度地图远程 MCP Server（stdio + npx） | `/mcp/chat` |
| [SAA-17MiniMax](saaAtguiguV1/SAA-17MiniMax) | 8017 | 接入 MiniMax 模型服务：`spring-ai-starter-model-openai` 复用 OpenAI 兼容协议 | `/minimax/chat` |

## 快速开始

```bash
# 1. 编译
cd saaAtguiguV1
mvn -DskipTests clean install

# 2. 启动指定模块（以 SAA-01 为例）
mvn -pl SAA-01HelloWorld spring-boot:run
```

也可以直接在 IDEA 中打开 `saaAtguiguV1/pom.xml`，运行对应模块的 `Application` 启动类。

**各模块的测试 URL 均以注释形式写在对应 Controller 的方法上**，启动后直接访问即可，例如：

```
http://localhost:8007/structuredoutput/chat?sname=fei&email=110@qq.com
http://localhost:8012/rag4aiops?msg=00000
http://localhost:8013/toolcall/chat?msg=你是谁现在几点了
```

## 环境依赖

- JDK 21、Maven 3.9+
- **阿里云百炼 API Key**：各模块 `application.properties` 的 `spring.ai.dashscope.api-key`
- **Redis Stack**：`localhost:6379`（模块 08 / 11 / 12 / 13 依赖）
- **Ollama**（可选）：`localhost:11434`，模块 02 依赖
- **Node.js / npx**：模块 16 通过 `npx -y @baidumap/mcp-server-baidu-map` 拉起远程 MCP Server，需自备百度地图 API Key
- **MiniMax API Key**（可选）：模块 17 的 `spring.ai.openai.api-key`，配合 `https://api.minimaxi.com` 与 `MiniMax-M3` 模型

Maven 仓库配置在父 `pom.xml`：公司内网 Nexus 优先，阿里云公共仓库与 Spring Milestones 兜底。内网地址换成自己的即可。

## 学习路径

建议按模块编号顺序推进：`01 → 02` 打通调用链路，`03 → 08` 掌握 API 抽象层与提示词/记忆，`09 → 13` 进入多模态与 RAG/Tool Calling，`14 → 16` 进阶 MCP 协议，`17` 拓展第三方模型服务接入。

每个模块对应的理论要点已整理在 [笔记.md](笔记.md)。

## 目录结构

```
saaInAction
├── 笔记.md                      # 课程理论笔记
└── saaAtguiguV1/
    ├── pom.xml                  # 父工程，统一依赖版本
    ├── SAA-01HelloWorld/
    │   └── src/main/
    │       ├── java/com/fxy/    # config（模型装配）/ controller（测试入口）
    │       └── resources/       # application.properties
    └── ...
```

代码包统一为 `com.fxy`，每个模块下固定 `config`（模型 / 存储 / MCP 装配）与 `controller`（测试用接口）两个包。

## 说明

- 本仓库为个人学习笔记，`.idea` 下的个人配置与 `target` 编译产物可能残留，如需清理可自行补充 `.gitignore`。
- 各模块 `spring.application.name` 部分为课程复制模板，命名不统一，不影响运行。
