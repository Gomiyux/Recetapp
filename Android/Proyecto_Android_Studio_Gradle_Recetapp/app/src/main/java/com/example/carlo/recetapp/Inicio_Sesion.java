package com.example.carlo.recetapp;

/**
 * Created by carlo on 28/08/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.carlo.recetapp.ClasesAntiguas.Receta;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetUsuario;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaUsuario;


public class Inicio_Sesion extends Activity {

    Button login, registrarse;
    Context context;
    private Thread thread;
    Activity activi = this;
    String id_auxiliar;
    Boolean correcto;
    EditText editText;
    EditText editText2;
    String email_auxiliar;

    public void setCorrecto(Boolean aux){
        this.correcto=aux;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_login_form);

        int color = Color.parseColor("#000000");
        editText = (EditText) findViewById(R.id.login_contraseña) ;
        editText2 = (EditText) findViewById(R.id.login_usuario) ;


        editText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        editText2.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        login = (Button) findViewById(R.id.entrar);
        context=this.getApplicationContext();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id_auxiliar=editText.getText().toString();
                email_auxiliar=editText2.getText().toString();



                if(email_auxiliar.equals("admin")){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(Constants.message0, email_auxiliar);
                startActivity(i);}

                else {
                    TareaAsincronaGetUsuario getuser = new TareaAsincronaGetUsuario(activi, id_auxiliar, email_auxiliar);
                    getuser.execute();
                }

            }
        });

        registrarse = (Button) findViewById(R.id.registrase);
        context=this;



        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
                */


                        // get prompt_rellena_receta.xmlena_receta.xml view
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_registrarse, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set prompt_rellena_recetarellena_receta.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.nuevousuarioemail);
                        final EditText userInput2 = (EditText) promptsView
                                .findViewById(R.id.nuevousuarioid);


                        userInput.requestFocus();

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // get user input and set it to result
                                                // edit text
                                                //result.setText(userInput.getText());


                                                TareaAsincronaUsuario auxiliar = new TareaAsincronaUsuario(activi,userInput.getText().toString(),userInput2.getText().toString());
                                                auxiliar.execute();











                                            }
                                        })
                                .setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it

                        alertDialog.show();


                    }
        });

    }


}