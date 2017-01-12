package com.model;

public enum DescriptionError {
	
	ERROR_MOTOR(1, "Ошибка двигателя"),                  
	ERROR_SENSOR(2, "Ошибка сенсора"),                 
	ERROR_ROM(4, "Ошибка ROM"),
	ERROR_JAM(5, "Замятие"),                    
	ERROR_NO_BOX(8, "Отсутствует бокс"),                 
	ERROR_TIME_OUT_CASH_ACCEPTOR(9, "Таймаут купюрника"),  
	ERROR_BOX_FULL(54, "Бокс переполнен");                
	
	private Integer id;
	private String description;

	public int getId() {
		return id;
	}
	
	public static DescriptionError getById(Integer id) {
	    for(DescriptionError e : values()) {
	        if(e.id.equals(id)) return e;
	    }
	    
	    return null;
	 }
	
	public String getDescription(){
		return description;
	}

	DescriptionError(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
