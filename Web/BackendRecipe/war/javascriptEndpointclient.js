function init(){
	/* It allows the endpoint to be loaded */	
	initcallback = function(){/* Put here instructions that should be executed when the API is loaded */
		/*document.getElementById("b_adduser").disabled = false;
		document.getElementById("b_addingredient").disabled = false;
		document.getElementById("b_getuser").disabled = false;
		document.getElementById("b_addrecipe").disabled = false;
		document.getElementById("b_getrecipe").disabled = false;
		document.getElementById("b_addingredienttorecipe").disabled = false;
		document.getElementById("b_getingredient").disabled = false;
		document.getElementById("b_listuser").disabled = false;
		document.getElementById("b_listingredient").disabled = false;
		document.getElementById("b_updateusermaindata").disabled = false;
		document.getElementById("b_updaterecipe").disabled = false;
		document.getElementById("b_removeuser").disabled = false;
		document.getElementById("b_removerecipe").disabled = false;
		document.getElementById("b_removeingredient").disabled = false;*/
	};
	gapi.client.load('recipesendpoint', // Name of the Endpoint API
					 'v1', // Endpoint version
					 initcallback, // Callback that will be called when the Endpoint API is loaded
					 'http://localhost:8888/_ah/api'); // URL where the API is stored (use not local if deployed in Google)
}

// ---- ADDING A USER ----------------------------
function addUser_response(resp){
	/* It processes the response after calling addUser API method */
	if (!resp.code){
		/* Do something with the user if necessary */
		console.log(resp);
	}
}
function addUser(user){
	/* It allows a user to be added */
	gapi.client.recipesendpoint.addUser(user).execute(addUser_response);		
}
// -----------------------------------------------
// ---- ADDING AN INGREDIENT ---------------------
function addIngredient_response(resp){
	/* It processes the response after calling addIngredient API method */
	if (!resp.code){
		/* Do something with the ingredient if necessary */
		console.log(resp);
	}
}
function addIngredient(ingredient){
	/* It allows an ingredient to be added */
	gapi.client.recipesendpoint.addIngredient(ingredient).execute(addIngredient_response);
}
// -----------------------------------------------
// ---- GETTING A USER ---------------------------

function getUser(theid){
	/* It allows a user to be retrieved from the datastore by using the id */
	var requestdata = {
		id: theid,
	}; // The parameters of the API method are passed as a JSON whose fields are the list of parameters
	gapi.client.recipesendpoint.getUser(requestdata).execute(getUser_response);
}
// -----------------------------------------------
// ---- ADDING A RECIPE --------------------------
function addRecipe_response(resp){
	/* It processes the response after calling addRecipe API method */
	if (!resp.code){
		/* Do something with the recipe */
		console.log(resp);
	}
}
function addRecipe(userid, therecipe){
	/* The function needs two parameters: the user id, and the recipe that will be included as an 
	 * item of the user list of recipes */
	var requestdata = {
			id: userid,
	};
	/* In this case, the first parameter (as it is @Named) is injected with the URL (as in a GET request), the
	 * second parameter is passed as a POST parameter, because it is necessary to pass an object that it is an 
	 * entity. */
	gapi.client.recipesendpoint.addRecipe(requestdata, therecipe).execute(addRecipe_response);
}
// -----------------------------------------------
// ---- GETTING A RECIPE -------------------------

function getRecipe(userid, recipeid){
	/* The function needs two parameters: the user id and the recipe id. If the recipe exists, it is retrieved. */
	var requestdata = {
			id_recipe: recipeid,
			id: userid,
	};
	gapi.client.recipesendpoint.getRecipe(requestdata).execute(getRecipe_response);	
}
// -----------------------------------------------
// ---- ADDING INGREDIENT TO RECIPE --------------
function addSpecificIngredientToRecipe_response(resp){
	/* It processes the response after calling addSpecificIngredientToRecipe API method.
	 * The response contains the recipe modified with the new added ingredient.
	 */
	
	if (!resp.code){
		/* Do something with the recipe */
		console.log(resp);
	}
}
function addSpecificIngredientToRecipe(recipeid, userid, ingredientid, ingr_in_recipe){
	/* The function needs the recipe, user and ingredient ids. If entities exist, the object ingr_in_recipe contains
	 * the information about quantity and measurement unit used for this particular ingredient in this specific 
	 * recipe.
	 */
	var requestdata = {
			id_recipe: recipeid,
			id: userid,
			id_ingredient: ingredientid,
	};
	gapi.client.recipesendpoint.addSpecificIngredientToRecipe(requestdata, ingr_in_recipe).execute(addSpecificIngredientToRecipe_response);
}
// -----------------------------------------------
// ---- GETTING AN INGREDIENT --------------------

