## SpringMVC

### 1、MVC介绍

MVC是一种软件架构的思想，将软件按照模型、视图、控制器来划分

M：Model，模型层，指工程中的JavaBean，作用是处理数据

JavaBean分为两类：

- 一类称为实体类Bean：专门存储业务数据的，如 Student、User 等
- 一类称为业务处理 Bean：指 Service 或 Dao 对象，专门用于处理业务逻辑和数据访问。

V：View，视图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据

C：Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器

MVC的工作流程： 用户通过视图层发送请求到服务器，在服务器中请求被Controller接收，Controller调用相应的Model层处理请求，处理完毕将结果返回到Controller，Controller再根据请求处理的结果找到相应的View视图，渲染数据后最终响应给浏览器

#### 1.1、SpringMVC

SpringMVC是Spring的一个后续产品，是Spring的一个子项目

SpringMVC 是 Spring 为表述层开发提供的一整套完备的解决方案。在表述层框架历经 Strust、WebWork、Strust2 等诸多产品的历代更迭之后，目前业界普遍选择了 SpringMVC 作为 Java EE 项目表述层开发的**首选方案**

> 注：三层架构分为表述层（或表示层）、业务逻辑层、数据访问层，表述层表示前台页面和后台servlet

#### 1.2、SpringMVC的特点

- **Spring** **家族原生产品**，与 IOC 容器等基础设施无缝对接
- **基于原生的****Servlet**，通过了功能强大的**前端控制器****DispatcherServlet**，对请求和响应进行统一处理
- 表述层各细分领域需要解决的问题**全方位覆盖**，提供**全面解决方案**
- **代码清新简洁**，大幅度提升开发效率
- 内部组件化程度高，可插拔式组件**即插即用**，想要什么功能配置相应组件即可
- **性能卓著**，尤其适合现代大型、超大型互联网项目要求

### 2、入门案例

#### 2.1、开发环境

IDE：idea 2022.2

构建工具：maven3.5.6

服务器：Tomcat9.0.65

Spring版本：5.3.1

#### 2.2 maven工程

①添加web模块

a》打包方式war

```xml
<groupId>com.tzadok.spring_mvc</groupId>
<artifactId>spting_mvc_rest</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>war</packaging>
```

b》修改web.xml路径

<img src="D:\SSM2022\SSM-pro\SSM-SpringMVC\images\001.png" alt="001"  />

②引入依赖

```xml
<dependencies>
    <!-- SpringMVC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.1</version>
    </dependency>

    <!-- 日志 -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version> </dependency>

    <!-- ServletAPI -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- Spring5和Thymeleaf整合包 -->
    <dependency>
        <groupId>org.thymeleaf</groupId>
        <artifactId>thymeleaf-spring5</artifactId>
        <version>3.0.12.RELEASE</version>
    </dependency>

</dependencies>
```

> 注：由于 Maven 的传递性，我们不必将所有需要的包全部配置依赖，而是配置最顶端的依赖，其他靠传递性导入。

#### 2.3、配置web.xml

注册SpringMVC的前端控制器DispatcherServlet

①默认配置方式

```xml
<!--
    配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理
    SpringMVC的配置文件默认的位置和名称
    位置：WEB-INF下
    名称：<servlet-name>-servlet.xml,当前配置下的配置文件名为SpringMVC-servlet.xml
    url-pattern中/和/*的区别：
    /:匹配浏览器向服务器发送的所有请求（不包括.jsp）
    /*:匹配浏览器向服务器发送的所有请求（包括.jsp）
-->
<servlet>
    <servlet-name>SpringMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>SpringMVC</servlet-name>
    <!--设置springMVC的核心控制器所能处理的请求的请求路径
        /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
        但是/不能匹配.jsp请求路径的请求 -->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

②扩展配置方式

可通过init-param标签设置SpringMVC配置文件的位置和名称，通过load-on-startup标签设置SpringMVC前端控制器DispatcherServlet的初始化时间

```xml
<!--
    配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理
    SpringMVC的配置文件默认的位置和名称
    位置：WEB-INF下
    名称：<servlet-name>-servlet.xml,当前配置下的配置文件名为SpringMVC-servlet.xml
    url-pattern中/和/*的区别：
    /:匹配浏览器向服务器发送的所有请求（不包括.jsp）
    /*:匹配浏览器向服务器发送的所有请求（包括.jsp）
-->
<servlet>
    <servlet-name>SpringMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--  设置SpringMVC配置文件的位置和名称      -->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:SpringMVC.xml</param-value>
    </init-param>
    <!--  将DispatcherServlet的初始化时间提前到服务器启动时      -->
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>SpringMVC</servlet-name>
    <!--设置springMVC的核心控制器所能处理的请求的请求路径
        /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
        但是/不能匹配.jsp请求路径的请求 -->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

> 注：
>
> <url-pattern>标签中使用/和/*的区别：
>
> /所匹配的请求可以是/login或.html或.js或.css方式的请求路径，但是/不能匹配.jsp请求路径的请求
>
> 因此就可以避免在访问jsp页面时，该请求被DispatcherServlet处理，从而找不到相应的页面
>
> /*则能够匹配所有请求，例如在使用过滤器时，若需要对所有请求进行过滤，就需要使用/*的写法

#### 2.4、创建请求控制器

由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器

请求控制器中每一个处理请求的方法成为控制器方法

因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Spring的IoC容器管理，此时SpringMVC才能够识别控制器的存在

```java
@Controller
public class HelloController {

}
```

#### 2.5、创建SpringMVC的配置文件

```xml
<!-- 扫描控制层组件   -->
<context:component-scan base-package="com.tzadok.spring_mvc.controller"></context:component-scan>

<!-- 配置Thymeleaf视图解析器 -->
<bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
    <property name="order" value="1"/>
    <property name="characterEncoding" value="UTF-8"/>
    <property name="templateEngine">
        <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
            <property name="templateResolver">
                <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                    <!-- src/main/webapp/WEB-INF -->
                    <!-- 视图前缀 -->
                    <property name="prefix" value="/WEB-INF/templates/"/>
                    <!-- 视图后缀 -->
                    <property name="suffix" value=".html"/>
                    <property name="templateMode" value="HTML5"/>
                    <property name="characterEncoding" value="UTF-8" />
                </bean>
            </property>
        </bean>
    </property>
</bean>
```

#### 2.6测试HelloWorld

①控制器

```java
// @RequestMapping注解：处理请求和控制器方法之间的映射关系 
// @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径 
// localhost:8080/springMVC/
@Controller
public class HelloController {

    @RequestMapping("/")
    public String protal(){
        //将逻辑视图返回
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "success";
    }
}
```

②HTML

**index.html**

```html
<body>
	<h1>index.html</h1>
	<a th:href="@{/hello}">测试SpringMVC</a>
	<a href="/hello">测试绝对路径</a>
</body>
```

**success.html**

```html
<body>
	<h1>success.html</h1>
</body>
```

#### 2.7总结

浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理。前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法。处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终转发到视图所对应页面

### 3、@RequestMapping注解

#### 3.1@RequestMapping注解的功能

从注解名称上我们可以看到，@RequestMapping注解的作用就是将请求和处理请求的控制器方法关联起来，建立映射关系。

SpringMVC 接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求。

#### 3.2、@RequestMapping注解的位置

@RequestMapping标识一个类：设置映射请求的请求路径的初始信息

@RequestMapping标识一个方法：设置映射请求请求路径的具体信息

```java
@RequestMapping("/test")
public class TestRequestMappingController {
//此时控制器方法所匹配的请求的请求路径为/test/hello
	@RequestMapping("/hello")
    public String hello(){
        return "success";
    }
}
```

#### 3.3、@RequestMapping注解的value属性

@RequestMapping注解的value属性通过请求的请求地址匹配请求映射

@RequestMapping注解的value属性是字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求

@RequestMapping注解的value属性必须设置，至少通过请求地址匹配请求映射

```html
<a th:href="@{/hello}">测试@RequestMapping注解所标识的位置</a><br>
<a th:href="@{/abc}">测试@RequestMapping注解的value属性</a><br>
```

控制器

```java
@RequestMapping(value = {"/hello","/abc"})
public String hello(){
    return "success";
}
```

#### 3.4、@RequestMapping注解的method属性

@RequestMapping注解的method属性通过请求的请求方式（get或post）匹配请求映射

@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配多种请求方式的请求

若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，则浏览器报错

405：Request method 'POST' not supported

```html
<form th:action="@{/hello}" method="post">
    <input type="submit" value="测试@requestMapping注解的method属性"><br>
</form>
```

```java
//此时控制器方法所匹配的请求的请求路径为/test/hello
@RequestMapping(
        value = {"/hello","/abc"},
        method = {RequestMethod.POST,RequestMethod.GET},
)
public String hello(){
    return "success";
}
```

> 注：
>
> 1、对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解
>
> 处理get请求的映射-->@GetMapping
>
> 处理post请求的映射-->@PostMapping
>
> 处理put请求的映射-->@PutMapping
>
> 处理delete请求的映射-->@DeleteMapping
>
> 2、常用的请求方式有get，post，put，delete
>
> 但是目前浏览器只支持get和post，若在form表单提交时，为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理
>
> 若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter，在RESTful部分会讲到

#### 3.5、@RequestMapping注解的params属性（了解）

@RequestMapping注解的params属性通过请求的请求参数匹配请求映射

@RequestMapping注解的params属性是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系

"param"：要求请求映射所匹配的请求必须携带param请求参数

"!param"：要求请求映射所匹配的请求必须不能携带param请求参数

"param=value"：要求请求映射所匹配的请求必须携带param请求参数且param=value

"param!=value"：要求请求映射所匹配的请求必须携带param请求参数但是param!=value

```html
<a th:href="@{/hello?username='admin'}">测试@RequestMapping注解params参数</a><br>
<a th:href="@{/hello(username='admin')}">测试@RequestMapping注解params参数</a><br>
```

```java
//此时控制器方法所匹配的请求的请求路径为/test/hello
@RequestMapping(
        value = {"/hello","/abc"},
        method = {RequestMethod.POST,RequestMethod.GET},
        params = {"username","!password","age=20","gender!=女"},
)
public String hello(){
    return "success";
}
```

> 注：
>
> 若当前请求满足@RequestMapping注解的value和method属性，但是不满足params属性，此时页面回报错400：Parameter conditions "xxxxxxx" not met for actual

#### 3.6、@RequestMapping*注解的headers属性（了解）

@RequestMapping注解的headers属性通过请求的请求头信息匹配请求映射

@RequestMapping注解的headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系

"header"：要求请求映射所匹配的请求必须携带header请求头信息

"!header"：要求请求映射所匹配的请求必须不能携带header请求头信息

"header=value"：要求请求映射所匹配的请求必须携带header请求头信息且header=value

"header!=value"：要求请求映射所匹配的请求必须携带header请求头信息且header!=value

若当前请求满足@RequestMapping注解的value和method属性，但是不满足headers属性，此时页面显示404错误，即资源未找到

#### 3.7、SpringMVC支持ant风格的路径

？：表示任意的单个字符

*：表示任意的0个或多个字符

**：表示任意层数的任意目录

注意：在使用** 时，只能使用 /**/xxx的方式

