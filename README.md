# 手写Spring项目
## 1.Spring容器是什么？
Spring管理应用对象的配置和生命周期，是一种承载对象的容器。这些Bean可以被Spring进行创建一个单独的实例或者是每次创建都生成新的实例。（Singleton Or Protocol）

如果一个Bean对象交给Spring容器来进行管理的话，则从定义这个对象后，Bean的初始化、Bean的属性填充等操作都需要Spring来进行处理。最终会得到一个实例化完整的Bean对象。

### 1.1 设计思路
Spring容器需要存放对象，如果只是单独把一个对象实例存在列表或链表等容器中的话，可能会导致这个对象无法有效的找到。因此我们可以通过Bean的标识作为Key，BeanDefinition作为Value来存放对象。其中BeanDefinition包含了对象实例。
**因此我们这里选择HashMap来设计这个Bean的容器。**

首先，我们设计这个容器，我们还需要三个步骤的设计：
- Bean的定义
- Bean的注册
- Bean的获取

业务流程为：首先定义Bean(使用BeaDefinition来进行作为Bean的定义)，再将Bean注册Spring容器中，最后容器暴露能够去获取这个对象实体：**暂时Bean的名字为Key，对象是value**

### 1.2 问题
**Q1：为什么要使用BeanDefinition来作为Value？**

Answer：？

**Q2：为什么这个容器对象是ConcurrentHashMap？**

Answer：因为我们在对容器里面的Bean增删改查的时候，是有一个map的remove操作的。这里为了保证线程安全，所以使用ConcurrentHashMap。

## 2.实现Bean的定义、注册、获取
之前我们测试的时候发现，我们是自己new了一个Bean然后注入到了BeanDefinition。但是实际上这部分工作是spring给我们完成的。**另外也需要考虑到单例的问题，对于对象的二次获取应该还是之前的那个对象。**

### 2.1 设计思路
之前的BeanDefinition中放的是Object对象，getBean之后每次需要强转。因此这里我们修改Object为Class，接下来就需要在获取Bean对象的时候处理Bean对象的实例化操作以及判断当前单例对象是否在容器中缓存起来了。

- Bean的定义 -> Bean的注册
- Bean的获取 -> Bean单例

以上都放到了Spring的容器中。

我们创建一个SingletonBeanRegistry的接口，然后由DefaultSingletonBeanRegistry来实现，里面管理着一个存放Singleton Bean的容器。实现从里面getSingleton的方法和addSingleton的方法
> 1. 注意：这个addSingleton的方法我们使用的是protected的权限修饰符，理论只让它的子类和它在同一个包下的类来使用这个方法。

- 对于BeanFactory而言，我们给它设计一个接口BeanFactory，使用**工厂模式**，具体根据用户的配置来确定使用哪一个工厂来创建和管理Bean。

- 使用AbstractBeanFactory作为抽象工厂,这个对象是个抽象类，继承了SingletonBeanRegistry类。在这个对象中，我们实现从里面获取Singleton的方法，如果没有获取到，则调用抽象方法获取对象的Bean
Definition以及去create一个Bean。

- 有一个新的抽象对象AbstractAutowireCapableBeanFactory，这个对象继承了AbstractBeanFactory,实现了AbstractBeanFactory中的getBean方法，并且定义了getBeanDefinition和createBean两个抽象方法。

- 另外有一个DefaultListableBeanFactory继承了AbstractAutowireCapableBeanFactory并实现了BeanDefinitionRegistry中的registerBeanDefinition方法，在这个DefaultListableBeanFactory中定义了一个容器，来存放BeanName和BeanDefinition。

**总而言之：**
1. BeanFactory是一个简单工厂，在这个工厂中，主要是用来获取Bean。但是获取Bean的主要流程是首先先定义这个Bean的BeanDefinition,里面存放了这个Bean的Class。并且会将BeanDefinition注册到BeanDefinition的容器中。
2. 在BeanFactory的getBean方法中，首先会去singletonMap中找之前有没有以Singleton的方式实例化这个Bean。如果没有的话则会调用createBean方法创建这个Bean，并且将这个Bean存放到SingletonObject的容器中。
3. **DefaultListableBeanFactory中存放了一个BeanDefinition的容器，在DefaultSingletonBeanRegistry中存放了SingletonObject的一个容器。**
4. 总体流程是 注册BeanDefinition\放到BeanDefinition容器中\从BeanDefinition拿到Class\Class实例化生成对象\放到Singleton容器中\从Singleton容器中拿类的实例化。

### 2.2 设计模式
#### 2.2.1 简单工厂模式

定义一个创建对象的接口，让其子类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行。工厂根据传入的对象，来决定创建对应的产品类。

![FactoryPattern.png](img%2FFactoryPattern.png)


#### 2.2.2 工厂方法模式

定义一个抽象工厂，其定义了产品的生产接口，但不负责具体的产品。将生产任务交给不同的派生类工厂，这样不用通过指定类型来创建对象了。派生工厂用来创建实际产品。

![FactoryMethod.png](img%2FFactoryMethod.png)


#### 2.2.3 抽象工厂模式

