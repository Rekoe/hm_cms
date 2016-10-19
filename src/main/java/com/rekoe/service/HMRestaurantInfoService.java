package com.rekoe.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.HMRestaurantInfo;

@IocBean(name = "hmRestaurantInfoService", fields = { "dao" })
public class HMRestaurantInfoService extends BaseService<HMRestaurantInfo> {

}