```html
<a th:href="@{/aaa/test/ant}">测试@RequestMapping注解支持ant风格的路径</a><br>
```

```java
@RequestMapping("/**/test/ant")
public String testAnt(){
    return "success";
}
```

#### 3.8、SpringMVC支持路径中的占位符（重点）

原始方式：/deleteUser?id=1

rest方式：/user/delete/1

SpringMVC路径中的占位符常用于RESTful风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在通过@PathVariable注解，将占位符所表示的数据赋值给控制器方法的形参

```html
<a th:href="@{/test/rest/admin/1}">测试@RequestMapping注解value属性中的占位符</a><br>
```

```java
@RequestMapping("/test/rest/{username}/{id}")
public String testRest(@PathVariable("id") Integer id,@PathVariable("username") String username){
    System.out.println("id:" + id + ",username:" + username);
    return "success";
}
//最终输出的内容为-->id:1,username:admin
```

#### 3.9总结

```java
/**
 * @author Tzadok
 * @date 2022/8/7 21:04:13
 * @project SSM-SpringMVC
 * @description:
 * 1、@RequestMapping注解标识的位置
 * @RequestMapping标识一个类：设置映射请求的请求路径的初始信息
 * @RequestMapping标识一个方法：设置映射请求请求路径的具体信息
 * 2、@RequestMapping注解value属性
 * 作用：通过请求的请求路径匹配请求
 * value属性是数组类型，即当前浏览器所发送请求的请求路径匹配value属性中的任何一个值
 * 则当前请求就会被注解所标识的方法进行处理
 * 3、@RequestMapping注解的method属性
 * 作用：通过请求的请求方式匹配请求
 * method属性时RequestMethod类型的数组，即当前浏览器所发送请求的请求方式匹配method属性找那个的任何一种请求方式
 * 则当前请求就会被注解所标识的方法进行处理
 * 若浏览器所发送的请求路径和@RequestMapping注解value属性匹配，但是请求方式不匹配
 * 此时页面报错：405 - Request method ‘xxxx’ not supported
 * 在@RequestMapping的基础上，结合请求方式的一些派生注解：
 * @GetMapping，@PostMapping，@DeleteMapping，@PutMapping
 * 4、@RequestMapping注解的params属性
 * 作用：通过请求的请求参数匹配请求，即浏览器发送的请求参数必须满足params属性的设置
 * params可以使用四种表达式：
 * "param"：表示当前所匹配请求的请求参数必须携带param参数
 * "!param"：表示当前所匹配请求的请求参数中一定不能携带param参数
 * "param=value"：表示当前所匹配请求的请求参数必须携带param参数且值必须为value
 * "param!=value"：表示当前所匹配请求的请求参数可以不携带param参数，若携带值一定不能是value
 * 若浏览器所发送的请求路径和@RequestMapping注解value属性匹配，但是请求参数不匹配
 * 此时页面报错：400 - Parameter conditions "username" not met for actual request parameters
 * 5、@RequestMapping注解的headers属性
 * 作用：通过请求的请求头信息匹配请求，即浏览器发送的请求的请求头信息必须满足headers属性的设置
 * 若浏览器所发送的请求路径和@RequestMapping注解value属性匹配，但是请求头信息不匹配
 * 此时页面报错：404 - Completed 404 NOT_FOUND
 * 6、SpringMVC支持ant风格的路径
 * 在@RequestMapping注解额value属性值中设置一些特殊字符
 * ？：任意的单个字符（不包括问号本身）
 * *：表示任意的0个或多个字符(不包括?和/)
 * **：表示任意层数的任意目录，注意使用方式只能**写在双斜线中，前后不能有任何的其他字符
 * 注意：在使用**时，只能使用\**\xxx的方式
 * 7、@RequestMapping注解使用路径中的占位符
 * 传统：/deleteUser?id=1
 * rest:/user/delete/1
 * 需用在@RequestMapping注解的value属性中所设置的路径汇总，使用{xxx}的方式表示路径中的数据
 * 在通过@PathVariable注解，将占位符所标识的值和控制器方法的形参进行绑定
 */
```

### 4、SpringMVC获取请求参数

#### 4.1、通过ServletAPI获取

将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请求的请求报文的对象

```java
@RequestMapping("/param/servletAPI")
public String getParamByServletRequest(HttpServletRequest request,HttpServletResponse response){
    HttpSession session = request.getSession();
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    System.out.println("username:"+username+",password:"+password);
    try {
        response.getWriter().write("<h1>" + "username:"+username+",password:"+password +"</h1>");
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "success";
}
```

#### 4.2、通过控制器方法的形参获取请求参数

在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在

DispatcherServlet中就会将请求参数赋值给相应的形参

```html
<form th:action="@{/param/pojo}" method="post">
    账户:<input type="text" name="username"><br>
    密码:<input type="password" name="password"><br>
    <input type="submit" value="登录"><br>
</form>
```

```java
@RequestMapping("param")
public String getParam(String username,String password){
    System.out.println("username:"+username+",password:"+password);
    return "success";
}
```

> 注：
>
> 若请求所传输的请求参数中有多个同名的请求参数，此时可以在控制器方法的形参中设置字符串
>
> 数组或者字符串类型的形参接收此请求参数
>
> 若使用字符串数组类型的形参，此参数的数组中包含了每一个数据
>
> 若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果

#### 4.3、@RequestParam

@RequestParam是将请求参数和控制器方法的形参创建映射关系

@RequestParam注解一共有三个属性：

value：指定为形参赋值的请求参数的参数名

required：设置是否必须传输此请求参数，默认值为true

若设置为true时，则当前请求必须传输value所指定的请求参数，若没有传输该请求参数，且没有设置defaultValue属性，则页面报错400：Required String parameter 'xxx' is not present；若设置为false，则当前请求不是必须传输value所指定的请求参数，若没有传输，则注解所标识的形参的值为null

defaultValue：不管required属性值为true或false，当value所指定的请求参数没有传输或传输的值为""时，则使用默认值为形参赋值

```java
@RequestMapping("param")
public String getParam(
        @RequestParam(value = "userName",required = true,defaultValue = "hello") String username,
        String password,
){
    System.out.println("jsessionId:" + jsessionId);
    System.out.println("referer:" + referer);
    System.out.println("username:"+username+",password:"+password);
    return "success";
}
```

#### 4.4、@RequestHeader

@RequestHeader是将请求头信息和控制器方法的形参创建映射关系

@RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

```java
@RequestMapping("param")
public String getParam(
        @RequestParam(value = "userName",required = true,defaultValue = "hello") String username,
        String password,
        @RequestHeader("referer") String referer,
){
    System.out.println("jsessionId:" + jsessionId);
    System.out.println("referer:" + referer);
    System.out.println("username:"+username+",password:"+password);
    return "success";
}
```

#### 4.5、@CookieValue

@CookieValue是将cookie数据和控制器方法的形参创建映射关系

@CookieValue注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

```java
@RequestMapping("param")
public String getParam(
        @RequestParam(value = "userName",required = true,defaultValue = "hello") String username,
        String password,
        @RequestHeader("referer") String referer,
        @CookieValue("JSESSIONID")String jsessionId
){
    System.out.println("jsessionId:" + jsessionId);
    System.out.println("referer:" + referer);
    System.out.println("username:"+username+",password:"+password);
    return "success";
}
```