定义一个抽象工厂，其定义了不同品牌不同产品种类的生产接口。不同品牌继承抽象工厂生成格子品牌下的不同品牌。

![AbstractFactory.png](img%2FAbstractFactory.png)


#### 2.2.4 模版模式

定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。 也就是说在抽象中先写方法，然后一部分抽象方法，抽象方法交给子类实现。

## 3.对象实例化策略
**对于前面的工作，在Bean对象的创建过程中我们运用反射调用了对象的空参构造器，但是如果这个对象是带参的我们应该怎么办？**

![error1.png](img%2Ferror1.png)

可以看到在这个错误中，对于Bean我们只给了一个带参数的构造器。在实例的过程中我们使用的是空参构造器去做的实例化，因此这里提出来了一个InstantiationException的错误。
也就是说我们之前并没有考虑带参数的对象实例化。因此，对于这里，我们需要进行进一步的修改。

### 3.1 设计思路
在Spring的源码中，我们在BeanFactory中的getBean的方法加入我们的构造函数的参数，这样就可以在实例化Bean对象的时候把对象入参传递进去。

至于我们怎么去创建这个带构造函数的Bean对象呢？
- 使用Java自带的方法DeclaredConstructor 
- Cglib来动态创建Bean对象，基于字节码框架ASM实现，通过ASM操作指令吗来创建对象。

首先，我们先定义一个InstantiationStrategy的接口，这个接口有一个instantiate方法，需要传入beanDefinition\beanName\constructor\args参数。
Jdk反射获得带参数的对象。Cglib**动态代理**生成带参数的对象实体。

其次我们在AbstractAutowireBeanFactory中创建一个InstantiationStrategy的类，这个类设置实例化Bean的策略。然后getBean调用一个createBean的方法，这个createBean的方法应该要带着Bean的入参。
另外这个createBean调用一个createInstance方法，这里调用了instantiationStrategy对象的instantiate方法，**并且创建好的对象要添加到Singleton容器中！！！！**

### 3.2 设计模式
#### 3.2.1 动态代理
为其他对象提供一个代理控制对这个对象的访问。举个例子，生活中我们去超市买东西，这些东西是超市从生产厂商买来给到我们的。因而，这个超市其实就是对生产厂商的一个代理。

通过代理模式，我们能做到如下：

1. 隐藏委托类的具体实现。
2. 实现客户和委托类的解耦合，在不改变委托类代码的情况下添加一下额外的功能。

> 1. 静态代理：程序员编码完成对代理类的实现。例如，一个接口定义功能。供应商来实现接口。代理类实例化供应商并对供应商的方法进行增强。
> 2. 缺点：如果一个类需要被代理，但是它实现了多个接口，那就需要在一个代理类下去实现这多个接口。从而这个代理类的代码会特别长。另外如果这个接口调整的时候，目标对象和代理类都要修改。
> 3. 动态代理：在程序运行的过程中进行创建。代理类不是程序员编码的而是动态生成的，动态代理主要是可以对被代理类的方法进行统一的处理，而不用修改每个被代理类的方法。
#### 3.2.2 JDK动态代理
类1:**java.lang.reflect.Proxy**

类2:**java.lang.reflect.InvocationHandler**

```java
/**
 * 调用处理程序
 */
public interface InvocationHandler { 
    Object invoke(Object proxy, Method method, Object[] args); 
}
```
这个接口作为处理器，来修改被代理类的函数。被代理类作为proxy传入，method为被代理类的方法，args为改方法的参数。这样对代理类的所有方法的调用都变成了invoke的调用。

**可以在这个invoke方法做统一的调用处理。**

步骤：
1. 定义一个实现InvocationHandler的中介类，这个类有一个目标对象，也就是被代理的对象，并编写invoke逻辑。
2. 实例化一个中介类，然后使用Proxy.newProxyInstance(ClassLoader,Interfaces,代理类)这个函数来得到被代理类的实例。
3. 这样其实JDK的动态代理为我们生成了一个继承中介类的被代理类的实例（包含了被代理类原本实现的所有接口），同时这个实例是实现了所有被代理类的方法，这个被代理类的方法其实是使用的中介类的invoke方法。
4. 因为JDK动态代理需要去继承一个中介类，所以实际上我们没办法基于类对象去做代理，因为Java的单继承特性。因而只能通过接口的方式代理，并且将目标对象作为中介类的属性传入。
#### 3.2.3 Cglib动态代理

Cglib动态代理示基于ASM机制实现的，通过生成被代理类的子类来实现。

主要模式是：
1. 查找目标类上所有非final的public类型的方法定义
2. 将这些方法的定义转换成字节码
3. 将组成的字节码转换成相应的代理的Class对象
4. 实现MethodInterceptor接口，用来代理对代理类上所有方法的请求。

#### 3.2.4 AspectJ动态代理


## 4.注入属性和依赖对象
目前可以基于构造器去创建Bean，但是很多属性是有依赖关系的。不能全部用构造器来创建。可以使用DI的方式，也就是Spring大名鼎鼎的Dependency Injection。

