package com.example.carlo.recetapp.TareasAsincronas;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.carlo.recetapp.CustomExpandableListAdapter;
import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.CollectionResponseUser;
import com.example.carlo.recetapp.recipesendpoint.model.Ingrrecipe;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;
import com.example.carlo.recetapp.recipesendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlo on 31/08/2016.
 */
public class TareaAsincronaGetRecetas extends AsyncTask<Void, Void, ArrayList<Recipe>> {

    private Activity a;
    private String Id_usuario;
    private ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

    public TareaAsincronaGetRecetas(Activity a, String Id_usuario)
    {
        this.a = a;
        this.Id_usuario=Id_usuario;
    }

    @Override
    protected ArrayList<Recipe> doInBackground(Void... params) {

        User auxiliar;
        ArrayList<Recipe> listrecipes = null;
        try{
            Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    null);
            builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
            //10.0.2.2
            builder.setApplicationName("Recetapp");
            Recipesendpoint servicio = builder.build();
            auxiliar = (servicio.getUser(Id_usuario).execute());

            if(auxiliar.getRecipes().size()>0){
                listrecipes=new ArrayList<>();
                for(int i=0 ; i<auxiliar.getRecipes().size(); i++ ){

                    listrecipes.add(auxiliar.getRecipes().get(i));

                }
            }


        }catch(Exception e){

        }
        return listrecipes;
    }

    @Override
    public void onPostExecute(ArrayList<Recipe> result) {

        if (result != null) {

            if (result.size() > 0) {


                expandableListView = (ExpandableListView) a.findViewById(R.id.expandableListView);


                for (int i = 0; i < result.size(); i++) {

                    List<String> aux = new ArrayList<>();

                    Recipe auxiliar = result.get(i);

                    aux.add("Id de receta: " + auxiliar.getIdRecipe());
                    aux.add("Pasos a Seguir: " + auxiliar.getSteps());
                    aux.add("Resumen de la receta: " + auxiliar.getCard().getSummary());
                    aux.add("Tipo de receta: " + auxiliar.getCard().getType());


                    if(result.get(i).getLing()!=null) {

                        aux.add(" -------  -------  INGREDIENTES  -------  -------");
                        for (int j = 0; j < result.get(i).getLing().size(); j++) {
                            Ingrrecipe ingaux = new Ingrrecipe();
                            ingaux = result.get(i).getLing().get(j);
                            aux.add("\t\t" + ingaux.getQuantity()+ " " + ingaux.getMeasurementu() + " de " + ingaux.getIdIng());
                        }
                    }
                    expandableListDetail.put(auxiliar.getCard().getName(), aux);

                }
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(a, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListView.refreshDrawableState();





            expandableListAdapter = new CustomExpandableListAdapter(a, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);}
        }
		/*
		AdapterLista adapter = new AdapterLista(a, R.layout.layoutuser,result);
		adapter.setActivity(a);
		ListView lv = (ListView)a.findViewById(R.id.listView1);
		lv.setAdapter(adapter);*/
        }


}