#### 4.6、通过POJO获取请求参数

可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

```html
<form th:action="@{/param/pojo}" method="post">
    账户:<input type="text" name="username"><br>
    密码:<input type="password" name="password"><br>
    <input type="submit" value="登录"><br>
</form>
```

```java
@RequestMapping("/param/pojo")
public String getParamByPojo(User user){
    System.out.println(user);
    return "success";
}
```

#### 4.7、解决获取请求参数的乱码问题

解决获取请求参数的乱码问题，可以使用SpringMVC提供的编码过滤器CharacterEncodingFilter，但是必须在web.xml中进行注册

```xml
<!-- 配置Spring的编码过滤器   -->
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

> 注：
>
> SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效

#### 4.8总结

```java
/**
 * @author Tzadok
 * @date 2022/8/8 10:13:43
 * @project SSM-SpringMVC
 * @description:
 * 获取请求参数的方式：
 * 1、通过servletAPI获取
 * 只需要在控制器方法的形参位置设置HttpServletRequest类型的形参，就可以在控制器方法中使用request对象获取请求参数
 * 2、通过控制器方法的形参获取
 * 只需要在控制器方法的形参位置，设置一个形参，形参的名字和请求参数的名字一致即可
 * 3、@RequestParam：将请求参数和控制方法的形参绑定
 * @RequestParam注解的三个属性：value，required，defaultValue
 * value：设置和形参绑定的请求参数的名字
 * required:设置是否必须传输value所对应的请求参数
 * 默认值为true，表示value所对应的请求参数必须传输，否则页面报错
 * 400 Required String parameter 'username' id not present
 * 若设置为false，则表示value所对应的请求参数不是必须传输，若为传输，则形参值为null
 * defaultValue：设置当没有传输value所对应的请求参数时，为形参设置的默认值，此时和required的属性值无关
 * 4、@RequestHeader：将请求头信息和控制器方法的形参绑定
 * 5、@CookieValue：将cookie数据和控制器方法的形参绑定
 * 6、通过控制器方法的实体类类型的形参获取请求参数
 * 需要在控制器方法的形参位置设置实体类类型的形参，要保证实体类中的属性的属性名和请求参数的名字一样
 * 可以通过实体类类型的形参获取请求参数
 * 7、解决获取请求此参数的乱码问题
 * 在web.xml中配置Spring的编码过滤器CharacterEncodingFilter
 */
```

### 5、域对象共享数据

#### 5.1、使用ServletAPI向request域对象共享数据

```java
@RequestMapping("/test/request")
public String testRequest(HttpServletRequest request){
    request.setAttribute("testRequestScope","hello,Request");
    return "success";
}
```

#### 5.2、使用ModelAndView向request域对象共享数据

```java
@RequestMapping("/test/mav")
public ModelAndView testMAV(){
    /**
     * ModelAndView包含model和view的功能
     * model：向请求域中共享数据
     * view：设置逻辑视图实现页面跳转
     */
    ModelAndView mav = new ModelAndView();
    //向请求域中共享数据
    mav.addObject("testRequestScope","hello,ModelAndView");
    //设置逻辑视图
    mav.setViewName("success");
    return mav;
}
```

#### 5.3、使用Model向request域对象共享数据

```java
@RequestMapping("/test/model")
public String testModel(Model model){
    System.out.println(model.getClass().getName());
    //org.springframework.validation.support.BindingAwareModelMap
    model.addAttribute("testRequestScope","hello,Model");
    return "success";
}
```

#### 5.4、使用map向request域对象共享数据

```java
@RequestMapping("/test/map")
public String testMap(Map<String,Object> map){
	System.out.println(map.getClass().getName());
	//org.springframework.validation.support.BindingAwareModelMap
	map.put("testRequestScope","hello,Map");
	return "success";
}
```

#### 5.5、使用ModelMap向request域对象共享数据

```java
@RequestMapping("/test/modelMap")
public String testModelMap(ModelMap modelMap){
    System.out.println(modelMap.getClass().getName());
    //org.springframework.validation.support.BindingAwareModelMap
    modelMap.addAttribute("testRequestScope","hello,ModelMap");
    return "success";
}
```

#### 5.6、**Model**、**ModelMap**、**Map**的关系

```java
public interface Model{} 
public class ModelMap extends LinkedHashMap<String, Object> {} 
public class ExtendedModelMap extends ModelMap implements Model {} 
public class BindingAwareModelMap extends ExtendedModelMap {}
```

#### 5.7、向session域共享数据

```java
@RequestMapping("/test/session")
public String testSession(HttpSession session){
    session.setAttribute("testSessionScope","hello,Session");
    return "success";
}
```

#### 5.8、向application域共享数据

```java
@RequestMapping("/test/application")
public String testApplication(HttpSession session){
    ServletContext servletContext = session.getServletContext();
    servletContext.setAttribute("testApplicationScope","hello,Application");
    return "success";
}
```

### 6、SpringMVC的视图

SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户

SpringMVC视图的种类很多，默认有转发视图和重定向视图

当工程引入jstl的依赖，转发视图会自动转换为JstlView

若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

#### 6.1、ThymeleafView

当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图

后缀所得到的最终路径，会通过转发的方式实现跳转

```java
@RequestMapping("/test/view/thymeleaf")
public String testThymeleafView(){
    return "success";
}
```

#### 6.2、转发视图

SpringMVC中默认的转发视图是InternalResourceView

SpringMVC中创建转发视图的情况：

当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式实现跳转

例如"forward:/"，"forward:/employee"

```java
@RequestMapping("/test/view/forward")
public String InternalResourceView(){
    return "forward:/test/model";
}
```

#### 6.3、重定向视图

SpringMVC中默认的重定向视图是RedirectView

当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式实现跳转

例如"redirect:/"，"redirect:/employee"

```java
@RequestMapping("/test/view/rediect")
public String RedirectView(){
    return "redirect:/test/model";
}
```

> 注：
>
> 重定向视图在解析时，会先将redirect:前缀去掉，然后会判断剩余部分是否以/开头，若是则会自动拼接上下文路径

#### 6.4、视图控制器view-controller

当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

```xml
<!-- 开启mvc的注解驱动   -->
<mvc:annotation-driven/>

<!--
    视图控制器：为当前的请求直接设置视图名称实现页面跳转
    若设置视图控制器，则只有视图控制器所设置的请求会被处理，其他的请求将全部404
    此时必须在配置一个标签：<mvc:annotation-driven/>
 -->
<!--suppress SpringXmlModelInspection -->
<mvc:view-controller path="/" view-name="index"/>
```

> 注：
>
> 当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，此时需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：
>
> <mvc:annotation-driven />

### 7、RESTful

#### 7.1、RESTful简介

REST：**Re**presentational **S**tate **T**ransfer，表现层资源状态转移。

**①资源**

资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成。每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址。对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。

**②资源的表述**

资源的表述是一段对于资源在某个特定时刻的状态的描述。可以在客户端-服务器端之间转移（交换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格式可以通过协商机制来确定。请求-响应方向的表述通常使用不同的格式。

**③状态转移**

状态转移说的是：在客户端和服务器端之间转移（transfer）代表资源状态的表述。通过转移和操作资源的表述，来间接实现操作资源的目的。

#### 7.2、RESTful的实现

具体说，就是 HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。

它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE用来删除资源。

REST 风格提倡 URL 地址使用统一的风格设计，从前到后各个单词使用斜杠分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为 URL 地址的一部分，以保证整体风格的一致性。

| 操作     | 传统方式         | REST风格                |
| -------- | ---------------- | ----------------------- |
| 查询操作 | getUserById?id=1 | user/1-->get请求方式    |
| 保存操作 | saveUser         | user-->post请求方式     |
| 删除操作 | deleteUser?id=1  | user/1-->delete请求方式 |
| 更新操作 | updateUser       | user-->put请求方式      |

#### 7.3、HiddenHttpMethodFilter

由于浏览器只支持发送get和post方式的请求，那么该如何发送put和delete请求呢？

SpringMVC 提供了 **HiddenHttpMethodFilter** 帮助我们**将** **POST** **请求转换为** **DELETE** **或** **PUT** **请求**

**HiddenHttpMethodFilter** 处理put和delete请求的条件：

a>当前请求的请求方式必须为post

b>当前请求必须传输请求参数_method

满足以上条件，**HiddenHttpMethodFilter** 过滤器就会将当前请求的请求方式转换为请求参数_method的值，因此请求参数_method的值才是最终的请求方式

在web.xml中注册**HiddenHttpMethodFilter**

```xml
<!-- 设置处理请求方式的过滤器   -->
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

> 注：
>
> 目前为止，SpringMVC中提供了两个过滤器：CharacterEncodingFilter和 HiddenHttpMethodFilter
>
> 在web.xml中注册时，必须先注册CharacterEncodingFilter，再注册HiddenHttpMethodFilter
>
> 原因：
>
> - 在 CharacterEncodingFilter 中通过 request.setCharacterEncoding(encoding) 方法设置字符集的
>
> - request.setCharacterEncoding(encoding) 方法要求前面不能有任何获取请求参数的操作
>
> - 而 HiddenHttpMethodFilter 恰恰有一个获取请求方式的操作：
>
> - ```java
>   String paramValue = request.getParameter(this.methodParam);
>   ```

