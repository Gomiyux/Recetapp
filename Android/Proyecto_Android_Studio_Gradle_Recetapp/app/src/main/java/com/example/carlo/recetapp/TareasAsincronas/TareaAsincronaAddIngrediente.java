package com.example.carlo.recetapp.TareasAsincronas;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

/**
 * Created by carlo on 02/09/2016.
 */
public class TareaAsincronaAddIngrediente extends AsyncTask<Void, Void, Boolean> {



        private Ingredient ingrediente;
        private Activity a;
        String id_usuario;
        Recipe receta_auxiliar;

        public TareaAsincronaAddIngrediente(Activity a, Ingredient ingrediente){

            super();
            this.a = a;
            this.ingrediente=ingrediente;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try{
                Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        null);
                builder.setRootUrl("http://10.0.2.2:8888/_ah/api");
                //10.0.2.2
                builder.setApplicationName("Recetapp");
                Recipesendpoint servicio = builder.build();

                (servicio.addIngredient(ingrediente)).execute();

                return true;

            }catch(IOException e){

                return false;
            }


        }


        @Override
        public void onPostExecute(Boolean result){

        /*
        EditText texto = (EditText) a.findViewById(R.id.login_usuario);
        texto.setText(usuario.getEmail().getEmail());
        */

            if(result){

                LayoutInflater li = LayoutInflater.from(a);
                View promptsView = li.inflate(R.layout.promt_add_ingrediente, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        a);

                alertDialogBuilder.setView(promptsView);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

            else{
            LayoutInflater li = LayoutInflater.from(a);
            View promptsView = li.inflate(R.layout.promt_add_ingrediente_fail, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    a);

            alertDialogBuilder.setView(promptsView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

                // Toast.makeText(a, "La receta no se ha podido añadir", Toast.LENGTH_SHORT).show();
            }


        }





}
