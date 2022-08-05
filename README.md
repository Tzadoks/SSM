## SSM工作原理

SSM（Spring+SpringMVC+MyBatis）框架集由Spring、MyBatis两个开源框架整合而成（SpringMVC是Spring中的部分内容）。常作为数据源较简单的web项目的框架。

#### Spring

Spring就像是整个项目中装配bean的大工厂，在配置文件中可以指定使用特定的参数去调用实体类的构造方法来实例化对象。也可以称之为项目中的粘合剂。
Spring的核心思想是IoC（控制反转），即不再需要程序员去显式地new一个对象，而是让Spring框架帮你来完成这一切。

#### SpringMVC

SpringMVC在项目中拦截用户请求，它的核心Servlet即DispatcherServlet承担中介或是前台这样的职责，将用户请求通过HandlerMapping去匹配Controller，Controller就是具体对应请求所执行的操作。SpringMVC相当于SSH框架中struts。

#### MyBatis

MyBatis是对jdbc的封装，它让数据库底层操作变的透明。MyBatis的操作都是围绕一个sqlSessionFactory实例展开的。MyBatis通过配置文件关联到各实体类的Mapper文件，Mapper文件中配置了每个类对数据库所需进行的sql语句映射。在每次与数据库交互时，通过sqlSessionFactory拿到一个sqlSession，再执行sql命令。
页面发送请求给控制器，控制器调用业务层处理逻辑，逻辑层向持久层发送请求，持久层与数据库交互，后将结果返回给业务层，业务层将处理逻辑发送给控制器，控制器再调用视图展现数据。



SSM是Sping+SpringMVC+MyBatis集成的框架。是标准的MVC模式，将整个系统划分为表现层，controller层，service层，DAO层四层

视图层：View层 → 表现层(springMVC):Controller层(Handler层):→ 业务层(Spring):Service层 → 持久层(Mybatis):Dao层(Mapper层)

View层与Controller层联系紧密;
Service层与Dao层代码设计可更加模块化,提高代码复用率,降低耦合性;
持久层：DAO层（mapper层）（属于mybatis模块）
DAO层：主要负责与数据库进行交互设计，用来处理数据的持久化工作。

DAO层的设计首先是设计DAO的接口，也就是项目中你看到的Dao包。

然后在Spring的xml配置文件中定义此接口的实现类，就可在其他模块中调用此接口来进行数据业务的处理，而不用关心接口的具体实现类是哪个类，这里往往用到的就是反射机制，DAO层的jdbc.properties数据源配置，以及有 关数据库连接的参数都在Spring的配置文件中进行配置。

有的项目里面Dao层，写成mapper，当成一个意思理解。

mapper层=dao层，现在用MyBatis逆向工程生成的mapper层，其实就是dao层。对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的，而service层是针对我们controller，也就是针对我们使用者。service的impl是把mapper和service进行整合的文件。

业务层：Service层（属于Spring模块）
Service层：主要负责业务模块的逻辑应用设计。也就是项目中你看到的Service包。

Service层的设计首先是设计接口，再设计其实现的类。也就是项目中你看到的service+impl包。

接着再在Spring的xml配置文件中配置其实现的关联。这样我们就可以在应用中调用Service接口来进行业务处理。

最后通过调用DAO层已定义的接口，去实现Service具体的实现类。

Service层的业务实现，具体要调用到已定义的DAO层的接口。

Service层，存放业务逻辑处理，也是一些关于数据库处理的操作，但不是直接和数据库打交道，它有接口还有接口的实现方法，在接口的实现方法中需要导入mapper层，mapper层是直接跟数据库打交道的，他也是个接口，只有方法名字，具体实现在mapper.xml文件里，service是供我们使用的方法。

控制层/表现层：Controller层（Handler层） （属于springMVC模块）
Controller层：主要负责具体的业务模块流程控制，也就是controller包。

Controller层通过要调用Service层的接口来控制业务流程，控制的配置也同样是在Spring的xml配置文件里面，针对具体的业务流程，会有不同的控制器。

controller层，控制器，导入service层，因为service中的方法是我们使用到的，controller通过接收前端传过来的参数进行业务操作，在返回一个指定的路径或者数据表。

View层 （属于springMVC模块）
负责前台jsp页面的展示，此层需要与Controller层结合起来开发。

Jsp发送请求，controller接收请求，处理，返回，jsp回显数据。

model层=entity层。存放实体类，与数据库中的属性值基本保持一致。

SSM使用SpringMVC负责请求的转发和视图管理

Spring实现业务对象管理，MyBatis作为数据对象的持久化引擎；

数据持久化操作就是指，把数据放到持久化的介质中，同时提供增删改查操作，比如数据通过hibernate插入到数据库中。

————————————————
参考文章：https://blog.csdn.net/weixin_41924879/article/details/101034761