### 8、RESTful案例

#### 8.1、准备工作

和传统 CRUD 一样，实现对员工信息的增删改查。

- 搭建环境
- 准备实体类

```java
package com.tzadok.spring_mvc.pojo;

/**
 * @author Tzadok
 * @date 2022/8/8 21:20:54
 * @project SSM-SpringMVC
 * @description:
 */
public class Employee {

    private Integer id;

    private String lastName;

    private String email;

    //1 male, 0 female
    private Integer gender;

    public Employee() {
    }

    public Employee(Integer id, String lastName, String email, Integer gender) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }
}
```

- 准备dao模拟数据

```java
package com.tzadok.spring_mvc.dao;

import com.tzadok.spring_mvc.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tzadok
 * @date 2022/8/8 21:22:42
 * @project SSM-SpringMVC
 * @description:
 */
@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employees = null;

    static{
        employees = new HashMap<Integer, Employee>();

        employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1));
        employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1));
        employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0));
        employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0));
        employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1));
    }

    private static Integer initId = 1006;

    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        employees.put(employee.getId(), employee);
    }

    public Collection<Employee> getAll(){
        return employees.values();
    }

    public Employee get(Integer id){
        return employees.get(id);
    }

    public void delete(Integer id){
        employees.remove(id);
    }
}
```

#### 8.2、功能清单

| 功能                | URL地址     | 请求地址 |
| ------------------- | ----------- | -------- |
| 访问首页√           | /           | GET      |
| 查询全部数据√       | /employee   | GET      |
| 删除√               | /employee/1 | DELETE   |
| 跳转到添加数据页面√ | /toAdd      | GET      |
| 执行保存√           | //employee  | POST     |
| 跳转到更新数据页面√ | /employee/2 | GET      |
| 执行更新√           | /employee   | PUT      |

#### 8.3、具体功能：访问首页

①配置view-controller

```xml
<mvc:view-controller path="/" view-name="index"/>
```

②创建页面

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/employee}">访问所有员工</a>
</body>
</html>
```

#### 8.4、具体功能：查询员工数据

①控制器方法

```java
@GetMapping("/employee")
public String getAllEmployee(Model model){
    //获取所有的员工信息
    Collection<Employee> allEmployee = employeeDao.getAll();
    //将员工信息存到请求域中
    model.addAttribute("allEmployee",allEmployee);
    return "employee_list";
}
```

②创建employee-list.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>employee info</title>
    <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
</head>
<body>
  <table >
    <tr>
      <th colspan="5">employee list</th>
    </tr>
    <tr>
      <th>id</th>
      <th>lastName</th>
      <th>email</th>
      <th>gender</th>
      <th>options(<a th:href="@{/to/add}">add</a> )</th>
    </tr>
    <tr th:each="employee : ${allEmployee}">
      <td th:text="${employee.id}"></td>
      <td th:text="${employee.lastName}"></td>
      <td th:text="${employee.email}"></td>
      <td th:text="${employee.gender}"></td>
      <td>
        <a th:href="@{'/employee/'+${employee.id}}">update</a> /
        <a @click="deleteEmployee()" th:href="@{'/employee/'+${employee.id}}">delete</a>
      </td>
    </tr>
  </table>
</body>
</html>
```

#### 8.5、具体功能：删除

①创建处理delete请求方式的表单

```html
<!-- 作用：通过超链接控制表单的提交，将post请求转换为delete请求 -->
<form method="post">
  <input type="hidden" name="_method" value="delete">
</form>
```

②删除超链接绑定点击事件

引入vue.js

```html
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
```

删除超链接

```html
<a @click="deleteEmployee()" th:href="@{'/employee/'+${employee.id}}">delete</a>
```

通过vue处理点击事件

```javascript
<script type="text/javascript">
  var vue = new Vue({
    el:"#app",
    methods:{
      deleteEmployee(){
        //获取form表单
        var form = document.getElementsByTagName("form")[0];
        //将超链接的href属性值赋值给form表单的action属性
        //event.target当前触发事件的标签
        form.action = event.target.href;
        //表单提交
        form.submit();
        //阻止超链接的默认行为
        event.preventDefault();
      }
    }
  })
</script>
```

③控制器方法

```java
@DeleteMapping("/employee/{id}")
public String deleteEmployee(@PathVariable("id") Integer id){

    employeeDao.delete(id);

    return "redirect:/employee";
}
```

#### 8.6、具体功能：跳转到添加数据页面

①配置view-controller

```xml
<mvc:view-controller path="/to/add" view-name="employee_add"/>
```

②创建employee_add.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>add employee</title>
  <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
</head>
<body>
<form th:action="@{/employee}" method="post">
    <table >
        <tr>
            <th colspan="2">employee add</th>
        </tr>
        <tr>
            <td>lastName</td>
            <td>
                <input type="text" name="lastName">
            </td>
        </tr>
        <tr>
            <td>email</td>
            <td>
                <input type="text" name="email">
            </td>
        </tr>
        <tr>
            <td>gender</td>
            <td>
                <input type="radio" name="gender" value="1">male
                <input type="radio" name="gender" value="0">female
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="add">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
```

#### 8.7、具体功能：执行保存

①控制器方法

```java
@PostMapping("/employee")
public String saveEmployee(Employee employee){
    //保存员工信息
    employeeDao.save(employee);
    //重定向到列表功能：/employee
    return "redirect:/employee";
}
```

#### 8.8、具体功能：跳转到更新数据页面

①修改超链接

```html
<a th:href="@{'/employee/'+${employee.id}}">update</a> /
```

②控制器方法

```java
@GetMapping("/employee/{id}")
public String ToUpdate(@PathVariable("id") Integer id,Model model){
    //根据id查询员工信息
    Employee employee = employeeDao.get(id);
    //将员工信息共享到请求域
    model.addAttribute("employee",employee);
    //跳转到employee_update.html
    return "employee_update";
}
```

③创建employee_update.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>update employee</title>
  <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
</head>
<body>
<form th:action="@{/employee}" method="post">
  <input type="hidden" name="_method" value="put">
  <input type="hidden" name="id" th:value="${employee.id}">
  <table >
    <tr>
      <th colspan="2">employee add</th>
    </tr>
    <tr>
      <td>lastName</td>
      <td>
        <input type="text" name="lastName" th:value="${employee.lastName}">
      </td>
    </tr>
    <tr>
      <td>email</td>
      <td>
        <input type="text" name="email" th:value="${employee.email}">
      </td>
    </tr>
    <tr>
      <td>gender</td>
      <td>
        <input type="radio" name="gender" value="1" th:field="${employee.gender}">male
        <input type="radio" name="gender" value="0" th:field="${employee.gender}">female
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="update">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
```

#### 8.9、具体功能：执行更新

①控制器方法

```java
@PutMapping ("/employee")
public String updateEmployee(Employee employee){
    //修改员工信息
    employeeDao.save(employee);
    //重定向到列表功能
    return "redirect:/employee";
}
```

### 9、SpringMVC处理**ajax**请求

#### 9.1、@RequestBody

@RequestBody可以获取请求体信息，使用@RequestBody注解标识控制器方法的形参，当前请求的请求体就会为当前注解所标识的形参赋值

```html
<!--此时必须使用post请求方式，因为get请求没有请求体-->
<form th:action="@{/test/up}" method="post" enctype="multipart/form-data">
    头像：<input type="file" name="photo"><br>
    <input type="submit" value="上传">
</form>
```

```java
    @RequestMapping("/test/ajax")
    public void testAjax(Integer id,@RequestBody String requestBody,HttpServletResponse response) throws IOException {
        System.out.println("requestBody" +requestBody);
        System.out.println("id:"+id);
        response.getWriter().write("hello,axios");
    }
```

输出结果：

requestBody:username=admin&password=123456

#### 9.2、@RequestBody获取json格式的请求参数

> 在使用了axios发送ajax请求之后，浏览器发送到服务器的请求参数有两种格式：
>
> 1、name=value&name=value...，此时的请求参数可以通过request.getParameter()获取，对应SpringMVC中，可以直接通过控制器方法的形参获取此类请求参数
>
> 2、{key:value,key:value,...}，此时无法通过request.getParameter()获取，之前我们使用操作json的相关jar包gson或jackson处理此类请求参数，可以将其转换为指定的实体类对象或map集合。在SpringMVC中，直接使用@RequestBody注解标识控制器方法的形参即可将此类请求参数转换为java对象

使用@RequestBody获取json格式的请求参数的条件：

1、导入jackson的依赖

```xml
<!--  处理json的包      -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.1</version>
</dependency>
```

2、SpringMVC的配置文件中设置开启mvc的注解驱动

```xml
<mvc:annotation-driven/>
```

3、在控制器方法的形参位置，设置json格式的请求参数要转换成的java类型（实体类或map）的参数，并使@RequestBody注解标识

