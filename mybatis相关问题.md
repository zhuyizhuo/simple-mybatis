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
不会报错. 原因待补充.
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

##### 7.MyBatis的plugin实现机制

```
对插件支持的几个接口进行了动态代理,返回的代理对象,执行对应方法之前首先执行了插件的方法

```

##### 8.lazy loading 是怎么做到的？

```

```