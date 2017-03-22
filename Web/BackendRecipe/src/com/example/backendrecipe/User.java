package com.example.backendrecipe;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key;
	@Persistent
	private String id;
	@Persistent
	private Email email;
	@Persistent
	private List<Recipe> recipes;
	
	public User(){
		// When the object is created, the list of recipes is also created and no items are added, by the moment
		recipes = new ArrayList<Recipe>();
	}
	public void createKey(){
		key = KeyFactory.createKey(User.class.getSimpleName(), id);
	}
	public Key getKey(){
		return key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public List<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	public boolean deleteRecipefromArray (String idrecipe){
		for (int i = 0; i < recipes.size(); i++){
			if (recipes.get(i).getId_recipe().equals(idrecipe)){
				recipes.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean existRecipe(String idrecipe){
		for (int i = 0; i < recipes.size(); i++){
			if (recipes.get(i).getId_recipe().equals(idrecipe)){
				return true;
			}
		}
		return false;
	}
	public boolean addRecipetoUser(Recipe recipe){
		if (!existRecipe(recipe.getId_recipe())){
			recipes.add(recipe);
			return true;
		}
		return false;
	}
}