这也就是说我们在实例化Bean之后，为这个Bean去添加属性。
### 4.1 设计思路
既然我们是在实例化Bean之后去给Bean添加属性，那也就是说我们需要在BeanDefinition中添加Bean的属性信息。
也就是需要有一个参数PropertyValues。另外，我们还需要在createBean后去修改这个Bean，也就是说我们要ApplyValues到Properties上。

对于Bean对象的属性而言分为两种：基本数据类型、引用数据类型。对于这两种数据类型，我们需要分别去处理。

首先都需要用属性名和属性值来封装到PropertyValue中，这个HashMap的Key是String代表属性名，Value是Object（可能是基本数据类型对象，引用数据类型对象都是BeanReference）。

BeanReference中主要是存储了这个引用数据类型对象的名字，然后再去容器中拿到这个引用数据类型Bean，再封装到PropertyValue，再加入到PropertyValues容器中。**注意！这里需要先去注入这个引用数据对象，否则可能会出现无法注入成功的情况！**

注册完属性中的引用数据类型和将需要注入的属性之后，我们在createBean之后要ApplyPropertyValues这个方法，也就是从BeanDefinition中拿到PropertyValues这个集合，然后便利集合中的对象属性，再使用反射将这个对象属性注入到Bean对象中！。

### 4.2 思考
**Q3:注解Autowire和注解Resource的区别是什么？**
@Autowire为Spring提供的注解，只能按照byType进行注入，也就是说按照类型来装配对象。从Bean容器中找到这个类型的对象从而注入到这个属性上。
默认情况下我们要求这个对象必须存在，如果这个对象在Bean容器中不存在的话，那就会报错。但是我们可以把这个注解Autowire中的required属性改成false，这样就不一定要存在了。
**另外，如果我们想用byName的方式来查找这个Bean的话，我们可以使用@Qualifier("userDao")注解来先去找userDao的对象。**

@Resource注解默认情况下按照ByName来注入。其中有两个字段，一个是name，一个是type，如果想按照type就制定type的值，如果想按照自定义name的值就设置name的值，如果想都使用则都设置。

**Resource装配流程：**
- 如果指定了name和type则在容器中找到唯一一个Bean来匹配。找不到抛出异常
- 如果指定了name则找到唯一名字的bean进行装配，找不到抛出异常
- 如果指定了type则找到符合这个type的bean进行装配，找不到或者找到多个抛出异常
- 都没有指定的话，则自动按照byName的方式进行装配；如果没有匹配到的话，则回退为一个原始类型来进行匹配，如果匹配则自动装配。

**！！！！我们可以使用@Value的方式来给基本数据类型和String数据类型注入值。**

## 5.资源加载器解析文件注册对象
手动创建Bean的方式改到使用XML的方式来进行，通过配置文件来处理。因此需要一个资源文件解析器，需要做到能读取classpath、本地文件和云文件的配置内容。

这个XML文件包括的是Bean对象的描述、属性信息。

读取到配置文件后就应该注册Bean。
### 5.1 实现思路
首先我们需要一个Resource来对所有的资源文件读取，这里用到简单工厂模式用一个ResourceLoader来获取对应的Resource。这里我们主要设计三种Resource的获取：
- ClassPathResource
- UrlPathResource
- FilePathResource
每个都能拿到一个InputStream，这个InputStream用来读取文件数据。

ResourceLoader能跟据文件前缀来读取并确认是什么样的文件类型。首先定义一个文件前缀："classpath:"，这个代表ClassPathResource。如果传入的文件是包含这个前缀的，则就是ClassPathResource，从而可以获取到InputStream。
这个确定后，去设计一个关于XML的InputStream解析器。在这个解析器需要继承BeanDefinitionRegistry,这个BeanDefinitionRegistry去把读取到的bean信息注册到BeanDefinition容器中。
> **重要问题：在我们读取到有一个ref的节点的时候，我们需要new一个BeanReference来记录它的beanName，最后去bean容器中拿到这个Bean。**
> **因此我们需要把所有的属性信息存储在BeanDefinition的propertyValues中。这个PropertyValues后续在对象注入的时候，来逐个读取，并反射注入到这个对象中。**
> **这里的Reader主要也是接口来实现的，这个Reader主要做的事情就是读取文件然后注入到对象中**

截止到目前我们实现的Bean的初始化流程如下图：

![流程1.png](img%2F%E6%B5%81%E7%A8%8B1.png)

## 6.应用上下文
我们使用了DefaultListableBeanFactory用来读取XML，并且实例化Bean仓库，但是这个仓库无法直接给到用户进行使用。因此我们需要有一个入口函数，这个入口函数来对这些操作进行统一的封装。
从而给到用户进行直接使用。对外进行一个完整的服务提供。

也就是说对于我们之前XML文件的读取，Bean的定义和注册，Bean的实例化，Bean的属性注入的流程都交给一个服务来进行处理。因此我们引入**应用上下文的概念**，由这个应用上下文来为我们解决这个问题。

另外，我们需要用户在注册和实例化之间需要有别的定制化操作的时候，我们可以为用户添加一个接口，这样他们在Bean的实例化之前也可以做一些修改的操作。

