package com.example.backendrecipe;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Recipe {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key_recipe;
	@Persistent
	private String id_recipe;
	
	@PersistenceCapable
	@EmbeddedOnly
	public static class CardInfo{
		@Persistent
		private String name;
		@Persistent
		private String type;
		@Persistent
		private String summary;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
	}
	
	@Persistent
	@Embedded
	private CardInfo card;
	@Persistent
	private String steps;
	@Persistent
	private List<Ingrrecipe> ling;
	
	public Recipe(){
		// When a new Recipe object is created, the list of ingredients is also created but no items are added, by the moment.
		ling = new ArrayList<Ingrrecipe>();
	}
	public void createKey(Key keyparent){
		key_recipe = KeyFactory.createKey(keyparent, Recipe.class.getSimpleName(), id_recipe);
	}
	public Key getKey(){
		return(key_recipe);
	}
	public String getId_recipe() {
		return id_recipe;
	}
	public void setId_recipe(String id_recipe) {
		this.id_recipe = id_recipe;
	}
	public CardInfo getCard() {
		return card;
	}
	public void setCard(CardInfo card) {
		this.card = card;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	public List<Ingrrecipe> getLing() {
		return ling;
	}
	public void setLing(List<Ingrrecipe> ling) {
		this.ling = ling;
	}
	public boolean existIngrrecipe(String idingrrecipe){
		for (int i = 0; i < ling.size(); i++){
			if (ling.get(i).getId_ingrrecipe().equals(idingrrecipe)){
				return true;
			}
		}
		return false;
	}
	public boolean addIngrrecipe(Ingrrecipe ingrrecipe){
		if (!existIngrrecipe(ingrrecipe.getId_ingrrecipe())){
			ling.add(ingrrecipe);
			return true;
		}
		return false;
	}
}
