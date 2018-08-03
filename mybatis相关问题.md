## mybatis相关问题

----

##### 1.Mapper在spring管理下其实是单例，为什么可以是一个单例？ SCOPE -> application

mybatis-spring.jar中

SqlSessionTemplate类对sqlSession做了一层代理:

```java
public class SqlSessionTemplate implements SqlSession, DisposableBean {
	...
	public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
      PersistenceExceptionTranslator exceptionTranslator) {
	...
	//构造SqlSessionTemplate的时候在此处可见代理了SqlSession
    this.sqlSessionProxy = (SqlSession) newProxyInstance(
        SqlSessionFactory.class.getClassLoader(),
        new Class[] { SqlSession.class },
        new SqlSessionInterceptor());
  }
  ...
  private class SqlSessionInterceptor implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      //此处先获取sqlSession
      SqlSession sqlSession = getSqlSession(
          SqlSessionTemplate.this.sqlSessionFactory,
          SqlSessionTemplate.this.executorType,
          SqlSessionTemplate.this.exceptionTranslator);
      try {
        Object result = method.invoke(sqlSession, args);
        if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
         //提交sqlSession
          sqlSession.commit(true);
        }
        return result;
      }  catch (Throwable t) {
        	...
        	//关闭sqlSession
          closeSqlSession(sqlSession, SqlSessionTemplate.this.sqlSessionFactory);
          sqlSession = null;
          ...
        }
        throw unwrapped;
      } finally {
      	//关闭sqlSession
        if (sqlSession != null) {
          closeSqlSession(sqlSession, SqlSessionTemplate.this.sqlSessionFactory);
        }
      }
    }
  }
```

##### 2.MyBatis在Spring集成下没有mapper的xml文件会不会报错，为什么？

```
待研究
```

##### 3.[TypeHandler手写](https://github.com/zhuyizhuo/simple-mybatis/blob/master/src/main/java/com.zhuyizhuo.java.mybatis/handler/VarcharTypeHandler.java)



##### 4.手写Plugin,多个interceptor到底谁先执行？顺序由谁决定的？

```
plugin只能切以下handler
Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
ParameterHandler (getParameterObject, setParameters)
ResultSetHandler (handleResultSets, handleOutputParameters)
StatementHandler (prepare, parameterize, batch, update, query)

前提,如果同时存在以上4个plugin，并且同一类型的plugin存在多个，并且执行的sql语句符合所有plugin定义的切面方法，同时触发plugin执行的情况下,则执行顺序如下:
不同类型的plugin执行顺序为: Executor、StatementHandler、ParameterHandler、ResultSetHandler
不同类型的plugin执行顺序由jdbc的执行顺序决定。
相同类型的plugin执行顺序为：xml中后配置的插件先执行
相同类型的plugin执行顺序由在xml中的位置决定,位置靠后的先执行,原理是因为每次注册plugin时都会对之前的同类型plugin做一次代理，最后注册的plugin在最外层,执行时由外向内,返回时由内向外.
```

插件代理代码如下:

```java
public class Plugin implements InvocationHandler {
	...
  public static Object wrap(Object target, Interceptor interceptor) {
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    Class<?> type = target.getClass();
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
      //此处对插件进行代理
      return Proxy.newProxyInstance(
          type.getClassLoader(),
          interfaces,
          new Plugin(target, interceptor, signatureMap));
    }
    return target;
  }
	...
}
```

##### 5.Mapper 作者为什么要这样设计？为什么不是一个class而是一个interface?

```

```

##### 6.org.apache.ibatis.executor.BaseExecutor#queryFromDatabase 322行这行代码的意义,代码摘抄如下

```java
public abstract class BaseExecutor implements Executor {
	...
    private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
        List<E> list;
        //这行代码的意义?
        localCache.putObject(key, EXECUTION_PLACEHOLDER);
        try {
          list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
        } finally {
          localCache.removeObject(key);
        }
        localCache.putObject(key, list);
        if (ms.getStatementType() == StatementType.CALLABLE) {
          localOutputParameterCache.putObject(key, parameter);
        }
        return list;
      }
  ...
}  
```

```
查找发现该占位符在BaseExecutor的内部类DeferredLoad.canLoad()方法中有调用,代码摘抄如下:
private static class DeferredLoad {
...
    public boolean canLoad() {
      return localCache.getObject(key) != null && localCache.getObject(key) != EXECUTION_PLACEHOLDER;
    }
...
}
```



##### 7.MyBatis的plugin实现机制

```
对插件支持的几个接口进行了动态代理,返回的代理对象,执行对应方法之前首先执行了插件的方法

```

##### 8.lazy loading 是怎么做到的？

```

```
##### 9.怎么验证一级缓存的存在?

[代码链接](https://github.com/zhuyizhuo/simple-mybatis/blob/master/src/main/java/com.zhuyizhuo.java.mybatis/test/TestSimpleQuery.java)

```java
同一个sqlsession连续两次查询 第二次会命中mybatis一级缓存
1.写一个测试,连续两次查询数据库,
2.在第一次查询结束的地方打断点,手动更改数据的值
3.执行断点后的第二次查询,验证查询命中一级缓存
代码参考TestSimpleQuery.testQueryCache方法
```

##### 10.验证N+1问题

[代码链接](https://github.com/zhuyizhuo/simple-mybatis/blob/master/src/main/java/com.zhuyizhuo.java.mybatis/test/TestComplexQuery.java)

```
mybatis官网对N+1问题描述如下:
While this approach is simple, it will not perform well for large data sets or lists. This problem is known as the "N+1 Selects Problem". In a nutshell, the N+1 selects problem is caused like this:
You execute a single SQL statement to retrieve a list of records (the "+1").
For each record returned, you execute a select statement to load details for each (the "N").

意思是如果在嵌套查询的情况下,如果查询出单条结果,则不存在问题
如果查询出多条结果,对查询出的多条结果进行嵌套查询,则会出现N+1次查询的情况
代码参考TestComplexQuery.selectUserOrderLists方法,数据要求:首次查询出的结果是集合即可
```

##### 11. org.apache.ibatis.binding.MapperProxy#invoke 这个类的53行什么时候执行？

12. 手写1.0
13. 2.0版本的基础上，用annotation 
14. 2.0版本加入plugin功能