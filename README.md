# jdk_source_code_learning
## 环境搭建
### 1、获取源码
安装jdk的包下有src源码。
![](pic/src.png)
解压src.zip文件。
![](pic/src1.png)

### 2、搭建idea环境
建一个maven或者简单的Java项目，这里只是拿去了源码包下的java包，这个包下包含了常用的功能。

为了方便debug，idea增加一些配置，使得debug的时候进入我们的类，而不是进入到编译的class中。  
首先，打开Project Structure在SDKs点击 + 选择JDK安装目录，创建一个名为1.8_without_rt的SDK, 并从ClassPath中移除rt.jar。
![](pic/idea.png)
为我们项目重新自己引入lib/rt.jar。
引入lib/rt.jar是为了解决切换到1.8_without_rt的SDK导致找不到sun.misc.Unsafe等类的问题。
![](pic/idea1.png)
选择Project SDK为刚才新建的1.8_without_rt的SDK。
![](pic/idea2.png)

这样debug的时候，会首先进入到我们的类。