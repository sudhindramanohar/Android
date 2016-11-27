package com.bSecure;

public class BSecureData {

	String phone_no;
	String _cname;
	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public BSecureData(String cname, String phone_no) {
		this._cname = cname;
		this.phone_no =phone_no;
	}

	public String getName1() {
		return this._cname;
	}

	public void setName1(String cname) {
		this._cname = cname;
	}

}