```javascript
<script type="text/javascript" th:src="@{/js/vue.js}"></script>
<script type="text/javascript" th:src="@{/js/axios.min.js}"></script>
<script type="text/javascript">
var vue = new Vue({ 
    el:"#app", 
    methods:{ 
        testRequestBody(){ 
            axios.post( 
                "/SpringMVC/test/RequestBody/json", 
                {username:"admin",password:"123456"} 
            ).then(response=>{ console.log(response.data); 
           }); 
        } 
    } 
});
</script>
```

```java
//将json格式的数据转换为map集合
@RequestMapping("/test/RequestBody/json")
public void testRequestBody(@RequestBody Map<String,Object> map, HttpServletResponse response) throws IOException {
    System.out.println(map);
    response.getWriter().write("hello,RequestBody");

}
//将json格式的数据转换为实体类对象
@RequestMapping("/test/RequestBody/json")
public void testRequestBody(@RequestBody User user, HttpServletResponse response) throws IOException {
    System.out.println(user);
    response.getWriter().write("hello,RequestBody");

}
```

#### 9.3、@ResponseBody

@ResponseBody用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器

```java
@RequestMapping("/test/ResponseBody")
@ResponseBody
public String testResponseBody(){
    return "success";
}
```

#### 9.4、@ResponseBody响应浏览器json数据

服务器处理ajax请求之后，大多数情况都需要向浏览器响应一个java对象，此时必须将java对象转换为json字符串才可以响应到浏览器，之前我们使用操作json数据的jar包gson或jackson将java对象转换为json字符串。在SpringMVC中，我们可以直接使用@ResponseBody注解实现此功能

@ResponseBody响应浏览器json数据的条件：

1、导入jackson的依赖

```xml
<!--  处理json的包      -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.1</version>
</dependency>
```

2、SpringMVC的配置文件中设置开启mvc的注解驱动

```xml
<mvc:annotation-driven/>
```

3、使用@ResponseBody注解标识控制器方法，在方法中，将需要转换为json字符串并响应到浏览器的java对象作为控制器方法的返回值，此时SpringMVC就可以将此对象直接转换为json字符串并响应到浏览器

```javascript
<script type="text/javascript" th:src="@{/js/vue.js}"></script>
<script type="text/javascript" th:src="@{/js/axios.min.js}"></script>
<script type="text/javascript">
var vue = new Vue({ 
    el:"#app", 
    methods:{ 
        testRequestBody(){ 
            axios.post( 
                "/SpringMVC/test/RequestBody/json"
            ).then(response=>{ console.log(response.data); 
           }); 
        } 
    } 
});
</script>
```

```java
//浏览器响应list集合
@RequestMapping("/test/Response/json")
@ResponseBody
public List<User> testResponseBodyJson(){
    User user1 = new User(1001,"admin1","123456",23,"男");
    User user2 = new User(1002,"admin2","123456",23,"男");
    User user3 = new User(1003,"admin3","123456",23,"男");
    List<User> users = Arrays.asList(user1, user2, user3);
    return users;
}

//浏览器响应map集合
/*public HashMap<String, Object> testResponseBodyJson(){
    User user1 = new User(1001,"admin1","123456",23,"男");
    User user2 = new User(1002,"admin2","123456",23,"男");
    User user3 = new User(1003,"admin3","123456",23,"男");
    HashMap<String, Object> map = new HashMap<>();
    map.put("1001",user1);
    map.put("1002",user2);
    map.put("1003",user3);
    return map;
}*/
//浏览器响应对象
/*public User testResponseBodyJson(){
    User user = new User(1001,"admin","123456",23,"男");
    return user;
}*/
```

#### 9.5、@RestController注解

@RestController注解是springMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了

@Controller注解，并且为其中的每个方法添加了@ResponseBody注解

### 10、文件的上传和下载

#### 10.1、文件下载

ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文使用ResponseEntity实现下载文件的功能

```java
@RequestMapping("/test/down")
public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
    //获取ServletContext对象
    ServletContext servletContext = session.getServletContext();
    //获取服务器中文件的真实路径
     String realPath = servletContext.getRealPath("/images/1.jpg");
    // 创建输入流
     InputStream is = new FileInputStream(realPath);
    // 创建字节数组，is.available()获取输入流所对应的字节数
     byte[] bytes = new byte[is.available()];
    // 将流读到字节数组中
     is.read(bytes);
    // 创建HttpHeaders对象设置响应头信息
     MultiValueMap<String, String> headers = new HttpHeaders();
    // 设置要下载方式以及下载文件的名字
     headers.add("Content-Disposition", "attachment;filename=1.jpg");
    // 设置响应状态码
     HttpStatus statusCode = HttpStatus.OK;
    // 创建ResponseEntity对象
     ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
    // 关闭输入流 is.close();
     return responseEntity;
}
```

#### 10.2、文件上传

文件上传要求form表单的请求方式必须为post，并且添加属性enctype="multipart/form-data"，SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息

上传步骤：

①添加依赖：

```xml
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.1</version>
</dependency>
```

②在SpringMVC的配置文件中添加配置：

```xml
<!-- 配置文件上传解析器   -->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
```

③控制器方法：

```java
@RequestMapping("/test/up")
public String testUp(MultipartFile photo,HttpSession session) throws IOException {
    //获取上传的文件的文件名
    String filename = photo.getOriginalFilename();
    //获取上传的文件的后缀名
    String substring = filename.substring(filename.lastIndexOf("."));
    //获取UUID
    String uuid = UUID.randomUUID().toString();
    //拼接一个新的文件名
    filename = uuid + substring;
    //获取ServletContext对象
    ServletContext servletContext = session.getServletContext();
    //获取当前工程下photo目录的真实路径
    String photoPath = servletContext.getRealPath("photo");
    //创建photoPath所对应的file对象
    File file = new File(photoPath);
    //判断file所对应目录是否存在
    if (!file.exists()){
        file.mkdir();
    }
    String finaPath = photoPath + File.separator + filename;
    //实现文件上传
    photo.transferTo(new File(finaPath));
    return "success";
}
```

### 11、拦截器

#### 11.1、拦截器的配置

SpringMVC中的拦截器用于拦截控制器方法的执行

SpringMVC中的拦截器需要实现HandlerInterceptor

SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置：

```xml
<bean class="com.atguigu.interceptor.FirstInterceptor"></bean> 
<ref bean="firstInterceptor"></ref> 
<!-- 以上两种配置方式都是对DispatcherServlet所处理的所有的请求进行拦截 --> 
<mvc:interceptor> 
    <mvc:mapping path="/**"/> 
    <mvc:exclude-mapping path="/testRequestEntity"/> 
    <ref bean="firstInterceptor"></ref> 
</mvc:interceptor> 
<!--以上配置方式可以通过ref或bean标签设置拦截器，通过mvc:mapping设置需要拦截的请求，通过 mvc:exclude-mapping设置需要排除的请求，即不需要拦截的请求 -->
```

#### 11.2、拦截器的三个抽象方法

SpringMVC中的拦截器有三个抽象方法：

preHandle：控制器方法执行之前执行preHandle()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法

postHandle：控制器方法执行之后执行postHandle()

afterCompletion：处理完视图和模型数据，渲染视图完毕之后执行afterCompletion()

#### 11.3、多个拦截器的执行顺序

①若每个拦截器的preHandle()都返回true

此时多个拦截器的执行顺序和拦截器在SpringMVC的配置文件的配置顺序有关：

preHandle()会按照配置的顺序执行，而postHandle()和afterCompletion()会按照配置的反序执行

②若某个拦截器的preHandle()返回了false

preHandle()返回false和它之前的拦截器的preHandle()都会执行，postHandle()都不执行，返回false的拦截器之前的拦截器的afterCompletion()会执行

### 12、异常处理器

#### 12.1、基于配置的异常处理

SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver

HandlerExceptionResolver接口的实现类有：DefaultHandlerExceptionResolver和SimpleMappingExceptionResolver

SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver，使用方式：

```xml
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <property name="exceptionMappings">
            <props>
                <!-- key设置要处理的异常，value设置出现该异常时要跳转的页面所对应的逻辑视图-->
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
        <!--设置共享在请求域中的异常信息的属性名         -->
        <property name="exceptionAttribute" value="ex"></property>
    </bean>
```

#### 12.2、基于注解的异常处理

```java
//设置要处理的异常信息
@ExceptionHandler(ArithmeticException.class)
public String handleException(Throwable ex,Model model){

    //ex标识控制器方法要处理的异常
    model.addAttribute("ex",ex);
    return "error";
}
```

#### 12.3总结

```xml
    <mvc:interceptors>
<!--        bean和ref标签所配置的拦截器默认对DispatcherServlet处理的所有的请求进行拦截 -->
<!--        <bean class="com.tzadok.interceptor.FirstInterceptor"></bean>-->
        <ref bean="firstInterceptor"></ref>
        <ref bean="secondInterceptor"></ref>
        <mvc:interceptor>
            <!--   配置需要拦截的请求的请求路径，/**表示所有请求         -->
            <mvc:mapping path="/**"/>
            <!--   配置需要排除拦截的请求的请求路径         -->
            <mvc:exclude-mapping path="/test/hello"/>
            <!--   配置拦截器         -->
            <ref bean="firstInterceptor"></ref>
        </mvc:interceptor>
    </mvc:interceptors>
```

