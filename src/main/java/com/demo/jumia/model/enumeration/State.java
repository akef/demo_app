package com.demo.jumia.model.enumeration;

public enum State {
	VALID("valid"),
	NOT_VALID("notValid"),
	ALL("all");
	
	State(String code){
		this.code =code;
	}
	
	private String name;
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
