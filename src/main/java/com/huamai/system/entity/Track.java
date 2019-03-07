package com.huamai.system.entity;

import java.util.UUID;

import com.alibaba.druid.util.Utils;
import com.huamai.util.DateUtils;

/**
 * @ClassName: Track
 * @Description: 用户行为轨迹实体类
 * @author bai
 * @date 2019年2月25日
 */
public class Track {
	private Integer id;

	private String activityCode;// 所属活动编号

	private String customerCode;// 用户编号（手机号）

	private String traceId;// 轨迹ID

	private String ip;// 访问ip

	private String visitTime;// 访问时间

	private String leaveTime;// 离开时间

	private String traceDesc;// 页面描述

	private String exGoodsCode;// 附加：专区商品的code

	private String exActivityCode;// 附加：离开首页时点击的活动code
	
	public Track() {
		super();
	}

	public Track(String activityCode, String customerCode, String ip, String traceDesc) {
		super();
		this.activityCode = activityCode;
		this.customerCode = customerCode;
		this.traceId = getTrackIdMD5();
		this.ip = ip;
		this.visitTime = DateUtils.getNowTime();
		this.traceDesc = traceDesc;
	}

	public Track(String activityCode, String customerCode, String ip, String traceDesc, String exGoodsCode,
			String exActivityCode) {
		super();
		this.activityCode = activityCode;
		this.customerCode = customerCode;
		this.traceId = getTrackIdMD5();
		this.ip = ip;
		this.visitTime = DateUtils.getNowTime();
		this.traceDesc = traceDesc;
		this.exGoodsCode = exGoodsCode;
		this.exActivityCode = exActivityCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode == null ? null : activityCode.trim();
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode == null ? null : customerCode.trim();
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId == null ? null : traceId.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime == null ? null : visitTime.trim();
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime == null ? null : leaveTime.trim();
	}

	public String getExGoodsCode() {
		return exGoodsCode;
	}

	public void setExGoodsCode(String exGoodsCode) {
		this.exGoodsCode = exGoodsCode == null ? null : exGoodsCode.trim();
	}

	public String getExActivityCode() {
		return exActivityCode;
	}

	public void setExActivityCode(String exActivityCode) {
		this.exActivityCode = exActivityCode == null ? null : exActivityCode.trim();
	}

	public String getTraceDesc() {
		return traceDesc;
	}

	public void setTraceDesc(String traceDesc) {
		this.traceDesc = traceDesc == null ? null : traceDesc.trim();
	}

	public static String getTrackIdMD5() {
		return "TRACE-" + Utils.md5(UUID.randomUUID().toString());
	}
}