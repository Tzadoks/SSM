### MyBatis

#### 1、mybatis核心配置文件

文件名：mybatis-config.xml

存放位置：是src/main/resources目录下

```xml
<?xml version="1.0" encoding="UTF-8" ?> 
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd"> <configuration> 
    <!--设置连接数据库的环境--> 
    <environments default="development"> 
        <environment id="development"> 
            <transactionManager type="JDBC"/> 
            <dataSource type="POOLED"> 
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/> 
                <property name="url" value="jdbc:mysql://localhost:3306/ssm? serverTimezone=UTC"/> 
                <property name="username" value="root"/> 
                <property name="password" value="123456"/> 
            </dataSource> 
        </environment> 
    </environments> 
    <!--引入映射文件--> 
    <mappers> 
        <package name="mappers/UserMapper.xml"/> 
    </mappers> 
</configuration>
```

#### 2、log4j配置文件

文件名：log4j.xml

存放位置：src/main/resources目录下

```xml
<?xml version="1.0" encoding="UTF-8" ?> 
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd"> 

<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/"> 
    
<appender name="STDOUT" class="org.apache.log4j.ConsoleAppender"> 
    <param name="Encoding" value="UTF-8" /> 
    <layout class="org.apache.log4j.PatternLayout"> 
        <param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m (%F:%L) \n" /> 	</layout> 
</appender> 
    
    <logger name="java.sql"> 
        <level value="debug" /> </logger> 
    <logger name="org.apache.ibatis">
        <level value="info" /> </logger> 
    <root><level value="debug" /> 
        <appender-ref ref="STDOUT" /> 
    </root> 
</log4j:configuration>
```

#### 3、自定义映射关系

##### 3.1、resultMap处理字段和属性的映射关系

```xml
<!--resultMap：设置自定义映射 
	属性： 
	id：表示自定义映射的唯一标识 
	type：查询的数据要映射的实体类的类型 
	子标签： 
    id：设置主键的映射关系 
    result：设置普通字段的映射关系
  	association：设置多对一的映射关系 
  	collection：设置一对多的映射关系 
  	属性： 
  	property：设置映射关系中实体类中的属性名 
  	column：设置映射关系中表中的字段名 
-->

<resultMap id="userMap" type="User"> 
    <id property="id" column="id"></id> 
    <result property="userName" column="user_name"></result> 
    <result property="password" column="password"></result> 
    <result property="age" column="age"></result> 
    <result property="sex" column="sex"></result> 
</resultMap> 

<!--List<User> testMohu(@Param("mohu") String mohu);--> 
<select id="testMohu" resultMap="userMap"> 
    <!--select * from t_user where username like '%${mohu}%'--> 
    select id,user_name,password,age,sex from t_user where user_name like concat('%',#{mohu},'%')
</select>
```

若字段名和实体类中的属性名不一致，但是字段名符合数据库的规则（使用_），实体类中的属性名符合Java的规则（使用驼峰）

此时也可通过以下两种方式处理字段名和实体类中的属性的映射关系

a>可以通过为字段起别名的方式，保证和实体类中的属性名保持一致

b>可以在MyBatis的核心配置文件中设置一个全局配置信息mapUnderscoreToCamelCase，可以在查询表中数据时，自动将_类型的字段名转换为驼峰

例如：字段名user_name，设置了mapUnderscoreToCamelCase，此时字段名就会转换为userName

##### 3.2、多对一映射处理

场景模拟：

查询员工信息以及员工所对应的部门信息

###### 8.2.1、级联方式处理映射关系

```xml
<resultMap id="empDeptMap" type="Emp"> 
    <id column="eid" property="eid"></id> 
    <result column="ename" property="ename"></result> 
    <result column="age" property="age"></result> 
    <result column="sex" property="sex"></result> 
    <result column="did" property="dept.did"></result> 
    <result column="dname" property="dept.dname"></result> 
</resultMap> 

<!--Emp getEmpAndDeptByEid(@Param("eid") int eid);--> 
<select id="getEmpAndDeptByEid" resultMap="empDeptMap"> 
    select emp.*,dept.* from t_emp emp left join t_dept dept on emp.did = dept.did where emp.eid = #{eid} 
</select>
```

###### 3.2.2、使用association处理映射关系