### 13、注解配置SpringMVC

使用配置类和注解代替web.xml和SpringMVC配置文件的功能

13.1、创建初始化类，代替web.xml

在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器。 Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文。

```java
package com.tzadok.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author Tzadok
 * @date 2022/8/10 16:11:40
 * @project SSM-SpringMVC
 * @description: 代替web.xml
 */
@Configuration
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 设置一个配置类代替Spring的配置文件
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * 设置一个配置类代替SpringMVC的配置文件
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * 设置SpringMVC的前端控制器DispatcherServlet的url-pattern路径
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 设置当前的过滤器
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        //创建编码过滤器
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        //创建处理方式的过滤器
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter};
    }
}
```

#### 13.2、创建**SpringConfig**配置类，代替**spring**的配置文件

```java
@Configuration
public class SpringConfig {
}
```

#### 13.3、创建**WebConfig**配置类，代替**SpringMVC**的配置文件

```java
package com.tzadok.config;

import com.tzadok.interceptor.FirstInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;
import java.util.Properties;

/**
 * @author Tzadok
 * @date 2022/8/10 16:15:15
 * @project SSM-SpringMVC
 * @description: 代替SpringMVC的配置文件
 * 扫描组件
 * 视图解析器
 * 默认的serlvet
 * mvc的注解驱动
 * 视图控制器
 * 文件上传解析器
 * 拦截器
 * 异常解析器
 */
//标识为配置类
@Configuration
//扫描组件
@ComponentScan("com.tzadok.controller")
//开启mvc的注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    //默认的servlet处理静态资源
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    //配置视图解析器
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    //@Bean注解可以将标识的方法的返回值作为bean进行管理，bean的id为方法的方法名
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

    @Override
    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        FirstInterceptor firstInterceptor = new FirstInterceptor();
        registry.addInterceptor(firstInterceptor).addPathPatterns("/**");
    }

    @Override
    //配置异常解析器
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties prop = new Properties();
        prop.setProperty("java.lang.ArithmeticException","error");
        exceptionResolver.setExceptionMappings(prop);
        exceptionResolver.setExceptionAttribute("ex");
        resolvers.add(exceptionResolver);
    }

    //配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        //ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过 WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver
                = new ServletContextTemplateResolver( webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }
    //生成模板引擎并为模板引擎注入模板解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    //生成视图解析器并未解析器注入模板引擎
     @Bean
     public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
```

### 14、**SpringMVC**执行流程

#### 14.1、**SpringMVC**常用组件

- DispatcherServlet：**前端控制器**，不需要工程师开发，由框架提供

​	作用：统一处理请求和响应，整个流程控制的中心，由它调用其它组件处理用户的请求

- HandlerMapping：**处理器映射器**，不需要工程师开发，由框架提供

​	作用：根据请求的url、method等信息查找Handler，即控制器方法

- Handler：**处理器**，需要工程师开发

​	作用：在DispatcherServlet的控制下Handler对具体的用户请求进行处理

- HandlerAdapter：**处理器适配器**，不需要工程师开发，由框架提供

​	作用：通过HandlerAdapter对处理器（控制器方法）进行执行

- ViewResolver：**视图解析器**，不需要工程师开发，由框架提供

​	作用：进行视图解析，得到相应的视图，例如：ThymeleafView、InternalResourceView、RedirectView

- View：**视图**

​	作用：将模型数据通过页面展示给用户

#### 14.2、**SpringMVC**的执行流程

\1) 用户向服务器发送请求，请求被SpringMVC 前端控制器 DispatcherServlet捕获。

\2) DispatcherServlet对请求URL进行解析，得到请求资源标识符（URI），判断请求URI对应的映射：

a) 不存在

i. 再判断是否配置了mvc:default-servlet-handler

ii. 如果没配置，则控制台报映射查找不到，客户端展示404错误

iii. 如果有配置，则访问目标资源（一般为静态资源，如：JS,CSS,HTML），找不到客户端也会展示404错误

b) 存在则执行下面的流程

\3) 根据该URI，调用HandlerMapping获得该Handler配置的所有相关的对象（包括Handler对象以及Handler对象对应的拦截器），最后以HandlerExecutionChain执行链对象的形式返回。

\4) DispatcherServlet 根据获得的Handler，选择一个合适的HandlerAdapter。

\5) 如果成功获得HandlerAdapter，此时将开始执行拦截器的preHandler(…)方法【正向】

\6) 提取Request中的模型数据，填充Handler入参，开始执行Handler（Controller)方法，处理请求。在填充Handler的入参过程中，根据你的配置，Spring将帮你做一些额外的工作：

a) HttpMessageConveter： 将请求消息（如Json、xml等数据）转换成一个对象，将对象转换为指定的响应信息

b) 数据转换：对请求消息进行数据转换。如String转换成Integer、Double等

c) 数据格式化：对请求消息进行数据格式化。 如将字符串转换成格式化数字或格式化日期等

d) 数据验证： 验证数据的有效性（长度、格式等），验证结果存储到BindingResult或Error中

\7) Handler执行完成后，向DispatcherServlet 返回一个ModelAndView对象。

\8) 此时将开始执行拦截器的postHandle(...)方法【逆向】。

\9) 根据返回的ModelAndView（此时会判断是否存在异常：如果存在异常，则执行HandlerExceptionResolver进行异常处理）选择一个适合的ViewResolver进行视图解析，根据Model和View，来渲染视图。

\10) 渲染视图完毕执行拦截器的afterCompletion(…)方法【逆向】。

\11) 将渲染结果返回给客户端。

### SSM整合

#### 1.1、ContextLoaderListener

Spring提供了监听器ContextLoaderListener，实现ServletContextListener接口，可监听ServletContext的状态，在web服务器的启动，读取Spring的配置文件，创建Spring的IOC容器。web应用中必须在web.xml中配置

```xml
<!-- 配置spring的监听器，在服务器启动时加载spring的配置文件   -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<!-- 设置Spring配置文件自定义的位置和名称   -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring.xml</param-value>
</context-param>
```

#### 1.2、准备工作

①创建Maven Module

②导入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tzadok.ssm</groupId>
    <artifactId>SSM</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <spring.version>5.3.1</spring.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!--springmvc-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Mybatis核心 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.7</version>
        </dependency>

        <!--mybatis和spring的整合包-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>

        <!-- 连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.9</version>
        </dependency>

        <!-- junit测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <!-- MySQL驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.16</version>
        </dependency>

        <!-- log4j日志 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.2.0</version> </dependency>

        <!-- 日志 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>

        <!-- ServletAPI -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.1</version>
        </dependency>

        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
        </dependency>

        <!-- Spring5和Thymeleaf整合包 -->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
            <version>3.0.12.RELEASE</version>
        </dependency>

    </dependencies>

