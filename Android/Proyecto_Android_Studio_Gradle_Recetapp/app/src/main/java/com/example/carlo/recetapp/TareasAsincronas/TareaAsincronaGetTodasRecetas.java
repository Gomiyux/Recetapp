package com.example.carlo.recetapp.TareasAsincronas;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

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
 * Created by carlo on 03/09/2016.
 */
public class TareaAsincronaGetTodasRecetas extends AsyncTask<Void, Void, List<User>> {

        private Activity a;

        private ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        List<String> expandableListTitle;
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        public TareaAsincronaGetTodasRecetas(Activity a)
        {
            this.a = a;

        }

        @Override
        protected List<User> doInBackground(Void... params) {

            CollectionResponseUser auxiliar;
            List<User> listuser = null;
            try{
                Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        null);
                builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
                //10.0.2.2
                builder.setApplicationName("Recetapp");
                Recipesendpoint servicio = builder.build();
                auxiliar = servicio.listUser().execute();

                listuser=auxiliar.getItems();


            }catch(Exception e){

            }


            return listuser;
        }

        @Override
        public void onPostExecute(List<User> result) {

            if (result != null) {



                    expandableListView = (ExpandableListView) a.findViewById(R.id.expandableListView3);


                    for(int j =0; j<result.size(); j++){
                        if(result.get(j).getRecipes()!=null){
                             for (int i = 0; i < result.get(j).getRecipes().size(); i++) {

                                List<String> aux = new ArrayList<>();

                                Recipe auxiliar = result.get(j).getRecipes().get(i);

                                aux.add("Id de receta: " + auxiliar.getIdRecipe());
                                aux.add("Pasos a Seguir: " + auxiliar.getSteps());
                                aux.add("Resumen de la receta: " + auxiliar.getCard().getSummary());
                                aux.add("Tipo de receta: " + auxiliar.getCard().getType());


                                 if(auxiliar.getLing()!=null) {

                                     /*
                                     String ingrediente_line = "<font color=\\\"#d67601\\\"> INGREDIENTES </font>";
                                     aux.add((Html.fromHtml(ingrediente_line)).toString());
                                     */

                                     aux.add(" -------  -------  INGREDIENTES  -------  -------");



                                 for(int k=0; k<auxiliar.getLing().size();k++){
                                     Ingrrecipe ingaux = new Ingrrecipe();
                                     ingaux=auxiliar.getLing().get(k);
                                     aux.add("\t\t" + ingaux.getQuantity() + " " + ingaux.getMeasurementu() + " de " + ingaux.getIdIng());
                                 }}

                                expandableListDetail.put(auxiliar.getCard().getName(), aux);
                    }}
                    }

                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(a, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                    expandableListView.refreshDrawableState();





                    expandableListAdapter = new CustomExpandableListAdapter(a, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);}


        }




}
