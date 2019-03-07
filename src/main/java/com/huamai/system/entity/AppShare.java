package com.huamai.system.entity;

import java.io.Serializable;

/**  
* @ClassName: Share  
* @Description:  
* @author bai  
* @date 2019年1月12日  
*/
public class AppShare implements Serializable{
	
	/**  
	* @Fields field:field:
	*/
	private static final long serialVersionUID = 1L;

	private String title;
	
	private String desc;
	
	private String url;
	
	private String img;
	

	public AppShare(String titel, String desc, String url, String img) {
		super();
		this.title = titel;
		this.desc = desc;
		this.url = url;
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "AppShare [titel=" + title + ", desc=" + desc + ", url=" + url + ", img=" + img + "]";
	}
	
	
}
