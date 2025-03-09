

## 后端文件结构介绍
```
├─.idea                          # IntelliJ IDEA 配置文件（自动生成）
└─wxf-backend-client             # 项目主目录
├─.mvn                       # Maven 配置
│  └─wrapper                 # Maven Wrapper 文件（确保版本一致性）
└─src
├─main
│  ├─java
│  │  └─top
│  │      └─xcyyds
│  │          └─wxf
│  │              ├─common        # 全局通用类（常量/工具/基类）
│  │              ├─config        # 配置类（安全/数据源/Swagger）
│  │              ├─exception     # 全局异常处理（如 @ControllerAdvice）
│  │              ├─module        # 业务模块化设计（核心功能）
│  │              │  ├─auth       # 认证模块
│  │              │  │  ├─controller    # 认证相关 API 接口
│  │              │  │  ├─pojo
│  │              │  │  │  ├─dto        # 认证数据传输对象（如 LoginDTO）
│  │              │  │  │  └─entity    # 认证实体类（如 User）
│  │              │  │  ├─repository    # 认证数据访问层（JPA 接口）
│  │              │  │  └─service      # 认证业务逻辑层
│  │              │  │      └─impl      # 认证服务实现类
│  │              │  ├─post       # 帖子模块（结构同 auth）
│  │              │  │  ├─controller    # 认证相关 API 接口
│  │              │  │  ├─pojo
│  │              │  │  │  ├─dto        # 认证数据传输对象
│  │              │  │  │  └─entity    # 认证实体类
│  │              │  │  ├─repository    # 认证数据访问层
│  │              │  │  └─service      # 认证业务逻辑层
│  │              │  │      └─impl      # 认证服务实现类
│  │              │  └─user      # 用户模块（结构同 auth）
│  │              │     ├─controller    # 认证相关 API 接口
│  │              │     ├─pojo
│  │              │     │  ├─dto        # 认证数据传输对象
│  │              │     │  └─entity    # 认证实体类
│  │              │     ├─repository    # 认证数据访问层
│  │              │     └─service      # 认证业务逻辑层
│  │              │         └─impl      # 认证服务实现类
│  │              └─util         # 通用工具类（日期/加密/JSON等）
│  └─resources
│      ├─application.yml         # 主配置文件
│      ├─static/                 # 静态资源（CSS/JS/图片）
│      └─mapper/                 # MyBatis XML（若使用）
└─test
└─java
└─top
└─xcyyds
└─wxf            # 单元测试类（与 main 结构镜像）
```