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