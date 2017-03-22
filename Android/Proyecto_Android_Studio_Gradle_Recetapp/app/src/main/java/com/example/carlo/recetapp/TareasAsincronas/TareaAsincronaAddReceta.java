package com.example.carlo.recetapp.TareasAsincronas;

import java.io.IOException;
import java.util.ArrayList;

import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.CollectionResponseUser;

import com.example.carlo.recetapp.recipesendpoint.model.Email;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;
import com.example.carlo.recetapp.recipesendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by carlo on 28/08/2016.
 */
public class TareaAsincronaAddReceta extends AsyncTask <Void, Void, Boolean>{

    private Activity a;
    String id_usuario;
    Recipe receta_auxiliar;

    public TareaAsincronaAddReceta(Activity a, String id_usuario, Recipe receta_auxiliar){

        super();
        this.a = a;
        this.id_usuario=id_usuario;
        this.receta_auxiliar=receta_auxiliar;

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

            (servicio.addRecipe(id_usuario, receta_auxiliar)).execute();

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
            View promptsView = li.inflate(R.layout.prompt_add_receta, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    a);

            alertDialogBuilder.setView(promptsView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        else{

            LayoutInflater li = LayoutInflater.from(a);
            View promptsView = li.inflate(R.layout.prompt_add_receta_fail, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    a);

            alertDialogBuilder.setView(promptsView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

          // Toast.makeText(a, "La receta no se ha podido añadir", Toast.LENGTH_SHORT).show();
        }


    }



}
