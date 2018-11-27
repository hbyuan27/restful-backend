## 纯RESTful风格的后端Java Service
作者: Haibin Yuan

---

这是一个基于Spring-Boot开发的, 纯RESTful风格的, 后端Java应用骨架, 可以当做基础模板用于前后端分离项目的开发.
在SecurityConfig.java中做少量改动后, 也可以切换回普通的 SpringMVC Web应用.

### Basic Auth 测试用户 (用户名 / 密码):
- admin / password (Admin Role)
- guest / password (User Role)

### LDAP 测试用户
- bob / bobspassword (当 ldap.password.encoder 为默认的 PLAIN_TEXT 时)
- ben / benspassword (当 ldap.password.encoder 为 SHA 时)

### 骨架内容
这个项目骨架可以用作后端基础模板, 已经包含如下基础内容:
 - 支持热部署
 - 统一的response格式 (JsonResponse)
 - RESTful请求的全局异常处理(@ControllerAdvice)
 - 国际化的处理, 一键切换语言
 - 数据库表Audit的实现
 - 使用Spring-Security控制API访问权限
 - 设置RESTful风格的Login, Logout, 和错误处理
 - Spring Profile 和 Maven Profile的结合使用
 - SQL辅助工具p6spy
 - Swagger的使用和API文档的生成
 - 文件上传的尺寸控制和Excel上传的实现
 - Basic Authentication和LDAP Authentication的切换使用
 - 内置的LDAP server方便测试
 - JWT, 用于前后端分离项目的用户请求鉴权
 - 其他功能陆续补充中...

### 项目结构
├── java
│   └── com
│       └── hbyuan
│           └── demo
│               ├── Application.java
│               ├── admin
│               │   ├── controller
│               │   │   └── DataImportController.java
│               │   ├── entity
│               │   │   └── DemoExcelEntity.java
│               │   ├── repo
│               │   │   └── DemoExcelRepo.java
│               │   └── service
│               │       ├── DataImportService.java
│               │       └── DemoImportService.java
│               ├── api
│               │   ├── DemoApiBody.java
│               │   └── controller
│               │       └── ExposedApiController.java
│               ├── common
│               │   ├── Auditable.java
│               │   ├── CustomizedException.java
│               │   ├── DateUtil.java
│               │   ├── DefaultAuditorAware.java
│               │   ├── JsonResponse.java
│               │   ├── LocaleMessage.java
│               │   └── RestControllerAdvice.java
│               ├── config
│               │   ├── AuditConfig.java
│               │   ├── Profiles.java
│               │   ├── SecurityConfig.java
│               │   ├── SwaggerConfig.java
│               │   ├── WebConfig.java
│               │   └── authentication
│               │       ├── AuthenticationConfig.java
│               │       ├── AuthenticationMode.java
│               │       ├── JwtAuthenticationTokenFilter.java
│               │       ├── JwtTokenUtil.java
│               │       ├── LdapConfig.java
│               │       ├── PasswordEncodeMode.java
│               │       ├── RestAccessDeniedHandler.java
│               │       ├── RestAuthenticationEntryPoint.java
│               │       ├── RestLoginFailureHandler.java
│               │       ├── RestLoginSuccessHandler.java
│               │       └── RestLogoutSuccessHandler.java
│               └── user
│                   ├── Authority.java
│                   ├── CurrentUser.java
│                   ├── UserBean.java
│                   ├── controller
│                   │   └── UserController.java
│                   ├── entity
│                   │   └── UserEntity.java
│                   ├── repo
│                   │   └── UserRepo.java
│                   └── service
│                       ├── CurrentUserDetailsService.java
│                       └── DemoUserInitializer.java
├── resources
│   ├── application.yml
│   ├── public
│   │   ├── error
│   │   │   ├── 403.html
│   │   │   └── 404.html
│   │   ├── favicon.ico
│   │   └── index.html
│   ├── spy.properties
│   ├── static
│   │   └── i18n
│   │       ├── messages.properties
│   │       └── messages_zh_CN.properties
│   └── test-server.ldif
└── webapp
    └── views
        ├── login.html
        └── upload.html