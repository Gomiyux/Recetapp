package com.example.backendrecipe;

import com.example.backendrecipe.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "recipesendpoint",
	 version = "v1",
	 namespace = @ApiNamespace(
			 ownerDomain = "example.com", 
			 ownerName = "example.com", 
			 packagePath = "recipebackend")
)

public class RecipesEndPoint {

	@ApiMethod(name = "addUser", httpMethod = HttpMethod.POST)
	/* This method accepts an object user with all the fields filled except the key, that it is 
	 * calculated from the user id. In this case the list of recipes is empty because we are adding
	 * the user for the first time. 
	 * */
	public User addUser(User user){
		PersistenceManager mgr = getPersistenceManager();
		try{
			if (user != null){
				user.createKey();
				if (containsUser(user)){
					throw new EntityExistsException("User already exists");
				}
				// Only if the object is not in the Datastore, it is inserted on it
				mgr.makePersistent(user);
			}
		}finally{
			mgr.close();
		}
		return user;		
	}
	
	@ApiMethod(name = "addIngredient", httpMethod = HttpMethod.POST)
	/* This method accepts an object of class Ingredient with all the fields filled except the key, 
	 * that it is calculated from the ingredient id 
	 * */
	public Ingredient addIngredient(Ingredient ing){
		PersistenceManager mgr = getPersistenceManager();
		try{
			if (ing != null){
				ing.createKey();
				if (containsIngredient(ing)){
					throw new EntityExistsException("Ingredient already exists");
				}
				mgr.makePersistent(ing);
			}
		}finally{
			mgr.close();
		}
		return ing;
	}
	
	@ApiMethod(name = "getUser", httpMethod = HttpMethod.GET)
	/* This method accepts the User id and get the user if it is stored in the Datastore
	 */
	public User getUser(@Named ("id") String id){
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		try{
			user = mgr.getObjectById(User.class, KeyFactory.createKey(User.class.getSimpleName(), id));
			fetchingUserFully (user);// Fetching all the attributes of a user
		}finally{
			mgr.close();
		}
		return user;
	}
	
	@ApiMethod(name = "addRecipe", httpMethod = HttpMethod.PUT)
	/* This method accepts an object of class Recipe with all the fields filled except the key, 
	 * that it is calculated from the Recipe id. The recipe should be assigned to a specific user when
	 * it is created.
	 * The id of the user is needed because the recipe will be stored in the Datastore and the user
	 * will be modified by adding the recipe to the list of recipes 
	 * */
	public Recipe addRecipe(@Named ("id") String id,
							Recipe recipe){
		boolean recipeadded = false;
		if (recipe != null){
			PersistenceManager mgr = getPersistenceManager();
			User theuser = getUserwithOpenedmgr(mgr, id);
			if (theuser != null){
				recipe.createKey(KeyFactory.createKey(User.class.getSimpleName(), id));
				recipeadded = theuser.addRecipetoUser(recipe);
				if (recipeadded){
					try{
						mgr.makePersistent(theuser);
					}finally{
						mgr.close();
					}
				}
				else{
					mgr.close();
					return (null); // The recipe is in the datastore and could not be added again
				}
			}
			else{
				mgr.close();
				return (null); // This means that the recipe has not been added because the user does not exist
			}
		}
		return recipe;
	}
	
	@ApiMethod(name = "getRecipe", httpMethod = HttpMethod.GET)
	/* This method retrieves a recipe by using the recipe id and by considering the user key calculated from the user id.
	 */
	public Recipe getRecipe (@Named("id_recipe") String id_recipe,
							 @Named("id") String id){
		Recipe recipe = null;
		PersistenceManager mgr = getPersistenceManager();
		try{
			Key keyrecipe = new KeyFactory
					.Builder(User.class.getSimpleName(), id)
					.addChild(Recipe.class.getSimpleName(), id_recipe).getKey();
			recipe = mgr.getObjectById(Recipe.class, keyrecipe);
			fetchingRecipeFully(recipe);
		}finally{
			mgr.close();
		}
		return recipe;
	}
	
