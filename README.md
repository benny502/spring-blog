# 第一步： 构建开发环境

> 安装vscode插件
- Java Extension Pack
- Tomcat for Java
   如果想获得更好的体验，本人还推荐以下插件:
- Spring Boot Extension Pack

> 安装gradle与node

 项目运行与需要依赖gradle与node，虽然vscode在启动扫描时会根据项目中的gradlew文件自动下载相应版本的gradle，但是墙内的朋友就不要期待了，还是按照官方说明，根据自己的系统的自行安装吧。

> 安装vue-cli 

可以使用以下命令

    npm install -g @vue/cli

安装结束可以输入`vue --version`确认是否安装正确。

> 创建一个Springboot项目

- 新建一个`spring-blog`文件夹作为根项目的位置。
- 使用http://start.spring.io 或 Spring Boot Extension Pack中的 Spring Initializr Java Support来初始化一个Springboot项目放在`spring-blog`下的`blog`目录中。project选gradle，依赖暂时就选择web和mysql。
- 在`spring-blog`下运行vue脚手架:

      vue create web
- 进入web路径执行 
 
      gradle init

现在项目的结构应该为：
spring-boot
├── blog
│   └── ......
│   └── build.gradle
│   └── settings.gradle
├── web
│   └── ......
│   └── build.gradle

> 接下来我们来修改gradle配置文件， 使web项目自动部署

- web下的build.gradle
``` groovy
plugins {
    id "com.moowork.node" version "1.3.1"
}

task cnpmInstall(type: NpmTask) {
    group = "node"
    args = ['install', '--registry=https://registry.npm.taobao.org']
}

task npmBuild(type: NpmTask, dependsOn: cnpmInstall) {
    inputs.files(fileTree('config'))
    inputs.files(fileTree('src'))
    inputs.files(fileTree('static'))
    inputs.files(fileTree('build'))
    inputs.files(fileTree('package.json'))
    inputs.files(fileTree('package-lock.json'))

    outputs.files(fileTree('dist'))

    group = "node"
    args = ['run', 'build']
}


task npmDev(type: NpmTask, dependsOn: cnpmInstall) {
    group = "node"
    args = ['run', 'serve']
}
```
这里定义了三个task，分别是`npm install` `npm run build` `npm run serve`

- blog下的build.gradle
```groovy
plugins {
	id 'org.springframework.boot' version '2.1.5.RELEASE'
	id 'java'
}

apply plugin: 'io.spring.dependency-management'

group = 'com.benny'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'mysql:mysql-connector-java'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

build.dependsOn project(':web').getTasksByName('npmBuild', false)
processResources {
	from(project(':web').file('dist').path) {
		into 'static'
	}
	from(project(':web').file('dist/index.html').path) {
		into 'templates'
	}
}

apply plugin: 'war'
```

这里声名了blog项目的build依赖web项目的Task `npmBuild`。
使用processResources将web项目下的dist中的文件部署到blog下的resource/static文件夹下，并且将index.html单独搬运到templates下。
apply plugin 'war'在build时生成war包，以便使用tomcat运行。

- 将settings.gradle搬运到上一层，`spring-blog`下:

```groovy
pluginManagement {
	repositories {
		gradlePluginPortal()
	}
}
rootProject.name = 'demo'
include 'blog'
include 'web'
```

> 大功告成。现在运行`gradle bootRun`然后访问http://localhost:8080应该可以看到vue的启动页面：

![image.png](https://upload-images.jianshu.io/upload_images/337531-7055535bd4e8558b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

有一点需要说明的是:

- 使用Debugger for Java插件调试时，生成的class文件路径`blog/bin`
- 而`grade bootRun/build`的输出路径为`blog/build`

`grade build`不只会编译java源码，还会做很多其他的事情，比如搬运resource中的静态文件和我们自定义的任务。

> 所以使用debug插件调试时是看不到前端文件的。如果想要做一个整体效果的调试。可以在生成的war包上右键。选择debug on Tomcat Server

