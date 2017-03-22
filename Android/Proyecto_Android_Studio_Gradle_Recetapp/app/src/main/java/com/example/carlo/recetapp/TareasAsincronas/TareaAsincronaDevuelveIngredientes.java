package com.example.carlo.recetapp.TareasAsincronas;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.example.carlo.recetapp.CustomExpandableListAdapter;
import com.example.carlo.recetapp.MainActivity;
import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.IngredientCollection;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlo on 04/09/2016.
 */
public class TareaAsincronaDevuelveIngredientes extends AsyncTask<Void, Void, ArrayList<Ingredient>> {

        private Activity a;
        private ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        private ArrayList<Ingredient> devolver;
        List<String> expandableListTitle;
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        public TareaAsincronaDevuelveIngredientes(Activity a){
            this.a = a;

        }

        @Override
        protected ArrayList<Ingredient> doInBackground(Void... params) {

            IngredientCollection listaaux;


            ArrayList<Ingredient> listingredient = new ArrayList<>();
            try{
                Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        null);
                builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
                //10.0.2.2
                Recipesendpoint servicio = builder.build();
                listaaux = (servicio.listIngredient()).execute();

                if(listaaux!=null) {
                    for (int i = 0; i < listaaux.getItems().size(); i++) {

                        listingredient.add(listaaux.getItems().get(i));
                    }
                }





            }catch(Exception e){

            }
            return listingredient;
        }

        @Override
        public void onPostExecute(ArrayList<Ingredient> result){

            if (result != null) {

                ArrayList<String> listingredient = new ArrayList<>();

                for (int i = 0; i < result.size(); i++) {

                    listingredient.add(result.get(i).getIdIngredient());
                }

                ((MainActivity) a).ingredientes=result;
                ((MainActivity) a).id_ing=listingredient;



                /*
                expandableListTitle= new ArrayList<>();

                Spinner dropdown = (Spinner)a.findViewById(R.id.lista_ingredientes);


                if(dropdown!=null){
                    dropdown = (Spinner)a.findViewById(R.id.lista_ingredientes);



                for(int i=0; i<result.size();i++) {

                    if(result.get(i)!=null) {
                        expandableListTitle.add(result.get(i).getIdIngredient());
                    }
                }




                ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(a.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, expandableListTitle.toArray());
                dropdown.setAdapter(adapter);

            }*/
                }

        }






}
