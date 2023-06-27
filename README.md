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
