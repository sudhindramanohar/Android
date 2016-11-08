package com.example.nirapp;

public class nir_data {
	int _id;
	String _name;
	/*
	 * String _password; String _address; String _phone;
	 */

	String _cname;

	public nir_data(int id, String cname) {
		this._id = id;
		this._cname = cname;
	}

	public nir_data(String cname) {
		this._cname = cname;
	}

	public int getId1() {
		return this._id;
	}

	public void setId1(int id) {
		this._id = id;
	}

	public String getName1() {
		return this._cname;
	}

	public void setName1(String cname) {
		this._cname = cname;
	}

	public nir_data() {

	}

	/*
	 * public nir_data(int id,String name,String password,String address,String
	 * phone) { this._id=id; this._name=name; this._address=address;
	 * this._password=password; this._phone=phone; }
	 * 
	 * public nir_data(String name,String password,String address,String phone)
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