</project>
```

③创建表和数据

```mysql
/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : ssm

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 11/08/2022 11:04:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_emp
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp`  (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`emp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_emp
-- ----------------------------
INSERT INTO `t_emp` VALUES (1, 'Marcus', 59, '男', 'dim@mail.com');
INSERT INTO `t_emp` VALUES (2, 'Helen', 55, '女', 'davish@gmail.com');
INSERT INTO `t_emp` VALUES (3, 'Rhonda', 45, '女', 'rthom@mail.com');
INSERT INTO `t_emp` VALUES (4, 'Josephine', 5, '女', 'josephinefo1993@gmail.com');
INSERT INTO `t_emp` VALUES (5, 'Angela', 43, '女', 'angelahol@gmail.com');
INSERT INTO `t_emp` VALUES (6, 'Kathleen', 30, '女', 'kathleenco@mail.com');
INSERT INTO `t_emp` VALUES (7, 'Jose', 37, '男', 'jjor1954@gmail.com');
INSERT INTO `t_emp` VALUES (8, 'Larry', 48, '男', 'larrreyes@gmail.com');
INSERT INTO `t_emp` VALUES (9, 'Anthony', 23, '男', 'gutierrezanthony@gmail.com');
INSERT INTO `t_emp` VALUES (10, 'Andrew', 33, '男', 'elliand8@gmail.com');
INSERT INTO `t_emp` VALUES (11, 'Herbert', 57, '男', 'spencerherb@mail.com');
INSERT INTO `t_emp` VALUES (12, 'Ethel', 21, '女', 'johnsoneth@gmail.com');
INSERT INTO `t_emp` VALUES (13, 'Charlotte', 49, '女', 'moore7@gmail.com');
INSERT INTO `t_emp` VALUES (14, 'Susan', 3, '女', 'gardsu@gmail.com');
INSERT INTO `t_emp` VALUES (15, 'Russell', 59, '男', 'russelln@gmail.com');
INSERT INTO `t_emp` VALUES (16, 'Alfred', 57, '男', 'salazaralfred@mail.com');
INSERT INTO `t_emp` VALUES (17, 'Theodore', 2, '男', 'reyes6@gmail.com');
INSERT INTO `t_emp` VALUES (18, 'Alfred', 25, '男', 'alfredmendo@gmail.com');
INSERT INTO `t_emp` VALUES (19, 'Eva', 14, '女', 'evar77@gmail.com');
INSERT INTO `t_emp` VALUES (20, 'Clarence', 12, '男', 'clboyd@gmail.com');
INSERT INTO `t_emp` VALUES (21, 'Jennifer', 14, '女', 'simjen@gmail.com');
INSERT INTO `t_emp` VALUES (22, 'Marvin', 36, '男', 'marjim@gmail.com');
INSERT INTO `t_emp` VALUES (23, 'Cindy', 13, '女', 'alvarezcindy@gmail.com');
INSERT INTO `t_emp` VALUES (24, 'Marie', 25, '女', 'florm@mail.com');
INSERT INTO `t_emp` VALUES (25, 'Fred', 36, '男', 'fdaniels@gmail.com');
INSERT INTO `t_emp` VALUES (26, 'Steven', 36, '男', 'ls115@mail.com');
INSERT INTO `t_emp` VALUES (27, 'Sean', 38, '男', 'gibssean6@gmail.com');
INSERT INTO `t_emp` VALUES (28, 'Eleanor', 44, '女', 'scotteleanor@gmail.com');
INSERT INTO `t_emp` VALUES (29, 'Elizabeth', 33, '女', 'elizabeth63@mail.com');
INSERT INTO `t_emp` VALUES (30, 'Judith', 44, '女', 'adamsju@mail.com');
INSERT INTO `t_emp` VALUES (31, 'Thelma', 20, '女', 'warrenth@gmail.com');
INSERT INTO `t_emp` VALUES (32, 'Craig', 11, '男', 'frcra@gmail.com');
INSERT INTO `t_emp` VALUES (33, 'Julie', 15, '女', 'juliestewart7@gmail.com');
INSERT INTO `t_emp` VALUES (34, 'Johnny', 1, '男', 'webb914@gmail.com');
INSERT INTO `t_emp` VALUES (35, 'Elizabeth', 47, '女', 'teliz316@gmail.com');
INSERT INTO `t_emp` VALUES (36, 'Manuel', 9, '男', 'crawfman@gmail.com');
INSERT INTO `t_emp` VALUES (37, 'Randy', 52, '男', 'randyharri@gmail.com');
INSERT INTO `t_emp` VALUES (38, 'Don', 55, '男', 'robertsdon69@gmail.com');
INSERT INTO `t_emp` VALUES (39, 'Rose', 37, '女', 'rosfer@gmail.com');
INSERT INTO `t_emp` VALUES (40, 'Esther', 37, '女', 'nguyenesther55@mail.com');
INSERT INTO `t_emp` VALUES (41, 'Keith', 38, '男', 'davkeith@gmail.com');
INSERT INTO `t_emp` VALUES (42, 'Sharon', 58, '女', 'shaj@mail.com');
INSERT INTO `t_emp` VALUES (43, 'Bobby', 34, '男', 'bobbywhite9@gmail.com');
INSERT INTO `t_emp` VALUES (44, 'Ethel', 11, '女', 'forde@gmail.com');
INSERT INTO `t_emp` VALUES (45, 'Harold', 50, '男', 'haroldcook@gmail.com');
INSERT INTO `t_emp` VALUES (46, 'Andrew', 27, '男', 'weaa@gmail.com');
INSERT INTO `t_emp` VALUES (47, 'Kathryn', 1, '女', 'owkathr226@gmail.com');
INSERT INTO `t_emp` VALUES (48, 'Jason', 20, '男', 'jason913@gmail.com');
INSERT INTO `t_emp` VALUES (49, 'Philip', 45, '男', 'schphilip@gmail.com');
INSERT INTO `t_emp` VALUES (50, 'Peggy', 12, '女', 'ramos803@gmail.com');

SET FOREIGN_KEY_CHECKS = 1;

```

#### **4.3**、配置**web.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--配置Spring的编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--配置处理请求方式的编码   -->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- 配置SpringMVC的前端控制器DispatcherServlet   -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 设置SpringMVC配置文件自定义的位置和名称  -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <!--  将DispatcherServlet的初始化时间提前到服务器启动时      -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!-- 配置spring的监听器，在服务器启动时加载spring的配置文件   -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- 设置Spring配置文件自定义的位置和名称   -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring.xml</param-value>
    </context-param>
</web-app>
```

#### **1.4**、创建**SpringMVC**的配置文件并配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 配置Controller扫描组件   -->
    <context:component-scan base-package="com.tzadok.controller"></context:component-scan>

    <!-- 配置视图解析器   -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <!-- src/main/webapp/WEB-INF -->
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/templates/"/>
                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8" />
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

    <!-- 开启默认的servlet处理静态资源   -->
    <mvc:default-servlet-handler/>

    <!-- 开启注解驱动    -->
    <mvc:annotation-driven/>

    <!-- 配置视图控制器   -->
    <!--suppress SpringXmlModelInspection -->
    <mvc:view-controller path="/" view-name="index" />

    <!-- 配置文件上传解析器   -->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>

    <!-- 配置拦截器   -->

    <!-- 配置异常处理解析   -->
</beans>
```

#### 4.5、搭建**MyBatis**环境

①创建属性文**jdbc.properties**

```jdbc
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC
jdbc.username=root
jdbc.password=000000
```

②创建MyBatis的核心配置文件mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--   将下划线映射为驼峰     -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```

③创建Mapper接口和映射文件

```java
package com.tzadok.mapper;

import com.tzadok.pojo.Emp;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:44:02
 * @project SSM-SpringMVC
 * @description:
 */
public interface EmpMapper {

    /**
     * 查询所有的员工信息
     * @return
     */
    List<Emp> getAllEmployee();

    /**
     * 添加员工
     */
    void saveEmployee(Emp emp);

    /**
     * 根据id查找员工
     * @param id
     * @return
     */
    Emp getEmployeeById(Integer id);

    /**
     * 修改用户信息
     * @param emp
     */
    void updateEmployee(Emp emp);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteEmp(Integer id);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.tzadok.mapper.EmpMapper">

<!--    List<Emp> getAllEmployee();-->
    <select id="getAllEmployee" resultType="Emp">
        select * from t_emp
    </select>

<!--    void saveEmployee(Emp emp);-->
    <insert id="saveEmployee" parameterType="emp">
        insert into t_emp values (null,#{empName},#{age},#{sex},#{email})
    </insert>

<!--    Emp getEmployeeById(Integer id);-->
    <select id="getEmployeeById" resultType="emp">
        select * from t_emp where emp_id = #{id}
    </select>

    <update id="updateEmployee">
        update t_emp
        <set>
            <if test="empName!=null and empName!=''">
                emp_name = #{empName},
            </if>
            <if test="sex!=null and sex!=''">
                sex=#{sex},
            </if>
            <if test="age!=null and age!=''">
                age=#{age},
            </if>
            <if test="email!=null and email!=''">
                email=#{email},
            </if>
        </set>
        where emp_id = #{empId}
    </update>

<!--    void deleteEmp(Integer id);-->
    <delete id="deleteEmp">
        delete from t_emp where emp_id = #{empId}
    </delete>

</mapper>
```

④创建日志文件log4j.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">

<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8" />
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m  (%F:%L) \n" />
        </layout>
    </appender>
    <logger name="java.sql">
        <level value="debug" />
    </logger>
    <logger name="org.apache.ibatis">
        <level value="info" />
    </logger>
    <root>
        <level value="debug" />
        <appender-ref ref="STDOUT" />
    </root>
</log4j:configuration>
```

#### 1.6、创建Spring的配置文件并配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 配置扫描组件(除控制层以外)   -->
    <context:component-scan base-package="com.tzadok" >
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!-- 引入jdbc.properties   -->
    <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>

    <!-- 配置数据源   -->
    <bean id="datasource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>

    <!-- 配置事务管理器   -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="datasource"></property>
    </bean>

    <!--
        开启事务的注解驱动
        将使用注解@Transactional标识的方法或类中所有的方法进行事务管理
     -->
    <tx:annotation-driven transaction-manager="transactionManager"/>

    <!-- 配置SqlSessionFactoryBean,可以直接在Spring的IOC中获取SqlSessionFactory   -->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--  设置Mybatis的核心配置文件的路径      -->
        <property name="configLocation" value="classpath:mybatis-comfig.xml"></property>
        <!--  设置数据源      -->
        <property name="dataSource" ref="datasource"></property>
        <!--  设置类型别名所对应的包      -->
        <property name="typeAliasesPackage" value="com.tzadok.pojo"></property>
        <!--  设置数据源      -->
        <property name="configurationProperties" value="true"></property>
        <!--  设置映射文件的路径，只有映射文件的包和mapper接口的包不一致时需要设置      -->
<!--        <<property name="mapperLocations" value="classpath:mappers/*.xml"></property>-->
        <property name="plugins">
            <array>
                <!--  配置分页插件     -->
                <bean class="com.github.pagehelper.PageInterceptor"></bean>
            </array>
        </property>
    </bean>

    <!--
        配置mapper接口的扫描，可以将指定包下所有的mapper接口，
        通过SqlSession创建代理实现类对象，并将这些对象交给IOC容器管理
     -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.tzadok.mapper"></property>
    </bean>

</beans>
```

