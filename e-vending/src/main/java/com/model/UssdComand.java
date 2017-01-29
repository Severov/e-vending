package com.model;

public enum UssdComand {
	
	EMPTY(0, ""),
	_1KS1(1, "*111#"), 
	_2MTS1(2, "*101#"),                  	                
	_3LIFECELL1(3, "*112#"),
	_4KS2(4, "*119#"),                    
	_5LIFECELL2(5, "*119#"),                 
	_6MTS2(6, "*110*10#");               
	
	private Integer id;
	private String description;

	public int getId() {
		return id;
	}
	
	public static UssdComand getById(Integer id) {
	    for(UssdComand e : values()) {
	        if(e.id.equals(id)) return e;
	    }
	    
	    return null;
	 }
	
	public String getDescription(){
		return description;
	}

	UssdComand(int id, String description) {
        this.id = id;
        this.description = description;
    }

}
