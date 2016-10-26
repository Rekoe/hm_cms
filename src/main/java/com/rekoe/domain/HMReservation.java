package com.rekoe.domain;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
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
public class HMReservation implements Serializable {

	private static final long serialVersionUID = 3847768969866910621L;

	@Id
	private long id;

	@Column(hump = true)
	private long tradingStoreId;

	@Column(hump = true)
	private Date eatTim;

	@Column(hump = true)
	@ColDefine(type = ColType.TIMESTAMP)
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

	@Comment("联系电话")
	@Column
	private long phon;

	@Column("s")
	@Comment("状态")
	private int status;

	@Column(hump = true)
	@Comment("订单ID")
	@ColDefine(width = 64)
	private long orderId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getPhon() {
		return phon;
	}

	public void setPhon(long phon) {
		this.phon = phon;
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
}