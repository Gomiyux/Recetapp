package com.example.carlo.recetapp.TareasAsincronas;

import java.util.ArrayList;

import com.example.carlo.recetapp.Constants;
import com.example.carlo.recetapp.Inicio_Sesion;
import com.example.carlo.recetapp.MainActivity;
import com.example.carlo.recetapp.R;
import com.example.carlo.recetapp.recipesendpoint.Recipesendpoint;
import com.example.carlo.recetapp.recipesendpoint.model.CollectionResponseUser;

import com.example.carlo.recetapp.recipesendpoint.model.Email;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by carlo on 28/08/2016.
 */
public class TareaAsincronaGetUsuario extends AsyncTask <Void, Void, User>{

    private Activity a;
    private User usuario;
    private String id_usuario;
    private String email_auxiliar;


    public TareaAsincronaGetUsuario(Activity a, String id_usuario, String email){

        super();
        this.id_usuario=id_usuario;
        this.a=a;
        this.email_auxiliar=email;

    }



    @Override
    public void onPostExecute(User result){

        /*
        EditText texto = (EditText) a.findViewById(R.id.login_usuario);
        texto.setText(usuario.getEmail().getEmail());
        EditText editText2 = (EditText) a.findViewById(R.id.login_usuario) ;
        String auxiliar = editText2.getText().toString();

        */

        //&&

        //(email_auxiliar==usuario.getEmail().getEmail())
        if ((result != null)) {

            if(result.getEmail()!=null) {




                 if (result.getEmail().getEmail().equals(email_auxiliar)) {

                     Intent i = new Intent(a, MainActivity.class);
                     i.putExtra(Constants.message0, email_auxiliar);
                     a.startActivity(i);

                } else {

                    LayoutInflater li = LayoutInflater.from(a);
                    View promptsView = li.inflate(R.layout.prompt_fail_to_login, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            a);

                    alertDialogBuilder.setView(promptsView);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

        }

        else{

            LayoutInflater li = LayoutInflater.from(a);
            View promptsView = li.inflate(R.layout.prompt_fail_to_login, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    a);

            alertDialogBuilder.setView(promptsView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    @Override
    protected User doInBackground(Void... params) {


        try{
            Recipesendpoint.Builder builder = new Recipesendpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    null);
            builder.setRootUrl("http://10.0.2.2:8888/_ah/api");

            //10.0.2.2
            builder.setApplicationName("Recetapp");
            Recipesendpoint servicio = builder.build();
            usuario= (servicio.getUser(id_usuario)).execute();

        }catch(Exception e){



        }


        return usuario;
    }



}