因此，从这个角度来看我们现在整体变成了这个流程：Bean定义-》Bean注册-》Bean修改(BeanFactoryPostProcess)-》Bean的实例化-》Bean的拓展(BeanPostProcessor)

- BeanFactoryPostProcess -> 是一个在Bean对象定义、注册之后还没有实例化的情况下需要处理的。
- BeanPostProcessor -> 是Bean在实例化之后需要去处理的事情。

### 6.1 实现思路
首先我们需要一个ApplicationContext,这个ApplicationContext帮我们完成所有的事情，传入的参数就是资源文件的路径。
具体流程如下图：

![流程2.png](img%2F%E6%B5%81%E7%A8%8B2.png)

### 6.2 实现难度
其实核心难度是如何去分配好这些类和方法在哪一个类里，不可能全部在一个类文件中。因此这部分工作还是需要分好结果，并且把握好继承关系。

尤其是关于模版方法和抽象类的使用，一定要确定哪些方法哪些类是用来做什么的。**这个极为重要！要先想好这个完整的架构！！**如下面这个方法
其实它就是不同的方法被不同的抽象类实现来完成的，这样能够保证每个方法都是不同的类在进行维护，也能统一每个方法的名字。
```java
        // 1. 创建BeanFactory，并且加载BeanDefinition
        refreshBeanFactory();

        // 2. 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 在实例化Bean之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. BeanPostProcess 需要提前其他Bean实例化操作之前进行注册操作
        registerBeanPostProcessor(beanFactory);

        // 5. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
```

## 7. Bean的初始化和销毁方法
在Bean初始化过程中，我们可以做一些数据的加载执行、链接注册中心暴露RPC接口以及在WEB程序关闭时执行链接断开。

主要需求：
- 在XML配置初始化和销毁的方法，通过InitializingBean、DisposableBean两个接口。

**配置Bean初始化和销毁的四个办法：**
1. Bean继承InitializingBean和DisposableBean接口。
2. 使用注解@Bean(initMethod = "",destroyMethod = "")
3. 使用BeanPostProcessor来进行。
4. 类中使用@PostConstruct 或者 使用@PreDestroy。前者是在Servlet加载后使用，后者是在Servlet卸载前。

### 7.1 设计思路
以XML为例子：在spring.xml给Bean对象配置init-method和destroy-method的内容，配置问价加载的时候也读出来这两个节点的内容并加载到BeanDefinition中去。这样就可以在Bean初始化的过程中使用反射机制来调用这样方法。

如果是通过实现接口的方式的话，直接通过bean对象调用对应的接口方法就行。两个效果是一样的。

除了在初始化做的操作以外，destroy-method和DisposableBean接口的定义，都会在Bean对象初始化阶段完成，执行注册销毁方法的信息到DefaultSingletonBeanRegistry类中的disposableBeans属性里，这是为了后续进行统一调用。

**关于销毁方法需要在虚拟机执行关闭之前进行操作，所以这里用了一个注册钩子的操作。**

在我们判断关闭的过程中，我们使用了设计模式，适配器模式。主要是因为这里我们用了两种可能出现销毁前的方法。

- 一个是通过直接调用接口中方法的方式来直接实现销毁前的处理
- 一种是通过获取到XML中的方法，然后反射的方式来调用这个方法

为什么需要使用设计模式呢？

主要是因为，在我们关闭的时候，不想去处理或者是判断这个逻辑，而是直接封装成同一个逻辑来进行使用。因而我们使用适配器模式，让两个不能在一起的方法在同一个方法中进行判断执行，做统一执行了。

### 7.2 设计亮点
**代码调用链条：**
getBean -> doGetBean -> createBean -> 创建Bean实例 -> 属性注入 -> 执行BeanPostProcessor中的before方法 -> 执行init-method -> 执行BeanPostProcessor中的after方法 -> 注册BeanDisposable的方法 -> 注册到Singleton中
**适配器模式**
将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

- 可以让任何两个没有关联的类一起运行。
- 提高了类的复用，可以一致化多个不同接口。
- 将现有接口实现类隐藏，增加了类的透明度。
- 灵活性高，可自由适配。
其实在这个项目中看起来，是我们将XML读取destroy方法跟DisposableBean进行了一个整合，然后能在这个Adapter中进行统一使用。而不是分情况去处理。

## 8.Aware感知容器对象
如果我们想获得Spring框架提供的BeanFactory、ApplicationContext、BeanClassLoader等这些能力做一些拓展框架的使用时，可以使用感知容器操作的接口。

我们需要定义一个标记性的接口，这个接口不需要有其他的方法，它只起到标记作用就可以，而具体的功能由继承这个接口的其他功能性接口定义具体方法，最终这个接口就可以通过instanceof进行判断和调用了。

### 8.1 设计思想
如果我们有一个类是继承了Aware的子类接口的类，则它会在执行BeanPostProcessor之前去执行一个判断它是不是Aware的逻辑。这样的话我们就可以从而根据标识类Aware来判断是否要去给这个类获取到相关的容器。

> 1. 这里ApplicationContext是在后面进行创建的，其实它不无法通过BeanFactory来进行注入，因此我们使用BeanPostProcessor的方式去进行设置。
> 2. Aware接口可以理解为用来 **获取某些容器对象**的方法。获取容器对象是为了想利用容器对象的能力/服务。