#### 1.7、测试功能

**①创建组件**

实体类Emp

```java
package com.tzadok.pojo;

/**
 * @author Tzadok
 * @date 2022/8/10 22:41:18
 * @project SSM-SpringMVC
 * @description:
 */
public class Emp {

    private Integer empId;
    private String empName;
    private Integer age;
    private String sex;
    private String email;

    public Emp() {
    }

    public Emp(Integer empId, String empName, Integer age, String sex, String email) {
        this.empId = empId;
        this.empName = empName;
        this.age = age;
        this.sex = sex;
        this.email = email;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

创建控制层组件EmpController

```java
package com.tzadok.controller;

import com.github.pagehelper.PageInfo;
import com.tzadok.pojo.Emp;
import com.tzadok.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:19:37
 * @project SSM-SpringMVC
 * @description:
 * 查询所有的员工信息-->/employee-->get
 * 查询分页的员工信息-->/employee/page/1-->get
 * 根据id查询员工信息-->/employee/id-->get
 * 跳转到添加页面-->/to/Add-->get
 * 添加员工信息-->/employee-->post
 * 修改员工信息-->/employee-->put
 * 删除员工信息-->/employee-->delete
 */
@Controller
public class EmpController {

    @Autowired
    private EmpService empService;
	//查看全部员工信息并分页
    @GetMapping ("/employee/page/{pageNum}")
    public String getEmployeePage(@PathVariable("pageNum") Integer pageNum,Model model){
        //获取员工的分页信息
        PageInfo<Emp> page = empService.getEmployeePage(pageNum);
        //将分页数据共享到请求域
        model.addAttribute("page",page);
        //跳转到employee-list页面
        return "employee-list";
    }
	//查看全部员工信息
    @GetMapping ("/employee")
    public String getAllEmployee(Model model){
        //查询所有的员工信息
        List<Emp> list = empService.getAllEmployee();
        //将所有的员工信息共享到请求域
        model.addAttribute("list",list);
        //跳转到employee-list页面
        return "employee-list";
    }
	//跳转添加页面
    @GetMapping("/to/Add")
    public String toAdd(){
        return "employee-add";
    }
	//保存用户信息
    @PostMapping("/employee")
    public String saveEmployee(Emp emp){
        empService.saveEmployee(emp);
        return "redirect:/employee/page/1";
    }
	//根据ID查询用户信息（为修改操作做回显功能）
    @GetMapping("/employee/{id}")
    public String getEmployeeById(@PathVariable("id") Integer id,Model model){
        Emp emp = empService.getEmployeeById(id);
        model.addAttribute("emp",emp);
        return "employee-update";
    }
	//跳转修改页面
    @GetMapping("/to/Update")
    public String toUpdate(){
        return "employee-update";
    }
	//更改员工信息
    @PutMapping ("/updateEmployee")
    public String updateEmployee(Emp emp){
        System.out.println(emp);
        empService.updateEmployee(emp);
        return "redirect:/employee/page/1";
    }
	//根据id删除员工信息
    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable Integer id){
        empService.deleteEmp(id);
        return "redirect:/employee/page/1";

    }
}
```

创建接口EmpService

```java
package com.tzadok.service;

import com.github.pagehelper.PageInfo;
import com.tzadok.pojo.Emp;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:28:57
 * @project SSM-SpringMVC
 * @description:
 */
public interface EmpService {

    /**
     * 查询所有的员工信息
     * @return
     */
    List<Emp> getAllEmployee();

    /**
     * 获取员工的分页信息
     * @param pageNum
     * @return
     */
    PageInfo<Emp> getEmployeePage(Integer pageNum);

    /**
     * 添加员工
     */
    void saveEmployee(Emp emp);

    /**
     * 根据员工号查找员工
     * @param id
     * @return
     */
    Emp getEmployeeById(Integer id);

    /**
     * 修改用户
     * @param emp
     */
    void updateEmployee(Emp emp);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteEmp(Integer id);
}
```

创建实现类EmpServiceImpl

```java
package com.tzadok.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzadok.mapper.EmpMapper;
import com.tzadok.pojo.Emp;
import com.tzadok.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:29:15
 * @project SSM-SpringMVC
 * @description:
 */
@Service
@Transactional
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Emp> getAllEmployee() {
        return empMapper.getAllEmployee();
    }

    @Override
    public PageInfo<Emp> getEmployeePage(Integer pageNum) {
        //开启分页功能
        PageHelper.startPage(pageNum,5);
        //查询所有的员工信息
        List<Emp> list = empMapper.getAllEmployee();
        //获取分页相关数据
        PageInfo<Emp> page = new PageInfo<>(list,5);
        return page;
    }

    @Override
    public void saveEmployee(Emp emp) {
        empMapper.saveEmployee(emp);
    }

    @Override
    public Emp getEmployeeById(Integer id) {
        return empMapper.getEmployeeById(id);
    }

    @Override
    public void updateEmployee(Emp emp) {
        System.out.println(emp);
        empMapper.updateEmployee(emp);
    }

    @Override
    public void deleteEmp(Integer id) {
        empMapper.deleteEmp(id);
    }
}
```

**②创建页面**

**index.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
  <h1>index.html</h1>
    <a th:href="@{/employee/page/1}">查看员工的分页信息</a>
</body>
</html>
```

**employee-list.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>员工信息</title>
    <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
</head>
<body>
<div id="app">
    <table>
        <tr>
            <th colspan="6">员工列表</th>
        </tr>
        <tr>
            <th>序号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>性别</th>
            <th>邮箱</th>
            <th>操作(<a th:href="@{/to/Add}" >添加</a>)</th>
        </tr>
        <tr th:each="emp,status : ${page.list}">
            <th th:text="${status.count}"></th>
            <th th:text="${emp.empName}"></th>
            <th th:text="${emp.age}"></th>
            <th th:text="${emp.sex}"></th>
            <th th:text="${emp.email}"></th>
            <th>
                <a th:href="@{'/employee/'+${emp.empId}}">修改</a>
                <a th:href="@{'/employee/' + ${emp.empId}}"  @click="deleteEmployee()">删除</a>
            </th>
        </tr>
    </table>
    <div style="text-align: center">
        <a th:if="${page.hasPreviousPage}" th:href="@{/employee/page/1}">首页</a>
        <a th:if="${page.hasPreviousPage}" th:href="@{'/employee/page/' + ${page.prePage}}">上一页</a>
        <span th:each="num : ${page.navigatepageNums}">
            <a th:if="${page.pageNum == num}" style="color: red" th:href="@{'/employee/page/' + ${num}}" th:text="'[' +${num}+']'"></a>
            <a th:if="${page.pageNum != num}" th:href="@{'/employee/page/' + ${num}}" th:text="${num}"></a>
    </span>
        <a th:if="${page.hasNextPage}" th:href="@{'/employee/page/' + ${page.nextPage}}">下一页</a>
        <a th:if="${page.hasNextPage}" th:href="@{'/employee/page/' + ${page.pages}}">末页</a>
    </div>
    <form method="post">
        <input type="hidden" name="_method" value="delete">
    </form>
</div>
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
<script type="text/javascript">
    var vue = new Vue({
        el:"#app",
        methods:{
            deleteEmployee(){
                //获取form表单
                var form = document.getElementsByTagName("form")[0];
                //将超链接的href属性值赋值给form表单的action属性
                //event.target当前触发事件的标签
                form.action = event.target.href;
                //表单提交
                form.submit();
                //阻止超链接的默认行为
                event.preventDefault();
            }
        }
    })
</script>
</body>
</html>
```

**employee-add.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>添加员工</title>
    <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
</head>
<body>
<form th:action="@{/employee}" th:method="post">
  <table>
    <tr>
      <th colspan="2">添加员工</th>
    </tr>
    <tr>
      <td>姓名</td>
      <td><input type="text" name="empName"></td>
    </tr>
    <tr>
      <td>年龄</td>
      <td><input type="text" name="age"></td>
    </tr>
    <tr>
      <td>性别</td>
      <td>
        <input type="radio" name="sex" value="男">male
        <input type="radio" name="sex" value="女">female
      </td>
    </tr>
    <tr>
      <td>邮箱</td>
      <td><input type="text" name="email"></td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="添加">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
```

employee-update.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>添加员工</title>
    <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
</head>
<body>
<form th:action="@{/updateEmployee}" th:method="post">
  <input type="hidden" name="_method" value="put">
  <input type="hidden" name="empId" th:value="${emp.empId}">
  <table>
    <tr>
      <th colspan="2">添加员工</th>
    </tr>
    <tr>
      <td>姓名</td>
      <td><input type="text" name="empName" th:value="${emp.empName}"></td>
    </tr>
    <tr>
      <td>年龄</td>
      <td><input type="text" name="age" th:value="${emp.age}"></td>
    </tr>
    <tr>
      <td>性别</td>
      <td>
        <input type="radio" name="sex" value="男" th:field="${emp.sex}">male
        <input type="radio" name="sex" value="女" th:field="${emp.sex}">female
      </td>
    </tr>
    <tr>
      <td>邮箱</td>
      <td><input type="text" name="email" th:value="${emp.email}"></td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="修改">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
```