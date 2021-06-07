#### 基于SpringBoot的创意家居用品城设计与实现



##### 1、项目介绍

首先，这是我个人得毕业设计项目，整个项目由自己个人开发而成。现在就这个项目做一个简单的介绍，本设计目的在于给予了企业一个实用型的在线商城。创意家居是指在满足产品本身的实用性的基础上，在外观的设计上融入时尚，个性化的家居用品，产品以独特的设计打动人心，与个人的喜好融为一体，其中融合了设计师的创新与灵感，它所展现的魅力能够舒缓生活中的部分压力，增添生活中的乐趣，满足人们的精神需求。该系统给予了企业和消费的直接沟通的平台，依靠此系统，消费者可以按照自己的个性化需求寻求满足自身需要的家居，企业可以在此系统开展属于自身的消费市场，进一步扩大自身的市场竞争能力。该系统实现了某家居企业与消费者之间的双赢。

在本设计研究方案中，本系统主要包括三大模块，普通用户模块，设计师模块，管理端模块。其中用户模块包括个人信息管理，设计师与家居用品浏览，购物车管理，订单管理和个人定制管理等模块，设计师模块包括个人信息管理，用户个人定制管理等模块，管理端模块则包括普通用户信息管理，设计师信息管理，品牌管理，风格管理，家居用品管理，设计方案管理，成功案例管理和订单管理等模块。本系统的开发采取服务端/客户端的模式，服务端采取SpringBoot框架，整合了Mybaits，Redis，Maven等组件，集成开发环境为 IntelliJ IDEA，主要开发语言为Java；客户端主要采用了Vue框架，整合了ElementUI，JS等组件，采用ajax异步访问服务端，集成开发环境为Visual Studio Code；数据库采用的是目前很流行的关系型数据库MySQL；图片采用FastDFS(分布式文件系统)图片服务器结合Nginx服务器进行存储；最后整个web应用打包整合到云服务器进行发布运行。这样的结构配置能够做到安全稳定运营，易于扩展，高速开发，从而满足项目的各项设计需求。

##### 2、项目功能

目总体功能结构图如下所示：

![](https://cdn.jsdelivr.net/gh/xiewende/blog_img/20210607/%E7%B3%BB%E7%BB%9F%E5%8A%9F%E8%83%BD%E6%80%BB%E5%9B%BE.png)



##### 3、技术选型

后端：Springboot + mybaits + mysql + redis + fastDFS

前端：vue框架 + ElementUI组件



##### 4、技术架构

本系统的技术逻辑架构如下：

![](https://cdn.jsdelivr.net/gh/xiewende/blog_img/20210607/%E7%B3%BB%E7%BB%9F%E6%8A%80%E6%9C%AF%E6%A1%86%E6%9E%B61.png)

##### 5、下载部署步骤

- 本目录下有数据库的建表语句sql文件 data.sql，下载直接执行即可。
- 配置 redis 和 fastDFS ，由于这两个本人是采取部署在本地虚拟机linux上面的，本目录下的 redis 即是本设计涉及到的redis和fastDFS部署的虚拟机机子，文件毕业设计linux开启相关服务.md就是开启 虚拟机相关服务的相关步骤。
- 前端代码也是在本目录下，直接打开即可。
- 下载后台修改数据库配置信息和linux配置信息。



##### 6、恭喜自己

最后恭喜自己完美毕业！！！！完美散页！！继续下一个学习生涯的开始！！！