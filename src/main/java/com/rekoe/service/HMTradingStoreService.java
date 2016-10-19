package com.rekoe.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.HMTradingStore;

@IocBean(name = "hmTradingStoreService", fields = { "dao" })
public class HMTradingStoreService extends BaseService<HMTradingStore> {

}