function getIngredient(ingredientid){
	/* The function uses the ingredient id for retrieving the information about an ingredient if this one exists */
	var requestdata = {
			id_ingredient: ingredientid,
	};
	gapi.client.recipesendpoint.getIngredient(requestdata).execute(getIngredient_response);
}
// -----------------------------------------------
// ---- LISTING USERS ----------------------------
function listUser_response(resp){
	/* It processes the response after calling listUser API method.
	 */
	if (!resp.code){
		/* Do something with the list of users */
		console.log(resp);
	}
}
function listUser(){
	/* The function does not need any parameter */
	gapi.client.recipesendpoint.listUser().execute(listUser_response);
}
// -----------------------------------------------
// ---- LISTING INGREDIENTS ----------------------

function listIngredient(){
	/* The function does not need any parameter */
	gapi.client.recipesendpoint.listIngredient().execute(listIngredient_response);
}
// -----------------------------------------------
// ---- UPDATING USER MAIN DATA ------------------
function updateUserMainData_response(resp){
	/* It processes the response after calling updateUserMainData API method.
	 */
	if (!resp.code){
		/* Do something with the user */
		console.log(resp);
	}
}
function updateUserMainData(user){
	/* The function accepts a user object as parameter. Only the id and email fields should be defined. */
	gapi.client.recipesendpoint.updateUserMainData(user).execute(updateUserMainData_response);
}
// -----------------------------------------------
// ---- UPDATING RECIPE --------------------------
function updateRecipe_response(resp){
	/* It processes the response after calling updateRecipe API method.
	 */
	if (!resp.code){
		/* Do something with the recipe */
		console.log(resp);
	}
}
function updateRecipe(userid, recipeid, recipe){
	/* The function accepts the user and the recipe ids as parameters. They will be sent as a part of the URL.
	 * The third parameter is sent in the request body and it contains the updated recipe.
	 */
	console.log("Calling updateRecipe from dynamic button onclick event");
	var requestData = {
		id: userid,
		id_recipe: recipeid,
	}
	gapi.client.recipesendpoint.updateRecipe(requestData, recipe).execute(updateRecipe_response);	
}
function showinconsole(resp){
	// It shows the results in the console for debugging purposes
	console.log(resp.id_recipe);
	console.log(resp.card.name);
	console.log(resp.card.summary);
	console.log(resp.card.type);
	console.log(resp.steps);
	if (resp.ling != null){
		console.log("ling length: ");
		console.log(resp.ling.length);
		var l = resp.ling.length;
		for (var i = 0; i < l; i++){
			console.log(resp.ling[i].id_ing);
			console.log(resp.ling[i].id_ingrrecipe);
			console.log(resp.ling[i].measurementu);
			console.log(resp.ling[i].quantity);
		}
	}
	console.log(resp);
}
function changerecipe(oldrecipe){
	var newrecipe = oldrecipe;
	newrecipe.card.name = document.getElementById("dynamic_id_recipe").value;
	newrecipe.card.summary = document.getElementById("dynamic_card_summary").value;
	newrecipe.card.type = document.getElementById("dynamic_card_type").value;
	newrecipe.steps = document.getElementById("dynamic_steps").value;
	var l = 0;
	if (newrecipe.ling != null){
		l = newrecipe.ling.length;
		for (var i = 0; i < l; i++){
			newrecipe.ling[i].id_ingrrecipe = document.getElementById("dynamic"+i+"idingrrecipe").value;
			newrecipe.ling[i].id_ing = document.getElementById("dynamic"+i+"iding").value;
			newrecipe.ling[i].measurementu = document.getElementById("dynamic"+i+"measurmentu").value;
			newrecipe.ling[i].quantity = document.getElementById("dynamic"+i+"quantity").value;
		}
	}
	console.log("New recipe: ");
	console.log(newrecipe);
	return newrecipe;
}
function showrecipeinUIforUpdating(resp, userid, recipeid){
	// Create new UI elements dynamically for showing the recipe
	var thediv = document.getElementById("divupdaterecipe");
	create_recipe_item(thediv, "Recipe Id: ", "dynamic_id_recipe", resp.id_recipe);
	create_recipe_item(thediv, "Recipe Name: ", "dynamic_card_name", resp.card.name);
	create_recipe_item(thediv, "Recipe Summary: ", "dynamic_card_summary", resp.card.summary);
	create_recipe_item(thediv, "Recipe Type: ", "dynamic_card_type", resp.card.type);
	create_recipe_item(thediv, "Recipe Steps: ", "dynamic_steps", resp.steps);
	var l = 0;
	if (resp.ling != null){
		create_recipe_item(thediv, "Number of ingredients: ", "dynamic_l", resp.ling.length);
		l = resp.ling.length;
		for (var i = 0; i < l; i++){
			create_recipe_item(thediv, "Ingredient ("+i+") Id for Recipe: ", "dynamic"+i+"idingrrecipe", resp.ling[i].id_ingrrecipe);
			create_recipe_item(thediv, "Ingredient ("+i+") Id: ", "dynamic"+i+"iding", resp.ling[i].id_ing);
			create_recipe_item(thediv, "Ingredient ("+i+") Measurement Unit: ", "dynamic"+i+"measurmentu", resp.ling[i].measurementu);
			create_recipe_item(thediv, "Ingredient ("+i+") Quantity: ", "dynamic"+i+"quantity", resp.ling[i].quantity);
		}
	}
	var buttonupdate = create_recipe_update_button(thediv, "btn btn-primary", "Update Recipe");
	buttonupdate.addEventListener("click", function(){
		var updatedrecipe = changerecipe(resp);
		updateRecipe(userid, recipeid, updatedrecipe);
	});
}
function prepare_recipe_update(userid, recipeid){
	/* First, it is necessary to get the recipe, to show the information about the recipe (including the list
	 * of ingredients), and, finally, to allow such information to be properly modified.
	 */
	var requestData = {
			id_recipe: recipeid,
			id: userid,
	}
	gapi.client.recipesendpoint.getRecipe(requestData).execute(
		function(resp){ // Get the recipe
			if (!resp.code){ // If the recipe is available, the recipe is shown
				showinconsole(resp);
				showrecipeinUIforUpdating(resp, userid, recipeid);
			}
		}
	);
}
// -----------------------------------------------
// ---- REMOVING A USER --------------------------
function removeUser_response(resp){
	/* It processes the response after calling removeUser API method */
	if (!resp.code){
		/* Do something with the user */
		console.log(resp);
	}
}
function removeUser(userid){
	/* The function needs the user id that should be removed. */
	var requestdata = {
			id: userid,
	};
	gapi.client.recipesendpoint.removeUser(requestdata).execute(removeUser_response);	
}
// -----------------------------------------------
// ---- REMOVING A RECIPE ------------------------
function removeRecipe_response(resp){
	/* It processes the response after calling removeRecipe API method */
	if (!resp.code){
		/* Do something with the updated user */
		console.log(resp);
	}
}
function removeRecipe(userid, recipeid){
	/* The function needs the user and the recipe ids. The recipe is removed and the user that owns it is updated */
	var requestdata = {
			id: userid,
			id_recipe: recipeid,
	};
	gapi.client.recipesendpoint.removeRecipe(requestdata).execute(removeRecipe_response);	
}
// -----------------------------------------------
// ---- REMOVING AN INGREDIENT -------------------
function removeIngredient_response(resp){
	/* It processes the response after calling removeIngredient API method */
	if (!resp.code){
		/* Do something with the user */
		console.log(resp);
	}
}
function removeIngredient(ingredientid){
	/* The function needs the user id that should be removed. */
	var requestdata = {
			id_ingredient: ingredientid,
	};
	gapi.client.recipesendpoint.removeIngredient(requestdata).execute(removeIngredient_response);	
}
// -----------------------------------------------






