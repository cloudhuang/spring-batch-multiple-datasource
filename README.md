Thanks KeatsPeeks, `AbstractRoutingDataSource` is a good starter for the solution, and here is a [good tutorial](http://howtodoinjava.com/spring/spring-orm/spring-3-2-5-abstractroutingdatasource-example/) on this part.

 Mainly the important parts are:
 1. define the lookup code
    ```java
    public class MyRoutingDataSource extends AbstractRoutingDataSource{
        @Override
        protected Object determineCurrentLookupKey() {
            String language = LocaleContextHolder.getLocale().getLanguage();
            System.out.println("Language obtained: "+ language);
            return language;
        }
    }
    ```
 2. register the multiple datasource
    ```java
    <bean id="dataSource" class="com.howtodoinjava.controller.MyRoutingDataSource">
       <property name="targetDataSources">
          <map key-type="java.lang.String">
             <entry key="en" value-ref="concreteDataSourceOne"/>
             <entry key="es" value-ref="concreteDataSourceTwo"/>
          </map>
       </property>
       <!-- <property name="defaultTargetDataSource" ref="concreteDataSourceOne"/> -->
    </bean>

    <bean id="abstractDataSource" class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close"
        p:driverClassName="${jdbc.driverClassName}"
        p:username="${jdbc.username}"
        p:password="${jdbc.password}" />
     
    <bean id="concreteDataSourceOne"
        parent="abstractDataSource"
        p:url="${jdbc.databaseurlOne}"/>
      
     <bean id="concreteDataSourceTwo"
        parent="abstractDataSource"
        p:url="${jdbc.databaseurlTwo}"/>
    ```

So after that, the problem is become to:
1. How to load datasource config properties when spring startup and config the corresponding `dataSource` using the config properties in database.

2. How to use multiple `dataSource` in spring batch

   Actually when I try to google it, seems this is a most common case, google give the suggestion search words - "spring batch multiple data sources", there are a lots articles, so I choose the answer in 

3. How to define the lookup code based on the spring batch jobs(steps)

   Typically this should be a business point, You need define the lookup strategy and can be injected to the `com.example.demo.datasource.CustomRoutingDataSource#determineCurrentLookupKey` to routing to the dedicated data source.

## Limitation

The really interesting is actually it is supports the multiple `dataSource`, but the db settings cannot store in the DB indeed. The reason is it will get the cycle dependencies issue:

![](https://ws1.sinaimg.cn/large/7e4476c3gy1fgstb3nr7qj20ca085t8r.jpg)

```shell
The dependencies of some of the beans in the application context form a cycle:

   batchConfiguration (field private org.springframework.batch.core.configuration.annotation.JobBuilderFactory com.example.demo.batch.BatchConfiguration.jobs)
      ↓
   org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration (field private java.util.Collection org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration.dataSources)
┌─────┐
|  routingDataSource defined in class path resource [com/example/demo/datasource/DataSourceConfiguration.class]
↑     ↓
|  targetDataSources defined in class path resource [com/example/demo/datasource/DataSourceConfiguration.class]
↑     ↓
|  myBatchConfigurer (field private java.util.Collection org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration.dataSources)
└─────┘
```

So obviously the solution is break the dependency between `dataSource` and `routingDataSource`

- Save the DB setting in properties
- Or involve other approach but not in the primary `dataSource`




## See Also
https://scattercode.co.uk/2013/11/18/spring-data-multiple-databases/
https://numberformat.wordpress.com/2013/12/27/hello-world-with-spring-batch-3-0-x-with-pure-annotations/

http://spring.io/guides/gs/batch-processing/

https://stackoverflow.com/questions/25256487/how-to-java-configure-separate-datasources-for-spring-batch-data-and-business-da