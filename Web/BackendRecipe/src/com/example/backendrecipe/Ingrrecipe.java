package com.example.backendrecipe;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Ingrrecipe {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key_ingrrecipe;
	@Persistent
	private String id_ingrrecipe;
	@Persistent
	private String quantity;
	@Persistent
	private String measurementu;
	@Persistent
	private String id_ing;
	
	public void createKey(Key keyparent){
		key_ingrrecipe = KeyFactory.createKey(keyparent, Ingrrecipe.class.getSimpleName(), id_ingrrecipe);		
	}
	public Key getKey(){
		return key_ingrrecipe;
	}
	public String getId_ingrrecipe() {
		return id_ingrrecipe;
	}
	public void setId_ingrrecipe(String id_ingrrecipe) {
		this.id_ingrrecipe = id_ingrrecipe;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getMeasurementu() {
		return measurementu;
	}
	public void setMeasurementu(String measurementu) {
		this.measurementu = measurementu;
	}
	public String getId_ing() {
		return id_ing;
	}
	public void setId_ing(String id_ing) {
		this.id_ing = id_ing;
	}
}

