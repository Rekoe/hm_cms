package com.rekoe.domain;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

/**
 * 商圈分布
 * @author kouxian
 *
 */
@Table("hm_trading_store")
@Comment("商圈店分布")
public class HMTradingStore implements Serializable {

	private static final long serialVersionUID = 2067301152703239830L;

	@Id
	private long id;

	@Column
	@Comment("商圈名字")
	private String name;

	@Column
	@Comment("地址")
	private String addr;

	@Column
	@Comment("电话")
	private String phone;

	@Column
	@Comment("附近酒店")
	private String hotel;

	@ManyMany(target = HMCuisine.class, relation = "hm_tradingstore_cuisine", from = "tradingstoreid", to = "cuisineid")
	private List<HMCuisine> cuisines;

	@Column(hump = true)
	private long restaurantInfoId;

	@One(target = HMRestaurantInfo.class, field = "restaurantInfoId")
	private HMRestaurantInfo restaurantInfo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public List<HMCuisine> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<HMCuisine> cuisines) {
		this.cuisines = cuisines;
	}

	public long getRestaurantInfoId() {
		return restaurantInfoId;
	}

	public void setRestaurantInfoId(long restaurantInfoId) {
		this.restaurantInfoId = restaurantInfoId;
	}

	public HMRestaurantInfo getRestaurantInfo() {
		return restaurantInfo;
	}

	public void setRestaurantInfo(HMRestaurantInfo restaurantInfo) {
		this.restaurantInfo = restaurantInfo;
	}

}