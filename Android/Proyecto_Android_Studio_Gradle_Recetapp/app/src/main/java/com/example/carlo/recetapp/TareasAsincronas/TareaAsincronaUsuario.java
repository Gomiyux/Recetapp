package com.example.carlo.recetapp.TareasAsincronas;

import java.io.IOException;
import java.util.ArrayList;

import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.CollectionResponseUser;

import com.example.carlo.recetapp.recipesendpoint.model.Email;
import com.example.carlo.recetapp.recipesendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by carlo on 28/08/2016.
 */
public class TareaAsincronaUsuario extends AsyncTask <Void, Void, Boolean>{

    private Activity a;
    Boolean aceptado=false;
    private String id;
    private String email;
    private User usuario;
    private Email email2;

    public TareaAsincronaUsuario(Activity a, String id, String email){

        super();
        this.a = a;
        this.email=email;
        this.id=id;
        usuario = new User();
        email2 = new Email();
        email2.setEmail(email);
        usuario.setEmail(email2);
        usuario.setId(id);


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
            aceptado=true;
            (servicio.addUser(usuario)).execute();



        }catch(IOException e){

            aceptado= false;
        }

        return aceptado;
    }


    @Override
    public void onPostExecute(Boolean result){

        /*
        EditText texto = (EditText) a.findViewById(R.id.login_usuario);
        texto.setText(usuario.getEmail().getEmail());
        */


        if (aceptado==true){

            LayoutInflater li = LayoutInflater.from(a);
            View promptsView = li.inflate(R.layout.prompt_postsign, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    a);

            alertDialogBuilder.setView(promptsView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        else{

            LayoutInflater li = LayoutInflater.from(a);
            View promptsView = li.inflate(R.layout.prompt_postsign_false, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    a);

            alertDialogBuilder.setView(promptsView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();



        }

    }



}
