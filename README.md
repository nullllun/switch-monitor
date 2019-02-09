# 广东工业大学交换机监控（Java后台）

- [2016年“数字校园”学生科技项目](https://github.com/chn-lee-yumi/switch-monitor)延续版本
- 由于权限问题，目前只监控大学城校区学生宿舍的交换机和生活区核心交换机。未对教学区和其它校区的交换机进行适配
- 使用GPLv3开源协议

## 留下的坑
- 高并发下可能出现不同机器数据交换的情况（目前降低扫描速度可一定程度避开）
- 偶尔获取到数据不准确（对过大的值已做剔除处理），如汇聚端口偶现0数据导致绘图不好看（解决方法：在输出时对0数据前后进行判断）
- 偶见数据库死锁现象（几率低于1%）

## 部分未实现功能
- 交换机重启功能（OID已写入文件可直接使用，SNMP的write团体也已实现）
- 数据使用MongoDB存储

## 项目环境
- JDK11
- Redis
- Mysql

## 配套项目
- eoLinker
- 微信小程序
- 网页前端

## 性能
- i7 8700 轮询动态调整到50s，java约占用10% CPU，MySQL视调优情况

## 项目配置
  &nbsp;&nbsp;&nbsp;&nbsp;由于配置文件涉及敏感数据以及系统环境不同，故使用了ulisesbocchio进行加密，进行编译时需在resources目录下自行新建一个application-encrypt.yml的文件。<br/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容包括 jasypt.encryptor.password 以及 system.os (1为Linux 2为Win)


## 使用到的技术
1. SpringBoot
2. MySQL
3. Mybatis
4. Dubbo
5. WebSocket（用于网页登录）
6. SNMP4J
7. QuartZ
8. JWT
9. SpringSecurity
10. Zxing（二维码生成）
11. ulisesbocchio
    