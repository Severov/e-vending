package com.editor;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.FoodDAO;

@Service("foodEditor")
public class FoodEditor extends java.beans.PropertyEditorSupport {

    @Resource(name = "foodDAOimpl")
    private FoodDAO food;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
        setValue(food.findById(Integer.parseInt(text)));
	}
}
