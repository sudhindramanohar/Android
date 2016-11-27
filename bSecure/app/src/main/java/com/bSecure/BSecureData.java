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

	public BSecureData() {

	}

	/*
	 * public BSecureData(int id,String name,String password,String address,String
	 * phone) { this._id=id; this._name=name; this._address=address;
	 * this._password=password; this._phone=phone; }
	 * 
	 * public BSecureData(String name,String password,String address,String phone)
	 * {
	 * 
	 * this._name=name; this._address=address; this._password=password;
	 * this._phone=phone; }
	 * 
	 * public int getId() { return this._id; }
	 * 
	 * public void setId(int id) { this._id=id; }
	 * 
	 * public String getName(){ return this._name; }
	 * 
	 * 
	 * public void setName(String name){ this._name = name; }
	 * 
	 * 
	 * public String getAdrs() { return this._address; }
	 * 
	 * public void setAdrs(String address) { this._address=address; }
	 * 
	 * public String getPhoneNumber(){ return this._phone; }
	 * 
	 * 
	 * public void setPhoneNumber(String phone_number){ this._phone =
	 * phone_number; }
	 * 
	 * public String getPassword(){ return this._password; }
	 * 
	 * 
	 * public void setPassword(String password){ this._password = password; }
	 */
}