	@ApiMethod(name = "addSpecificIngredientToRecipe", httpMethod = HttpMethod.PUT)
	/* This method adds a specific ingredient to a Recipe that it is owned by a specific user. Several parameters are needed:
	 * the recipe id, the user id, the id of the ingredient (the ingredient should be exist) and the ingrrecipe that should
	 * only be filled with the id, the measurement unit and the quantity of the product
	 */
	public Recipe addSpecificIngredientToRecipe(@Named("id_recipe") String id_recipe,
												@Named("id") String id,
												@Named("id_ingredient") String id_ingredient,
												Ingrrecipe ingrrecipe){
		Recipe recipe = null;
		Ingredient ingredient = null;
		PersistenceManager mgr = getPersistenceManager();
		// First, it is necessary to find the recipe
		recipe = getRecipewithOpenedmgr(mgr, id_recipe, id);
		if (recipe == null){
			mgr.close();
			return (null); // The recipe is not found or it cannot be retrieved
		}
		// Once the recipe has been found, it is necessary to find the ingredient in the set of the global ingredients
		ingredient = getIngredientwithOpenedmgr(mgr, id_ingredient);
		if (ingredient == null){
			mgr.close();
			return (null); // The ingredient is not available in the general list of ingredients
		}
		// Here the recipe and the ingredient are available, therefore, the keys are calculated with the aim of
		// creating the ingrrecipe key, since such key is calculated from the id of the ingrrecipe and the key parent,
		// that is, the key recipe
		Key userkey = KeyFactory.createKey(User.class.getSimpleName(), id);
		Key recipekey = KeyFactory.createKey(userkey, Recipe.class.getSimpleName(), id_recipe);
		ingrrecipe.createKey(recipekey);
		ingrrecipe.setId_ing(id_ingredient); // Only the key of the general ingredient is stored in the ingrrecipe
		if (recipe.addIngrrecipe(ingrrecipe)){
			// When the ingrrecipe is added, it is necessary to update the recipe
			try{
				mgr.makePersistent(recipe);				
			}finally{
				mgr.close();
			}
		}	
		else{
			mgr.close();
			return (null); // The specific ingredient has not been added
		}
		return(recipe); // Here the modified recipe is returned
	}
	
	@ApiMethod(name = "getIngredient", httpMethod = HttpMethod.GET)
	/* This method retrieves an ingredient from its id
	 */
	public Ingredient getIngredient(@Named("id_ingredient") String id_ingredient){
		Ingredient ingredient = null;
		PersistenceManager mgr = getPersistenceManager();
		try{
			ingredient = mgr.getObjectById(Ingredient.class, KeyFactory.createKey(Ingredient.class.getSimpleName(), id_ingredient));
			fetchingIngredientFully(ingredient);
		}
		finally{
			mgr.close();
		}
		return(ingredient);
	}
	
	@SuppressWarnings({ "unchecked" })
	@ApiMethod(name = "listUser", httpMethod = HttpMethod.GET)
	public CollectionResponse<User> listUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(User.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<User>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (User obj : execute)
				fetchingUserFully(obj);
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "listIngredient", httpMethod = HttpMethod.GET)
	/* This method lists all the available ingredients
	 */
	public List<Ingredient>listIngredient(){
		List<Ingredient> ingredients = null;
		PersistenceManager mgr = null;
		try{
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Ingredient.class);
			ingredients = (List<Ingredient>)query.execute();
			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Ingredient obj : ingredients)
				fetchingIngredientFully(obj);
		}
		finally{
			mgr.close();
		}
		return ingredients;
	}
	
	@ApiMethod(name = "updateUserMainData", httpMethod = HttpMethod.PUT)
	/* This method update the main data of a specific user. The id cannot be changed.
	 * It accepts a User object with the attribute recipes to null
	 */
	public User updateUserMainData (User user){
		PersistenceManager mgr = getPersistenceManager();
		User storeduser = getUserwithOpenedmgr(mgr, user.getId());
		if (storeduser != null){ // The user exists in the Datastore
			storeduser.setEmail(user.getEmail()); // The email is modified
		}
		else{
			mgr.close();
			return null; // The user does not exist, nothing will be updated and the function ends
		}
		try{
			mgr.makePersistent(storeduser); // The modified user is now persistent
		}finally{
			mgr.close();
		}
		return storeduser;
	}
	
	@ApiMethod(name = "updateRecipe", httpMethod = HttpMethod.PUT)
	/* This method allows an existing recipe to be modified. The recipe object is passed as a parameter
	 * with the modified data. All the fields of the recipe should be properly defined. The key will be generated again
	 * during the execution of updateRecipe by taking the user id and the recipe id.
	 */
	public Recipe updateRecipe (@Named ("id") String id, @Named ("id_recipe") String id_recipe, Recipe recipe){
		PersistenceManager mgr = getPersistenceManager();
		Recipe storedrecipe = getRecipewithOpenedmgr(mgr, id_recipe, id); // Checking if the recipe is available
		if (storedrecipe != null){
			recipe.setId_recipe(storedrecipe.getId_recipe());
			recipe.createKey(KeyFactory.createKey(User.class.getSimpleName(), id));
			// We ensure that the key is the same that the previously stored one, because the id and key should not change
			try{
				mgr.deletePersistentAll(storedrecipe.getLing()); // All the ingredients are deleted first
				for (int i = 0; i < recipe.getLing().size(); i++){ // The key is created for each ingrrecipe
					Key userkey = KeyFactory.createKey(User.class.getSimpleName(), id);
					Key recipekey = KeyFactory.createKey(userkey, Recipe.class.getSimpleName(), id_recipe);
					// It is necessary to calculate the key parent first
					recipe.getLing().get(i).createKey(recipekey);
				}
				mgr.makePersistent(recipe); // The updated recipe is stored
			}finally{
				mgr.close();
			}
			
		}
		else{
			mgr.close();
			return null; // It is not possible to update the recipe
		}
		return (recipe);
	}
	
	@ApiMethod(name = "removeUser", httpMethod = HttpMethod.DELETE)
	/* This method removes a user. The parameter is the is of the user that will be removed
	 * If a user is deleted, all his/her recipes should be deleted too
	 */
	public void removeUser(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			User user = mgr.getObjectById(User.class, KeyFactory.createKey(User.class.getSimpleName(), id));
			if (user != null){ // It is necessary to delete all the recipes
				mgr.deletePersistentAll(user.getRecipes());
				mgr.deletePersistent(user);
			}
		} finally {
			mgr.close();
		}
	}

	@ApiMethod(name = "removeRecipe", httpMethod = HttpMethod.POST)
	/* This method removes a specific Recipe that belongs to a user. Both user and recipe ids are needed to access the recipe.
	 * The recipe is removed by updating the user
	 */
	public User removeRecipe(@Named("id") String id, @Named("id_recipe") String id_recipe){
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		try{
			user = mgr.getObjectById(User.class, KeyFactory.createKey(User.class.getSimpleName(), id));
			if (user != null){
				user.deleteRecipefromArray(id_recipe);
				mgr.makePersistent(user);
			}
		} finally{
			mgr.close();
		}
		return user;
	}
	
	@ApiMethod(name = "removeIngredient", httpMethod = HttpMethod.DELETE)
	/* This method removes a specific Recipe that belongs to a user. Both user and recipe ids are needed to access the recipe.
	 * The recipe is removed by updating the user
	 */
	public Ingredient removeIngredient(@Named("id_ingredient") String id_ingredient){
		PersistenceManager mgr = getPersistenceManager();
		Ingredient ingredient = null;
		try{
			ingredient = mgr.getObjectById(Ingredient.class, KeyFactory.createKey(Ingredient.class.getSimpleName(), id_ingredient));
			if (ingredient != null){
				mgr.deletePersistent(ingredient);
			}
		} finally{
			mgr.close();
		}
		return ingredient;
	}

	
	