## 9.对象作用域和FactoryBean
在MyBatis框架中，它的核心作用是可以满足用户不需要实现Dao接口类就可以通过xml或者注解配置的方式完成对数据库执行CURD操作。我们并没有手动的去创建任何操作数据库的对象，有的仅仅是一个接口的定义而这个接口定义可以被注入到其他需要使用Dao的属性中，这一过程最核心的问题就是把复杂而且以代理方式动态变化的对象注册到Spring容器中。

**核心目的：提供一个能让使用者定义负责的Bean对象。**也就是说对外提供一个可以二次从FactoryBean的getObject方法中获取对象的功能即可，这样所有实现这个接口的对象类就可以扩充自己的对象功能呢。

**MyBatis就是实现了一个MapperFactoryBean类，在getObject方法中提供SqlSession对象，从而执行CURD方法的操作**

### 9.1 设计思路：
解决单例还是原型对象，另一个处理FactoryBean类型对象创建过程中关于获取具体调用对象的getObject操作。

SCOPE_SINGLETON、SCOPE_PROTOTYPE对象创建的获取方式。主要区别于createBean后对象是否放到内存，不放到每次就需要重新叉棍见。

createBean执行对象创建、属性填充、依赖加载、前置后置处理、初始化等操作后，就需要再执行获取FactoryBean中的getObject对象了，整个getBean过程中都会新增一个单例的判断。

### 9.2 设计思想：
其实整个设计过程都是针对Bean的初始化过程来做的。当我们注册完Bean后，我们会得到Bean的类型：是单例的还是原型的。

同时我们在对类进行初始化的过程中，我们会先去singletonMap中去找目前singletonMap中是否有这个Bean。如果没有这个Bean的时候我们就走到创建Bean的流程中。

我们创建Bean的过程先是走的是Bean的实例化：传统类构造器实例化或者是Cglib反射实例化的方式。

然后走完实例化的过程，我们会去拿到这个对象，然后去对这个Bean去判断它是不是FactoryBean，如果是FactoryBean我们则会去走FactoryBean的getObject方法，从而去拿到这个FactoryBean创建的Bean。

同时这个FactoryBean有一个继承了DefaultSingletonRegistry的注册器,FactoryBeanRegistrySupport，在这个类中，其实我们也是放了一个容器来存放很多FactoryBean的Object。Key为FactoryBean的beanName，value为Object。

这样我们就完成了整个FactoryBean的工作流程。

![流程3.png](img%2F%E6%B5%81%E7%A8%8B3.png)

## 10 容器事件和事件监听器
Spring中有一个Event事件监听器，它可以提供事件的定义、发布以及监听事件来完成一些自定义的操作。比如可以定义一个新用户注册的事件，当有用户执行注册完成后，在事件监听中给用户发送一些优惠券和短信提醒，这样的操作就可以把属于基本功能的注册和对应的策略服务分开，降低系统的耦合。

这节以观察者模式的方式，设计和实现Spring Event中的容器事件和事件监听器，**最后可以在Spring框架中定义、监听和发布自己的事件信息。**

### 10.1 设计思想
基于观察者模式，要解决的就是一个对象状态改变给其他对象通知的问题。定义事件类、事件监听、事件发布。

使用观察者模式定义事件类、监听类、发布类，同时还需要完成一个广播器的功能，接收到事件推送时进行分析处理符合接受者感兴趣的事件。也就是使用isAssignFrom的判断。

isAssignFrom和instance类似，不过isAssignFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object，如果A.isAssignFrom(B)的结果是true，则证明B可以转换成A。

也就是说instanceof是 A->B，A是B的实现。isAssignFrom:A.B,B是A的实现

**ApplicationContextEvent是定义事件的抽象类，所有的事件包括关闭、刷新以及用户自己实现的事件都需要继承这个类。ContextClosedEvent和ContextRefreshEvent是Spring自己实现的两个事件类，可以用来监听刷新和关闭动作。**
EventObject->ApplicationEvent->ApplicationContextEvent->ContextClosedEvent\ContextRefreshEvent

### 10.2 设计流程
1. 创建ApplicationContext，读取XML文件，加载BeanDefinitions，其中包含设计的EventListeners
2. initApplicationEventMulticaster，初始化事件广播器，并和BeanFactory绑定。最后添加到Singleton容器中，交给Spring进行管理。
3. 从Spring容器中注册所有的事件监听器（先根据ApplicationListener从BeanDefinition中拿到所有属于ApplicationListener类型的对象，然后进行实例化操作。）然后添加到ApplicationListeners容器中。
4. 实例化其他单例对象。
5. 发布容器刷新完成事件。
6. 发布一个事件，通过ApplicationContext中的属性ApplicationEventMultiCaster进行事件发布，然后会有对应的监听器执行onApplicationEvent(event)方法来对这个事件发生进行对应的处理。
7. 容器关闭事件触发，容器关闭监听器对应进行处理。
8. 容器关闭，销毁所有的singleton对象。

![流程4.png](img%2F%E6%B5%81%E7%A8%8B4.png)

