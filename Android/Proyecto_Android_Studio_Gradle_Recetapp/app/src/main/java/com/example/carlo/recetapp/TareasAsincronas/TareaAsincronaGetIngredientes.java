package com.example.carlo.recetapp.TareasAsincronas;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.carlo.recetapp.CustomExpandableListAdapter;
import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.CollectionResponseUser;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.IngredientCollection;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;
import com.example.carlo.recetapp.recipesendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlo on 02/09/2016.
 */
public class TareaAsincronaGetIngredientes extends AsyncTask<Void, Void, ArrayList<Ingredient>> {

    private Activity a;
    private ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    private ArrayList<Ingredient> devolver;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

    public TareaAsincronaGetIngredientes(Activity a){
        this.a = a;

    }

    @Override
    protected ArrayList<Ingredient> doInBackground(Void... params) {

        IngredientCollection listaaux;

        ArrayList<Ingredient> listingredient = null;
        try{
            Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    null);
            builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
            //10.0.2.2
            Recipesendpoint servicio = builder.build();
            listaaux = (servicio.listIngredient()).execute();


            listingredient = (ArrayList<Ingredient>) listaaux.getItems();


        }catch(Exception e){

        }
        return listingredient;
    }

    @Override
    public void onPostExecute(ArrayList<Ingredient> result){

        if (result != null) {

            if (result.size() > 0) {


                expandableListView = (ExpandableListView) a.findViewById(R.id.expandableListViewingredient);


                for (int i = 0; i < result.size(); i++) {

                    List<String> aux = new ArrayList<>();

                    Ingredient auxiliar = result.get(i);


                    aux.add("Id del Ingrediente: " + auxiliar.getIdIngredient());
                    aux.add("Descripcion nutricional: " + auxiliar.getNutritionaldesc());


                    expandableListDetail.put(auxiliar.getName(), aux);

                }
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(a, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListView.refreshDrawableState();





                expandableListAdapter = new CustomExpandableListAdapter(a, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);}
        }

    }


}