```xml
<resultMap id="empDeptMap" type="Emp"> 
    <id column="eid" property="eid"></id> 
    <result column="ename" property="ename"></result> 
    <result column="age" property="age"></result> 
    <result column="sex" property="sex"></result> 
    <association property="dept" javaType="Dept"> 
        <id column="did" property="did"></id> 
        <result column="dname" property="dname">
        </result> 
    </association> 
</resultMap> 

<!--Emp getEmpAndDeptByEid(@Param("eid") int eid);--> 
<select id="getEmpAndDeptByEid" resultMap="empDeptMap"> 
    select emp.*,dept.* from t_emp emp left join t_dept dept on emp.did = dept.did where emp.eid = #{eid} 
</select>
```

###### 3.2.3、分步查询

①查询员工信息

```java
/**
* 通过分步查询查询员工信息 
* @param eid 
* @return 
*/ 
Emp getEmpByStep(@Param("eid") int eid);
```

```xml
<resultMap id="empDeptStepMap" type="Emp"> 
    <id column="eid" property="eid"></id> 
    <result column="ename" property="ename"></result> 
    <result column="age" property="age"></result> 
    <result column="sex" property="sex"></result> 
    
    <!--select：设置分步查询，查询某个属性的值的sql的标识（namespace.sqlId） column：将sql以及查询结果中的某个字段设置为分步查询的条件 --> 
    <association property="dept"select="com.atguigu.MyBatis.mapper.DeptMapper.getEmpDeptByStep" column="did"> 
    </association> 
</resultMap> 

<!--Emp getEmpByStep(@Param("eid") int eid);--> 
<select id="getEmpByStep" resultMap="empDeptStepMap"> 
    select * from t_emp where eid = #{eid} 
</select>
```

②根据员工所对应的部门id查询部门信息

```java
/**
* 分步查询的第二步： 根据员工所对应的did查询部门信息 
* @param did 
* @return 
*/ 
Dept getEmpDeptByStep(@Param("did") int did);
```

```xml
<!--Dept getEmpDeptByStep(@Param("did") int did);--> 
<select id="getEmpDeptByStep" resultType="Dept"> 
    select * from t_dept where did = #{did} 
</select>
```

##### 3.3、一对多映射处理

###### 3.3.1、collection

```java
/**
* 根据部门id查新部门以及部门中的员工信息 
* @param did 
* @return 
*/ 
Dept getDeptEmpByDid(@Param("did") int did);
```

```xml
<resultMap id="deptEmpMap" type="Dept"> 
    <id property="did" column="did"></id> 
    <result property="dname" column="dname"></result> 
    <!--ofType：设置collection标签所处理的集合属性中存储数据的类型 --> 
    <collection property="emps" ofType="Emp"> 
        <id property="eid" column="eid"></id> 
        <result property="ename" column="ename"></result> 
        <result property="age" column="age"></result> 
        <result property="sex" column="sex"></result> 
    </collection> 
</resultMap> 
<!--Dept getDeptEmpByDid(@Param("did") int did);--> 
<select id="getDeptEmpByDid" resultMap="deptEmpMap"> 
    select dept.*,emp.* from t_dept dept left join t_emp emp on dept.did = emp.did where dept.did = #{did} 
</select>
```

###### 3.3.2、分步查询

①查询部门信息

```java
/**
* 分步查询部门和部门中的员工 
* @param did 
* @return 
*/ 
Dept getDeptByStep(@Param("did") int did);
```

```xml
<resultMap id="deptEmpStep" type="Dept"> 
    <id property="did" column="did"></id> 
    <result property="dname" column="dname"></result> 
    <collection property="emps" fetchType="eager" select="com.atguigu.MyBatis.mapper.EmpMapper.getEmpListByDid" column="did"> 
    </collection> 
</resultMap> 
<!--Dept getDeptByStep(@Param("did") int did);--> 
<select id="getDeptByStep" resultMap="deptEmpStep"> 
    select * from t_dept where did = #{did} 
</select>
```

②根据部门id查询部门中的所有员工

```java
/**
* 根据部门id查询员工信息 
* @param did 
* @return 
*/ 
List<Emp> getEmpListByDid(@Param("did") int did);
```

```xml
<!--List<Emp> getEmpListByDid(@Param("did") int did);--> 
<select id="getEmpListByDid" resultType="Emp"> select * from t_emp where did = #{did} 
</select>
```

分步查询的优点：可以实现延迟加载

但是必须在核心配置文件中设置全局配置信息：

lazyLoadingEnabled：延迟加载的全局开关。当开启时，所有关联对象都会延迟加载

aggressiveLazyLoading：当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载

此时就可以实现按需加载，获取的数据是什么，就只会执行相应的sql。此时可通过association和collection中的fetchType属性设置当前的分步查询是否使用延迟加载， fetchType="lazy(延迟加载)|eager(立即加载)"

