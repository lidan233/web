# 这是我大一webgis的课设
也是我走上java web之路的开始

# webgis项目
前端是原生的vue app，后端是java项目，前端使用了openlayers，实现了对遥感图像的矢量化的操作，并支持各种坐标系的导出功能，如4326等。 

## 项目重点

### 项目采用了前后端分离设计

#### 前端
依赖库：openlayers, jquery, vue, vue element. 
实现了：基本的权限控制，项目操作导出，点，线，面等等操作。 

### 后端
依赖库：durid,mybatis,sprintboot,gson,redis等等。详情请看pom.xml

### 基本效果
![](images/2.png)
![](images/3.JPG)
![](images/4.JPG)
![](images/5.JPG)
![](images/6.JPG)
![](images/7.JPG)
![](images/8.JPG)


### note
1. 分离的设计，support文件夹中的lib和js可以单独部署。 
2. 前后端分离，支持高并发的设计，所有的模块均已微服务化，加工成API。 


### 欢迎关注我的个人网站crazyweirdo.xyz 