// -------------------------------------------------------------------------------------------------------
// Private methods that are used by the apimethods
// -------------------------------------------------------------------------------------------------------
	private User getUserwithOpenedmgr(PersistenceManager mgr, String id){
	// Method that allows a user to be retrieved by using a opened mgr
		User theuser = null;
		try{
			theuser = mgr.getObjectById(User.class, KeyFactory.createKey(User.class.getSimpleName(), id));
			fetchingUserFully (theuser);// Fetching all the attributes of a user
		}catch(Exception e){
			return null; // If there is any exception null is returned
		}
		return theuser;
	}
	
	private Recipe getRecipewithOpenedmgr(PersistenceManager mgr, String id_recipe, String id){
		Recipe recipe = null;
		try{
			Key userkey = KeyFactory.createKey(User.class.getSimpleName(), id);
			recipe = mgr.getObjectById(Recipe.class, KeyFactory.createKey(userkey, Recipe.class.getSimpleName(), id_recipe));
			fetchingRecipeFully(recipe);
		}catch(Exception e){
			return null;
		}
		return recipe;
	}
	
	private Ingredient getIngredientwithOpenedmgr(PersistenceManager mgr, String id_ingredient){
		Ingredient ingredient = null;
		try{
			ingredient = mgr.getObjectById(Ingredient.class, KeyFactory.createKey(Ingredient.class.getSimpleName(), id_ingredient));
			fetchingIngredientFully(ingredient);
		}catch(Exception e){
			return null;
		}
		return ingredient;
		
	}
	
	private boolean containsUser(User user) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(User.class, user.getKey());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}
	
	private boolean containsIngredient(Ingredient ing) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Ingredient.class, ing.getKey());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
	
	private void fetchingUserFully(User user){
		// This method allows to fetch all the attributes of a specific user, including the list of recipes
		user.getId();
		user.getEmail();
		for (int i = 0; i < user.getRecipes().size(); i++){
			fetchingRecipeFully(user.getRecipes().get(i));
		}
	}
	
	private void fetchingRecipeFully(Recipe recipe){
		// This method allows to fetch all the attributes of a specific recipe, including the list of ingredients
		recipe.getId_recipe();
		recipe.getCard();
		recipe.getSteps();
		for (int i = 0; i < recipe.getLing().size(); i++){
			recipe.getLing().get(i).getId_ingrrecipe();
			recipe.getLing().get(i).getId_ing();
			recipe.getLing().get(i).getMeasurementu();
			recipe.getLing().get(i).getQuantity();
		}
	}
	
	private void fetchingIngredientFully(Ingredient ingredient){
		ingredient.getId_ingredient();
		ingredient.getName();
		ingredient.getNutritionaldesc();
	}

}