#### 4、MyBatis的缓存

##### 4.1、MyBatis的一级缓存

一级缓存是SqlSession级别的，通过同一个SqlSession查询的数据会被缓存，下次查询相同的数据，就会从缓存中直接获取，不会从数据库重新访问

使一级缓存失效的四种情况：

\1) 不同的SqlSession对应不同的一级缓存

\2) 同一个SqlSession但是查询条件不同

\3) 同一个SqlSession两次查询期间执行了任何一次增删改操作

\4) 同一个SqlSession两次查询期间手动清空了缓存

##### 4.2、MyBatis的二级缓存

二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被缓存；此后若再次执行相同的查询语句，结果就会从缓存中获取

二级缓存开启的条件：

a>在核心配置文件中，设置全局配置属性cacheEnabled="true"，默认为true，不需要设置

b>在映射文件中设置标签<cache/>

c>二级缓存必须在SqlSession关闭或提交之后有效

d>查询的数据所转换的实体类类型必须实现序列化的接口

使二级缓存失效的情况：

两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效

##### 4.3、二级缓存的相关配置

在mapper配置文件中添加的cache标签可以设置一些属性：

①eviction属性：缓存回收策略，默认的是 LRU。

LRU（Least Recently Used） – 最近最少使用的：移除最长时间不被使用的对象。

FIFO（First in First out） – 先进先出：按对象进入缓存的顺序来移除它们。

SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。

WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。

②flushInterval属性：刷新间隔，单位毫秒

默认情况是不设置，也就是没有刷新间隔，缓存仅仅调用语句时刷新

③size属性：引用数目，正整数

代表缓存最多可以存储多少个对象，太大容易导致内存溢出

④readOnly属性：只读， true/false

true：只读缓存；会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了 很重要的性能优势。

false：读写缓存；会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是false。

##### 4.4、MyBatis缓存查询的顺序

先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以拿来直接使用。

如果二级缓存没有命中，再查询一级缓存

如果一级缓存也没有命中，则查询数据库

SqlSession关闭之后，一级缓存中的数据会写入二级缓存

#### 5、分页插件

limit index,pageSize

pageSize：每页显示的条数

pageNum：当前页的页码

index：当前页的起始索引，index=(pageNum-1)*pageSize

count：总记录数

totalPage：总页数

totalPage = count / pageSize;

if(count % pageSize != 0){

totalPage += 1;}

pageSize=4，pageNum=1，index=0 limit 0,4

pageSize=4，pageNum=3，index=8 limit 8,4

pageSize=4，pageNum=6，index=20 limit 8,4

首页 上一页 2 3 4 5 6 下一页 末页

##### 5.1、分页插件的使用步骤

**①添加依赖**

```xml
<dependency> 
    <groupId>com.github.pagehelper</groupId> 
    <artifactId>pagehelper</artifactId> 
    <version>5.2.0</version> 
</dependency>
```

**②配置分页插件**

在MyBatis的核心配置文件中配置插件

```xml
<plugins> 
<!--设置分页插件--> 
    <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin> 
</plugins>
```

##### 5.2、分页插件的使用

a>在查询功能之前使用PageHelper.startPage(int pageNum, int pageSize)开启分页功能

pageNum：当前页的页码

pageSize：每页显示的条数

b>在查询获取list集合之后，使用PageInfo<T> pageInfo = new PageInfo<>(List<T> list, int navigatePages)获取分页相关数据

list：分页之后的数据

navigatePages：导航分页的页码数

c>分页相关数据

PageInfo{

pageNum=8, pageSize=4, size=2, startRow=29, endRow=30, total=30, pages=8,

list=Page{count=true, pageNum=8, pageSize=4, startRow=28, endRow=32, total=30,

pages=8, reasonable=false, pageSizeZero=false},

prePage=7, nextPage=0, isFirstPage=false, isLastPage=true, hasPreviousPage=true,

hasNextPage=false, navigatePages=5, navigateFirstPage4, navigateLastPage8,

navigatepageNums=[4, 5, 6, 7, 8]

}

pageNum：当前页的页码

pageSize：每页显示的条数

size：当前页显示的真实条数

total：总记录数

pages：总页数

prePage：上一页的页码

nextPage：下一页的页码

isFirstPage/isLastPage：是否为第一页/最后一页

hasPreviousPage/hasNextPage：是否存在上一页/下一页

navigatePages：导航分页的页码数

navigatepageNums：导航分页的页码，[1,2,3,4,5]