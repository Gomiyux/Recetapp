package com.example.carlo.recetapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.carlo.recetapp.ClasesAntiguas.Ingrediente;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaAddIngrediente;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaAddReceta;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaDevuelveIngredientes;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetIngredientes;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetRecetas;
import com.example.carlo.recetapp.recipesendpoint.model.CardInfo;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link recetas_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link recetas_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ingredientes_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean Mutex;

    private OnFragmentInteractionListener mListener;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);

    private Button button;
    private String id_usuario;
    private EditText result;
    private Activity backup = getActivity();


    @Override
    public void onResume() {
        super.onResume();
       //lista();

    }


    Context context;
    private ArrayList<Ingredient> ingredients_list = new ArrayList<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    //HashMap<String, List<String>> expandableListDetail;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();



    /*
    public void llena_lista(){

        if(ingredients_list.size()>0) {

            List<String> aux = new ArrayList<>();

            Recipe auxiliar = ingredients_list.get(ingredients_list.size()-1);

            aux.add("Id de receta: " + auxiliar.getIdRecipe());
            aux.add("Pasos a Seguir: " + auxiliar.getSteps());
            aux.add("Resumen de la receta: " + auxiliar.getCard().getSummary());
            aux.add("Tipo de receta: " +  auxiliar.getCard().getType());


            expandableListDetail.put(auxiliar.getCard().getName(), aux);}


    }


    public void lista(){


        expandableListView = (ExpandableListView) getView().findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this.getActivity(), expandableListTitle, expandableListDetail);
        this.llena_lista();
        expandableListView.setAdapter(expandableListAdapter);

    }
*/


    public ingredientes_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recetas_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ingredientes_fragment newInstance(String param1, String param2) {

        ingredientes_fragment fragment = new ingredientes_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        TareaAsincronaGetIngredientes tarea = new TareaAsincronaGetIngredientes(this.getActivity());
        tarea.execute();

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_ingrediente);

        //lista();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                arg0.startAnimation(buttonClick);
                arg0.startAnimation(buttonClick);

                // get prompt_rellena_receta.xmlena_receta.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_rellena_ingrediente, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompt_rellena_receta.xmlena_receta.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.nombre_ingrediente_in);

                final EditText userInput2 = (EditText) promptsView
                        .findViewById(R.id.id_ingrediente_in);

                final EditText userInput3 = (EditText) promptsView
                        .findViewById(R.id.descripcion_ingrediente_in);


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
                                        if((userInput.getText().toString().length()!=0) && (userInput2.getText().toString().length()!=0) && (userInput3.getText().toString().length()!=0)) {

                                            Ingredient auxiliar = new Ingredient();
                                            auxiliar.setName(userInput.getText().toString());
                                            auxiliar.setIdIngredient(userInput2.getText().toString());
                                            auxiliar.setNutritionaldesc(userInput3.getText().toString());


                                            TareaAsincronaAddIngrediente tareaux = new TareaAsincronaAddIngrediente(getActivity(),auxiliar);
                                            tareaux.execute();

                                            TareaAsincronaGetIngredientes tarea = new TareaAsincronaGetIngredientes(getActivity());
                                            tarea.execute();


                                            TareaAsincronaDevuelveIngredientes tare= new TareaAsincronaDevuelveIngredientes(getActivity());
                                            tare.execute();

                                        }



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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        context = this.getContext();
        id_usuario= getArguments().getString(Constants.message0);


        return inflater.inflate(R.layout.fragment_ingrediente, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
