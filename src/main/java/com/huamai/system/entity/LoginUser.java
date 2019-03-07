package com.huamai.system.entity;

public class LoginUser {

	String vernbr; // 接口版本号
	String sigalg; // 签名算法
	String reqtim; // 请求时间
	String coridc; // 企业 ID
	String usridc; // 用户 ID
	String autcod; // 授权码
	String usrmob; // 手机号码

	
	public LoginUser() {
		super();
	}

	public LoginUser(String usrmob) {
		super();
		this.usrmob = usrmob;
	}

	public String getVernbr() {
		return vernbr;
	}

	public void setVernbr(String vernbr) {
		this.vernbr = vernbr;
	}

	public String getSigalg() {
		return sigalg;
	}

	public void setSigalg(String sigalg) {
		this.sigalg = sigalg;
	}

	public String getReqtim() {
		return reqtim;
	}

	public void setReqtim(String reqtim) {
		this.reqtim = reqtim;
	}

	public String getCoridc() {
		return coridc;
	}

	public void setCoridc(String coridc) {
		this.coridc = coridc;
	}

	public String getUsridc() {
		return usridc;
	}

	public void setUsridc(String usridc) {
		this.usridc = usridc;
	}

	public String getAutcod() {
		return autcod;
	}

	public void setAutcod(String autcod) {
		this.autcod = autcod;
	}

	public String getUsrmob() {
		return usrmob;
	}

	public void setUsrmob(String usrmob) {
		this.usrmob = usrmob;
	}

}
