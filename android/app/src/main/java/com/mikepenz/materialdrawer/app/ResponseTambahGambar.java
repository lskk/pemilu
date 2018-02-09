package com.mikepenz.materialdrawer.app;


import com.google.gson.annotations.SerializedName;


public class ResponseTambahGambar {

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("namafile")
	private String namafile;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setNamafile(String namafile){
		this.namafile = namafile;
	}

	public String getNamafile(){
		return namafile;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTambahGambar{" + 
			"result = '" + result + '\'' + 
			",msg = '" + msg + '\'' + 
			",namafile = '" + namafile + '\'' + 
			"}";
		}
}