var ioc = {
	gatheConf : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : "gathe_core.properties"
		}
	},
	conf : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : [ "custom/" ]
		}
	},
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			create : "init",
			depose : 'close'
		},
		fields : {
			url : {
				java : "$conf.get('db.url', 'jdbc:mysql://127.0.0.1:3306/platform?useUnicode=true&characterEncoding=utf-8')"
			},
			username : {
				java : "$conf.get('db.username', 'root')"
			},
			password : {
				java : "$conf.get('db.password', 'root')"
			},
			maxActive : {
				java : "$conf.getInt('db.maxActive', 20)"
			},
			validationQuery : "SELECT 'x'",
			testWhileIdle : true,
			testOnBorrow : false,
			testOnReturn : false,
			filters : "mergeStat",
			connectionProperties : "druid.stat.slowSqlMillis=1000"
		}
	},

	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : "dataSource"
		} ],
		fields : {
			executor : {
				refer : "cacheExecutor"
			}
		}
	},
	cacheExecutor : {
		type : "org.nutz.plugins.cache.dao.CachedNutDaoExecutor",
		fields : {
			cacheProvider : {
				refer : "cacheProvider"
			},
			cachedTableNames : [ "system_permission", "permission_category",
					"system_role", "system_user", "system_user_role",
					"article_category", "article", "web_mobile_bind" ],
			enableWhenTrans : false, // 事务作用域内不启用缓存,默认也是false
			db : "MYSQL"
		}
	},
	cacheProvider : {
		type : "org.nutz.plugins.cache.dao.impl.provider.EhcacheDaoCacheProvider",
		fields : {
			cacheManager : {
				refer : "cacheManager"
			}
		// 引用ehcache.js中定义的CacheManager
		},
		events : {
			create : "init"
		}
	}
};