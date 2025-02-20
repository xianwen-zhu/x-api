### 目录结构说明

- **src/main/java/com/example**
    - **controller**: 控制器层，负责接收 HTTP 请求并返回响应。这里包括 REST 控制器类，通常使用 `@RestController` 或 `@Controller` 注解。
    - **service**: 业务逻辑层，封装核心业务逻辑，调用数据访问层。业务层尽量与控制器解耦，避免过多的控制器逻辑。
    - **repository**: 数据访问层，负责与数据库进行交互。使用 Spring Data JPA 或其他持久化技术与数据库交互。
    - **model**: 实体类，用于映射数据库表。通常会加上 JPA 注解（如 `@Entity`）。
    - **dto**: 数据传输对象，封装用于传输的数据结构，通常用于避免暴露实体类，或者简化数据的传输。 
    - 使用这些 DTO 的好处是
      1.请求和响应数据结构清晰分离2.避免直接暴露实体类
      3.可以针对不同场景定制数据结构
      4.更好的API文档生成

    - **exception**: 自定义异常类及统一异常处理，通常结合 `@ControllerAdvice` 进行全局异常处理。
    - **config**: 配置类，管理 Spring Boot 配置、数据库连接、消息中间件等设置。
    - **util**: 工具类，封装常用的辅助方法。

- **src/main/resources**
    - **application.properties**: Spring Boot 配置文件，包含数据库连接、日志、第三方服务等配置信息。
    - **static**: 存放静态资源（如 CSS、JavaScript、图片等）。
    - **templates**: 存放模板文件，如 Thymeleaf 模板。

- **src/test/java/com/example**
    - **controller**: 控制器层的单元测试。
    - **service**: 业务逻辑层的单元测试。
    - **repository**: 数据访问层的单元测试。

### 开发和运行

1. **克隆项目**

   ```bash
   git clone https://github.com/your-repo/my-project.git
   cd my-project