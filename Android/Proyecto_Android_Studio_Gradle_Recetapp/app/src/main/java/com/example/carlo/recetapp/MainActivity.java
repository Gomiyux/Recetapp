package com.example.carlo.recetapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlo.recetapp.ClasesAntiguas.Receta;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaDevuelveIngredientes;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetRecetas;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,TodasRecetas.OnFragmentInteractionListener,Ayuda.OnFragmentInteractionListener, Contacto.OnFragmentInteractionListener,ingredientes_fragment.OnFragmentInteractionListener,  index.OnFragmentInteractionListener, recetas_fragment.OnFragmentInteractionListener {

    DrawerLayout drawer;
    private ArrayList<Recipe> recetas = new ArrayList<>();
    public String Id_usuario;
    public ArrayList<String> id_ing = new ArrayList<>();
    public ArrayList<Ingredient> ingredientes = new ArrayList<>();

    public void addReceta(Recipe aux){
        recetas.add(aux);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(window.FEATURE_NO_TITLE);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent i= getIntent();
        Id_usuario=i.getStringExtra(Constants.message0);

        TareaAsincronaDevuelveIngredientes tare= new TareaAsincronaDevuelveIngredientes(this);
        tare.execute();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {

        /*
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        TextView idd = (TextView) findViewById(R.id.id_mostrar);
        idd.setText("Id: "+ Id_usuario);
        //this.pascua();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        boolean fragmentTransaction = false;
        Fragment fragment = null;

        int id = item.getItemId();

        //LLamadas a las otras activitys desde el menu

        if (id == R.id.recetas) {

            fragment = new recetas_fragment();
            fragmentTransaction = true;

        }

        else if( id == R.id.inicio){

            fragment = new index();
            fragmentTransaction = true;

        }

        //contacta
          else if (id == R.id.nav_about) {
            fragment = new Contacto();
            fragmentTransaction = true;
        }

        else if (id == R.id.ingredientes) {
            fragment = new ingredientes_fragment();
            fragmentTransaction = true;

        }

        else if (id == R.id.todasrecetas) {
            fragment = new TodasRecetas();
            fragmentTransaction = true;
        }
        //Ayuda
        else if (id == R.id.nav_help) {
            fragment = new Ayuda();
            fragmentTransaction = true;
        }

        if(fragmentTransaction) {

            Bundle bundle = new Bundle();
            bundle.putString(Constants.message0, Id_usuario);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
            fragmentTransaction=false;
        }

        drawer.closeDrawers();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
