# ssm
- 项目基于 [Dubbo](http://dubbo.io/) 框架来构建分布式服务，前端采用 [Bootstrap](http://getbootstrap.com/) 框架，后端采用了 [SpringMVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)、[Spring](https://spring.io/)、[MyBatis](http://www.mybatis.org/mybatis-3/zh/)、[Shiro](https://shiro.apache.org/)、[Dubbo](http://dubbo.io/) 、[Activiti](https://www.activiti.org/) 等技术；  
- 项目已实现了权限管理、工作流管理等部分功能；
- 项目实现了通用的服务端参数合法性校验功能。  

### 服务端参数校验示例
``` java
public interface UserService {
    
    String BEAN_NAME = "userService";

    int add(@NotNull @Validated({Groups.Add.class}) User user);

    int add(@Size(min = 1) @Validated({Groups.Add.class}) Collection<User> users);

    int update(@NotNull @Validated({Groups.Update.class}) User user);

    int delete(@NotNull Long id);

    int delete(@Size(min = 1) Long[] ids);

    User getUserById(@NotNull Long id);
    
} 
```  
