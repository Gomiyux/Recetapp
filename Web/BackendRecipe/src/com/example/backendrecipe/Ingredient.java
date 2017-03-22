package com.example.backendrecipe;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Ingredient {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key_ingredient;
	@Persistent
	private String id_ingredient;
	@Persistent
	private String name;
	@Persistent
	private String nutritionaldesc;
	
	public void createKey(){
		key_ingredient = KeyFactory.createKey(Ingredient.class.getSimpleName(), id_ingredient);
	}
	public Key getKey(){
		return key_ingredient;
	}
	public String getId_ingredient() {
		return id_ingredient;
	}
	public void setId_ingredient(String id_ingredient) {
		this.id_ingredient = id_ingredient;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNutritionaldesc() {
		return nutritionaldesc;
	}
	public void setNutritionaldesc(String nutritionaldesc) {
		this.nutritionaldesc = nutritionaldesc;
	}
}

