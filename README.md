# WapCashDesk

### 接口文档 ###

* [项目SWAGGER文档](http://localhost:8888/WapCashDesk/swagger-ui.html)
* [项目SWAGGER文档<本地IP>](http://192.168.60.207:8888/WapCashDesk/swagger-ui.html)
* [项目SWAGGER文档<本地域名>](http://flyswagger.tunnel.qydev.com/WapCashDesk/swagger-ui.html)
* [项目SWAGGER文档<公测IP>](http://118.178.196.42:8888/WapCashDesk/swagger-ui.html)
* [项目SWAGGER文档<公测域名>](http://paytest.17wanxiao.com:8888/WapCashDesk/swagger-ui.html)

### 项目启动 ###

* [MAIN方法--启动](http://192.168.60.207:8888/WapCashDesk)
* [WAR包--启动](http://192.168.60.207:8888/WapCashDesk)

### 项目监控 ###

* [应用监控](http://localhost:8088/health)

### 项目访问地址 ###

* [本地地址<域名>](http://flyswagger.tunnel.qydev.com/WapCashDesk/)
* [本地地址<IP>](http://192.168.60.207:8888/WapCashDesk/)

* [公测地址](http://118.178.196.42:8888/WapCashDesk/)
* [公测地址](http://paytest.17wanxiao.com:8888/WapCashDesk/)

* [正式地址](http://210.43.241.76:8888/WapCashDesk/)


### 接口名称 ###

* [获取支付方式](http://flyswagger.tunnel.qydev.com/WapCashDesk/payWays)
~~~
{
  "channelno": "13",
  "merchantno": "20171200300",
  "partnerid": "PT000002",
  "projectPaywayList": [
    {
      "accountid": "1",
      "paywayid": "0204"
    }
  ]
}

~~~

* [订单状态查询](http://flyswagger.tunnel.qydev.com/WapCashDesk/queryOrder)
~~~
{
  "merchantno": "20171200300",
  "orderno": "1807231816191200000",
  "ordernotype": "1"
}
~~~

### 业务系统调用 ###

~~~
~~~