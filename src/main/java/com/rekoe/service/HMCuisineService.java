package com.rekoe.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.HMCuisine;

@IocBean(name = "hmCuisineService", fields = { "dao" })
public class HMCuisineService extends BaseService<HMCuisine> {

}
