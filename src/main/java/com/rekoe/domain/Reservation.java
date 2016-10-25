package com.rekoe.domain;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 点餐订单
 * 
 * @author kouxian
 *
 */
@Table("hm_reservation")
public class Reservation implements Serializable {

	private static final long serialVersionUID = 3847768969866910621L;

	@Id
	private long id;

	@Column(hump = true)
	private long tradingStoreId;

	@Column(hump = true)
	private Date eatTim;

	@Column(hump = true)
	private Date createTim;

	@Comment("是否定雅间")
	@Column(hump = true)
	private boolean privateRoom;

	@Comment("人数")
	@Column
	private int persion;

	@Comment("房间号")
	@Column(hump = true)
	private int roomNum;

	@Comment("是否自带酒水")
	@Column(hump = true)
	private boolean needWine;

	@Comment("联系电话")
	@Column
	private long phone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public long getTradingStoreId() {
		return tradingStoreId;
	}

	public void setTradingStoreId(long tradingStoreId) {
		this.tradingStoreId = tradingStoreId;
	}

	public Date getEatTim() {
		return eatTim;
	}

	public void setEatTim(Date eatTim) {
		this.eatTim = eatTim;
	}

	public Date getCreateTim() {
		return createTim;
	}

	public void setCreateTim(Date createTim) {
		this.createTim = createTim;
	}

	public boolean isPrivateRoom() {
		return privateRoom;
	}

	public void setPrivateRoom(boolean privateRoom) {
		this.privateRoom = privateRoom;
	}

	public int getPersion() {
		return persion;
	}

	public void setPersion(int persion) {
		this.persion = persion;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public boolean isNeedWine() {
		return needWine;
	}

	public void setNeedWine(boolean needWine) {
		this.needWine = needWine;
	}
}