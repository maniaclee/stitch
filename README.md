# stitch

统一配置读取客户端

支持zookeeper和properties方式

配置信息在zookeeper上修改后立即通知，支持本地缓存

支持不同环境的设置，如线上环境、线下环境的自动识别

### 使用

##### 配置环境信息

增加/data/config/env.properties，内容如下：

```properties
zkServer = localhost:2181
env = online
```

##### 设置配置信息

在zookeeper上增加相应节点，如配置信息根节点为/root，下面挂载很多应用（如app）的配置信息

如应用app下有一个 testKey= 123456的信息

则zookeeper路径为：/root/app/testKey

在online环境下路径为：/root/app/testKey.online = 123456

##### api

增加依赖

```xml
<dependency>
  <groupId>com.lvbby</groupId>
  <artifactId>stitch</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

##### 使用

```java
StitchClient proxy = StitchClientProxy.create("root", "app");//root为根路径，app为应用名称
System.out.println(proxy.get("testkey")); //读取相应的配置项
```

### 扩展

```java
Env env = new PropertiesEnvironment("/data/config/env.properties");//获取环境信息

KvService zk = new ZkKvService(env.getZkServer()); //使用zookeeper
KvService kvServiceCache = new KvServiceCache(zk); //缓存代理，推荐
StitchClient defaultStitchClient = new DefaultStitchClient(kvServiceCache);//创建api client
StitchClient proxy =  StitchClientProxy.proxy(decorators, defaultStitchClient);//可传入List<KeyDecorator>生成代理来对key进行定制化
```

**扩展接口KeyDecorator**

```java
public interface KeyDecorator {
    String decorateKey(String key);
}
```

**一些默认实现**

EnvDecorator 				- 将环境信息作为后缀加在key后面

PathDecorator				- 将路径加在key前面，作为zookeeper的路径，嵌套使用

PrefixKeyDecorator			- 增加前缀

SuffixKeyDecorator			- 增加后缀