### 10.3 观察者模式
又称为发布订阅者模式，是一种通知机制，让发送通知的一方（被观察方）和接受通知的一方（观察者）彼此分离，不受影响。

对象之间是一对多的依赖关系，当一个对象改变状态时，它的所有依赖项都会自动得到通知和更新。

使用观察者模式的优点是：拓展性强，可以定义很多被观察者和观察者。降低系统和系统之间的耦合性，比如建立订阅者集合，可以随时添加和删除集合当中的某一个元素。

## 11 AOP的实现
AOP：面向切面编程，通过预编译的方式和运行期间动态代理实现程序功能的统一维护。

## 11.1 设计思路
先来实现一个可以代理方法的Proxy，代理方法主要使用到方法拦截器类 MethodInterceptor#invoke而不是直接使用invoke

以及还需要使用到org.aspectj.weaver.tools.PointcutParser 处理拦截表达式。

**对于JDK代理AOP实现方式：**
1. 其实核心的方式使用的是先创建被代理对象
2. 实例化方法拦截器 MethodInterceptor
3. 添加切入点匹配器 AspectJExpressionPointcut
4. 获取JDK代理对象

具体如下图所示：

![JDK AOP.png](img%2FJDK%20AOP.png)

**对于Cglib代理AOP实现方式：**
1. 先创建被代理对象
2. 实例化方法拦截器 MethodInterceptor
3. 添加切入点匹配器 AspectJExpressionPointcut
4. 获取JDK代理对象

具体如下图：
![CglibAOP.png](img%2FCglibAOP.png)

Cglib的注意事项：
- 其实Cglib核心是通过继承被代理类的方式来实现代理的逻辑。所以它不仅只是可以基于接口来进行代理，它还可以针对类来进行代理。
- 主要是使用ASM字节码的方式来代理对象，因此效率是高于JDK动态代理的。
- CGLib在创建代理对象时所花费的时间却比JDK多得多，所以对于单例的对象，因为无需频繁创建对象，用CGLib合适，反之，使用JDK方式要更为合适一些。
- 由于CGLib由于是采用动态创建子类的方法，对于final方法，无法进行代理。

## 12. 把AOP拓展到Bean的生命周期
主要是实现AOP的核心功能和Spring框架的整合。

### 12.1 设计思想
借用BeanPostProcessor把动态代理融入到Bean的生命周期。

为了在对象创建过程中，能把XML中配置的代理对象也就是切面的一些类对象实例化，就需要用到BeanPostProcessor提供的方法，因为这个类中的方法可以分别作用Bean对象执行初始化前后修改Bean的对象的拓展信息。

但是因为创建的代理对象不是之前流程里的普通Bean，因此需要前置于其他对象的创建。

因此需要在createBean的过程中先对Bean是否需要代理进行判断，有则直接返回代理对象。

这里还需要解决方法拦截器的具体功能，提供一些BeforeAdvice、AfterAdvice的实现，让用户可以更加简化的使用切面功能。除此之外，还需要包装切面表达式和拦截方法的整合，以及提供不同类型的代理方式的代理工厂，来包装我们的切面服务。

### 12.2 设计步骤
**AOP概念：**

**Pointcut**

Pointcut是一种表达式，用于指定哪些地方应用切面。使用AspectJ表达式来确定哪些包的哪些类的哪些方法进行匹配，从而进行拦截。

**JoinPoint**

这个指的是可以插入切面的点，指的是方法中特定位置，用来加入相关的代码。在方法执行前后插入日志输出、性能统计、安全检查等操作。

1. 首先我们需要一个Pointcut里面放着类的匹配器和方法匹配器。也就是从切面上来获取到进行拦截的包、类或者方法。这里我们主要是使用的AspectJExpressionPointcut,这个会解析我们的表达式，最后会进行判断目标对象和方法是否是我们需要拦截的。
2. 其次我们需要一个Advice，标识我们需要怎么去增强我们的代码。这里主要包括前置通知、后置通知、异常通知、返回通知、环绕通知等。
3. 最后，Pointcut和Advice的组合形成一个切面，通过Pointcut定位到特定的JoinPoint上，然后使用Advice对JoinPoint进行增强。
4. 有了Advice之后我们可以实现一个MethodBeforeAdvice，这个是用来实现前置通知的接口，然后我们需要实现的接口应该实现这个接口，从而实现功能的增强。
5. 被代理类的信息放在了TargetSource这个类中，然后AdvisedSupport中再封装TargetSource、方法拦截器、方法匹配器，最后用来实现代理类的生成。

**Advisor**

它包含了切面的实现、Advice的实现。

**MethodInterceptor**

**方法拦截器，着是一个用来拦截方法的。依赖了Advice。**
方法拦截下来后，会先在合适的位置插入Advice的增强代码，然后再进行被拦截方法的执行。

**代理类创建 DefaultAdvisorAutoProxyCreator**

它是用来创建代理类的，实际上它是一个PostBeanProcessor，因为它需要提前于其他Bean的实例化，因此我们需要让这个代理类创建成为一PostBeanProcessor这样它在注册PostBeanProcessor的时候就进行了实例化。

