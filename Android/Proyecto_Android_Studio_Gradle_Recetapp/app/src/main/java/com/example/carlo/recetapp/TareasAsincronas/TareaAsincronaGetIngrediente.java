package com.example.carlo.recetapp.TareasAsincronas;

import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class TareaAsincronaGetIngrediente extends AsyncTask <Void, Void, Ingredient> {
	
	private Activity a;
	private String id_ingredient;
	
	TareaAsincronaGetIngrediente(Activity a, String idIngredient){
		this.a = a;
		this.id_ingredient = idIngredient;
	}

	@Override
	protected Ingredient doInBackground(Void... params) {
		Ingredient ing = null;
		try{
			Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
											  new GsonFactory(),
											  null);
			builder.setRootUrl("http://10.0.2.2:8888/_ah/api");

			//10.0.2.2
			Recipesendpoint servicio = builder.build();
			ing = (servicio.getIngredient(id_ingredient)).execute();
		}catch(Exception e){
			
		}
		return ing;
	}
	
	@Override
	public void onPostExecute(Ingredient result){
		/*
		if (result != null){
			FragmentoIngredient fragmento = new FragmentoIngredient();
			fragmento.setIng(result);
			a.getFragmentManager().beginTransaction().replace(R.id.LayoutPrincipal, fragmento).addToBackStack(null).commit();
		}
		else{
			Toast.makeText(a, "El ingrediente no est� disponible", Toast.LENGTH_SHORT).show();
		}*/
	}

}
