var ioc = {
	platformEditDirective : {
		type : "com.rekoe.web.freemarker.PlatformEditDirective",
		events : {
			create : "init",
		}
	},
	shiroTags : {
		type : "com.rekoe.shiro.freemarker.ShiroTags"
	},
	permissionResolver : {
		type : "org.apache.shiro.authz.permission.WildcardPermissionResolver"
	},
	articleList : {
		type : "com.rekoe.web.freemarker.ArticleListDirective",
		args : [ {
			refer : "articleService"
		}, {
			refer : "articleCategoryService"
		} ]
	},
	permissionShiro : {
		type : "com.rekoe.web.freemarker.PermissionShiroFreemarker",
		args : [ {
			refer : "permissionResolver"
		} ]
	},
	permission : {
		type : "com.rekoe.web.freemarker.PermissionDirective"
	},
	process : {
		type : "com.rekoe.web.freemarker.ProcessTimeDirective"
	},
	currentTime : {
		type : "com.rekoe.web.freemarker.CurrentTimeDirective"
	},
	htmlCut : {
		type : "com.rekoe.web.freemarker.HtmlCutDirective"
	},
	pagination : {
		type : "com.rekoe.web.freemarker.PaginationDirective"
	},
	timeFormat : {
		type : "com.rekoe.web.freemarker.TimeFormatDirective"
	},
	platformPermission : {
		type : "com.rekoe.web.freemarker.PlatformPermissionDirective"
	},
	articleCategoryTree : {
		type : "com.rekoe.web.freemarker.ArticleCategoryTreeDirective",
		args : [ {
			refer : "articleCategoryService"
		} ]
	},
	mapTags : {
		factory : "$freeMarkerConfigurer#addTags",
		args : [ {
			'shiro' : {
				refer : 'shiroTags'
			},
			'platform_perm' : {
				refer : 'platformPermission'
			},
			'perm_chow' : {
				refer : 'permissionShiro'
			},
			'cms_perm' : {
				refer : 'permission'
			},
			'process_time' : {
				refer : 'process'
			},
			'pagination' : {
				refer : 'pagination'
			},
			'htmlCut' : {
				refer : 'htmlCut'
			},
			'timeFormat' : {
				refer : 'timeFormat'
			},
			'currentTime' : {
				refer : 'currentTime'
			},
			'platform_edit' : {
				refer : 'platformEditDirective'
			},
			'article_category_tree' : {
				refer : 'articleCategoryTree'
			},
			'art_list' : {
				refer : 'articleList'
			}
		} ]
	}
};