另外，我们创建代理对象的操作是由它的beforeInitialization方法进行的。具体步骤如下：
1. 首先先进行判断这个类是否是需要代理的，如果不是直接返回null。
2. 是的话先从容器中拿到Advisor，也就是Pointcut和Advice的结合类AspectJExpressionPointcutAdvisor的集合。然后遍历这个类，最后根据表达式、类过滤器来确定当前要代理的类是在哪个切面下的。
3. 确定切面后，设置关于这个被代理类的AdvisedSupport,主要设置被代理类、方法拦截器、方法匹配器。
4. 最终创建这个被代理类。
**代理类的本质是：新创建一个类，这个类里面的方法调用需要使用Invocation的invoke方法，这个方法可以被Advice进行切入增强。**

**方法拦截流程**

首先，先对调用的目标方法来进行匹配，看它是否满足方法匹配器。如果满足，则调用方法拦截器的invoke方法，invoke方法里面会调用自身依赖的Advice对这个方法进行增强。然后再执行proceed方法。

## 13 自动扫描Bean对象注册
通过配置XML以及注解的使用，从而自动注册Bean对象。
### 13.1 设计思路
为了简化Bean对象的配置，让整个Bean对象的注册都是自动注册的，那么基本需要的元素包括：
1. 扫描路径入口
2. XML解析扫描信息
3. 给需要扫描的Bean对象做注解标记
4. 扫描Class对象摘取Bean注册的基本信息
5. 组装注册信息
6. 注册成Bean对象

### 13.2 属性填充
对于属性填充，整体是需要一个叫PropertyValuePlaceholderConfigurer来进行管理的。它会定义占位符的前缀、后缀。以及属性填充文件的地址。（属性填充的文件是一个Properties文件）这里其实用到了一个embeddedValueResolver,在AbstractBeanFactory中有一个容器放着所有的Resolver,里面就有一个关于解析Properties文件中的属性值器。其实核心就是这个解析器读取这个属性的标示值是否匹配解析器的特征，如果是就从文件中读取Value进行解析。
**它是一个BeanFactoryPostProcessor**，在BeanDefinition已经注册完成后，它进行读取配置文件来进行属性填充。核心逻辑主要是拿到原来BeanDefinition中PropertyValue的属性值，然后去读取它的前后缀来匹配，从而替换填充进去。

### 13.3 自动扫描
这个地方主要是在RefreshBeanFactory流程中进行的。核心逻辑是在读取XML文件时会读取是否有context:component-scan这个标签，如果有，则读入它的base-package地址，从而调用XMLBeanDefinitionReader#scanPackage方法，进而调用ClassPathBeanDefinitionScanner#doscan的方法，这个主要是通过扫描指定包下含有对应Component注解的类。从而创建这个类的BeanDefinition，最后实例化。

![自动扫描.png](img%2F%E8%87%AA%E5%8A%A8%E6%89%AB%E6%8F%8F.png)

## 14 通过注解注入属性信息
主要是实现@Autowired、@Value注解，完成对属性和对象的注入操作。

### 14.1 设计思路
主要是使用BeanPostProcessor来完成Bean的BeanDefinition中PropertyValues的注入。

也就是说在实例化也就是applyPropertyValues之前需要完成对PropertyValues的修改。**这里会用到之前设计的PropertyValuePlaceholderConfigurer来修改PropertyValues。**

### 14.2 设计步骤
首先我们需要设计一个BeanPostProcessor，并且它要被扫描到BeanDefinition中。这个BeanPostProcessor主要是用来读取@Value@Autowire@Qualifier这几个参数标识的属性信息。

其次我们需要在Bean的实例化之后来对这个BeanPostProcessor进行调用处理，因此原本是在属性注入后的BeanPostProcessor处理，这个Processor需要进行提前。因为它会直接修改Bean里面的属性，对属性进行注入。**（本质是上直接对实例化后的对象进行修改，而不是先修改到BeanDefinition中）。**

所以现在的Bean实例化过程变成：先从XML或者自动扫描得到BeanDefinition，然后实例化Bean，然后使用这个BeanPostProcessor来注入Bean的属性，再使用BeanDefinition中的Properties来注入Bean的属性，再调用其它BeanPostProcessor的方法再调用初始化方法等。

![Autowired.png](img%2FAutowired.png)

## 15 给代理对象设置属性注入
之前AOP动态代理的对象不在Bean的生命周期中，这次需要去实现让AOP动态代理的对象也能实现注入属性。

**核心设计思想：**
之前我们会判断对象是否是代理对象，但是现在不需要这个判断操作了。我们之前将代理对象的生成移到BeanPostProcessor的applyBeanPostProcessorsAfterInitialization操作去了，也就是说这时候我们会先实例化这个被代理对象，然后再到BeanPostProcessor的After操作的时候来处理这个对象的代理。

注：必须是在切面范围内的对象才会被代理！！并不是所有的对象都会被代理。也就是说我们在切面表达式那里就已经限制了被代理对象的范围！！！

## 16 循环依赖问题解决
当我们注入属性的时候，如果A依赖于B，B依赖于A，这样就变成了循环依赖问题。

如下图所示：循环依赖的三种类型

