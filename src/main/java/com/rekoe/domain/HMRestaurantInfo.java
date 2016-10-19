package com.rekoe.domain;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

/**
 * 实体店
 * 
 * @author kouxian
 *
 */
@Table("hm_restaurant_info")
public class HMRestaurantInfo implements Serializable {

	private static final long serialVersionUID = -7220425218488174235L;

	@Id
	private long id;

	@Column
	@Comment("店名")
	private String name;

	@Column
	@Comment("拼音")
	private String pinyin;

	@Column
	@Comment("菜系")
	@ManyMany(target = HMCuisine.class, relation = "hm_restaurantinfo_cuisine", from = "restaurantinfoid", to = "cuisineid")
	private List<HMCuisine> cuisines;

	@Many(target = HMTradingStore.class, field = "restaurantInfoId")
	private List<HMTradingStore> tradingStores;

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

	public String getPinyin() {
		return pinyin;
	}

	public List<HMTradingStore> getTradingStores() {
		return tradingStores;
	}

	public void setTradingStores(List<HMTradingStore> tradingStores) {
		this.tradingStores = tradingStores;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public List<HMCuisine> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<HMCuisine> cuisines) {
		this.cuisines = cuisines;
	}

}