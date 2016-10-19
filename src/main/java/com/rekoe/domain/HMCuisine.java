package com.rekoe.domain;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

@Table("hm_cuisine")
@Comment("惠民菜系")
@TableIndexes({ @Index(name = "hm_cuisine_name", fields = { "name" }, unique = true) })
public class HMCuisine implements Serializable {

	private static final long serialVersionUID = 6270819508385272870L;

	@Id
	private long id;

	@Column
	private String name;

	@ManyMany(target = HMRestaurantInfo.class, relation = "hm_restaurantinfo_cuisine", from = "cuisineid", to = "restaurantinfoid")
	private List<HMRestaurantInfo> restaurantInfos;

	@ManyMany(target = HMTradingStore.class, relation = "hm_tradingstore_cuisine", from = "cuisineid", to = "tradingstoreid")
	private List<HMTradingStore> tradingStore;

	public List<HMRestaurantInfo> getRestaurantInfos() {
		return restaurantInfos;
	}

	public void setRestaurantInfos(List<HMRestaurantInfo> restaurantInfos) {
		this.restaurantInfos = restaurantInfos;
	}

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

	public List<HMTradingStore> getTradingStore() {
		return tradingStore;
	}

	public void setTradingStore(List<HMTradingStore> tradingStore) {
		this.tradingStore = tradingStore;
	}
}