![循环依赖.png](img%2F%E5%BE%AA%E7%8E%AF%E4%BE%9D%E8%B5%96.png)

- 自身依赖
- 循环依赖
- 多组依赖形成闭环

因此，Spring除了提供构造函数注入、属性注入、还有Setter循环依赖注入的解决方案。

解决循环依赖的条件：
- 依赖的 Bean 必须都是单例
- 依赖注入的方式，必须不全是构造器注入，且 beanName 字母序在前的不能是构造器注入

构造器注入问题：

如果全是构造器注入，比如A(B b)，那表明在 new 的时候，就需要得到 B，此时需要 new B 。

但是 B 也是要在构造的时候注入 A ，即B(A a)，这时候 B 需要在一个 map 中找到不完整的 A ，发现找不到。

为什么找不到？因为 A 还没 new 完呢，所以找到不完整的 A，因此如果全是构造器注入的话，那么 Spring 无法处理循环依赖。

### 16.1 三级缓存
按照Spring解决循环依赖主要用到了三个缓存，分别存放了：成品对象、半成品对象（未填充属性值）、代理对象，**分阶段存放对象来解决循环依赖问题。**

如果只有一级缓存来处理，则代码的复杂度会变高很多。因为全部初始化完成有完整的排列顺序，因而这个代码逻辑是不结偶的，复杂度十分高。

如果只有二级缓存来处理，则有很多半成品对象，这时候容易导致空指针异常。**这时候将半成品和成品分开，会更加优雅和方便，从而解决循环依赖的代码更加清晰。**

另外Spring中不只是有普通Bean，还有代理生成的对象，三级缓存主要解决循环依赖对AOP的处理，**Spring的原理是：先处理普通的Bean，再处理代理生成的Bean，因此这里涉及到一个三级缓存去处理。而不是全部由一级二级缓存处理。**

### 16.2 一级缓存实现思路
1. 如果仅用一级缓存解决循环依赖，那么可以是在A对象newInstance创建并且未填充属性的情况下，直接放入缓存中。
2. 在A对象的属性填充B对象时，如果缓存中获取不到B对象则会先创建B对象，同样创建完成后，把对象B填充到缓存中
3. 接下来就对B对象属性填充，恰好从缓存中拿到**半成品A**，B属性填充完成。
4. 最后返回来继续完成A对象的属性填充，把实例化并填充了属性的B对象赋值给A对象，完成了循环依赖的操作。

### 16.3 三级缓存实现思路
- 循环依赖的核心实现主要包括：DefaultSingletonBeanFactory提供三级缓存：singletonObjects、earlySingletonObjects、singletonFactories，分别存放成品对象、半成品对象和工厂对象。同时包装三个缓存提供方法：getSingleton、registerSingleton、addSingletonFactory，这样使用方就可以分别在不同时间段存放和获取对应对象了。
- 在AbstractAutowireCapableBeanFactory的doCreateBean方法中，提供了提前暴露对象的操作，addSingletonFactory(beanName,()->getEarlyBeanReference(beanName,beanDefinition,finalBean));以及后续获取对象和注册对象的操作exposedObject=getSingleton(beanName);registerSingleton(beanName,exposedObject);经过这样的处理就可以完成复杂场景循环依赖的操作。
- 另外在DefaultAdvisorAutoProxyCreator提供的切面服务中，也需要接口instantiationAwareBeanPostProcessor新增的getEarlyBeanReference方法，把依赖的切面对象也能放在三级缓存中，处理对应的循环依赖。

1. 首先创建Bean，实例化这个Bean，然后判断它是不是singleton，如果是singleton的就丢到BeanFactories（三级缓存）中存着，并且从二级缓存（半成品Bean容器）中删除。
2. 然后进行@Autowired属性注入和@Value的属性注入。（如果是引用数据类型，就需要考虑循环依赖的问题。）
3. 同理在下面applyPropertyValues的过程中如果是一个引用数据类型，直接进行getBean操作，如果不是单例直接创建一个新对象。如果是单例，先去一级缓存中找是否有实例化的对象，如果没有则去半成品容器（二级缓存）中查找是否有这个对象，如果还是没有则去三级缓存（代理工厂容器中找有没有这个BeanName对应的工厂，如果有直接调用getObjects去获取对象）
4. 如果三级缓存代理工厂中没有，则会直接创建这个类，继续去判断这个对象中的属性注入有没有引用类型。如果跟之前循环依赖，那就会先去找一级缓存中有没有，再去看二级缓存有没有，这时候会直接去三级缓存的代理工厂中找这个类的代理工厂，然后用这个代理工厂去实现这个类，再直接注入到这个类的属性中，再把这个创建的类放到半成品容器中。这个类则直接完成了循环依赖。然后最后属性注入完成后放到一级缓存中
5. 其他类同理。三级缓存工厂还可以直接进行代理对象。

如下图：为循环依赖实现逻辑：

![循环依赖实现逻辑.png](img%2F%E5%BE%AA%E7%8E%AF%E4%BE%9D%E8%B5%96%E5%AE%9E%E7%8E%B0%E9%80%BB%E8%BE%91.png)
