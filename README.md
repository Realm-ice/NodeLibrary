# NodeLibrary
这是一个使用Java语言编写的图书管理系统，前端采用Fxml和CSS编写；

下载代码之后修改文件结构如图所示：
![image](https://user-images.githubusercontent.com/77494834/131939615-43778b28-78c6-4569-b33f-71fd8e936a4c.png)
需要新建一个名为com的文件夹，然后在com中新建bjpowernode文件夹，之后将bean/dao/global/media/module/service/theme/util放到bjpowernode中，再新建一个src文件夹，将icon.png/logo.png和com文件夹放入src中，最后新建一个nodelibrary文件夹，放入src/lib/nodelibrary.iml. nodelibrary和其他文件夹放入同级目录即可. 

执行方式：
管理系统的入口函数是App.java，使用IDEA运行App.java文件即可进入登录界面；
系统目前有一个用户admin，密码也是admin；可以在登录界面创建新用户；
登录之后有用户管理，图书管理，借阅管理，分类统计界面，并且可以实现增删改查功能；
此管理系统的编写没有使用数据库，而是利用IO流读写文件来持久化数据，因此下载所有文件之后可以直接运行，不需要配置数据库；
