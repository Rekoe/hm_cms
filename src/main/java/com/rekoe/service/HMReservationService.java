package com.rekoe.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.HMReservation;

@IocBean(name = "hmReservationService", fields = "dao")
public class HMReservationService extends BaseService<HMReservation> {

}
