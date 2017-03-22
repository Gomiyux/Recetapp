package com.example.carlo.recetapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaAddReceta;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaDevuelveIngredientes;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetRecetas;
import com.example.carlo.recetapp.recipesendpoint.model.CardInfo;
import com.example.carlo.recetapp.recipesendpoint.model.Ingredient;
import com.example.carlo.recetapp.recipesendpoint.model.Ingrrecipe;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
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
public class recetas_fragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static int numerito=0;
    private boolean Mutex;

    private OnFragmentInteractionListener mListener;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);
    ArrayList<String> ids_ingr = new ArrayList<>();
    ArrayList<Ingredient> listaingredientes = new ArrayList<>();
    int seleccionado=0;

    private Button button;
    private String id_usuario;
    private int i=0;
    private EditText result;
    private Recipe auxiliar;
    private int numeroIng;
    private ArrayList<Ingredient> ingredientes_anadir = new ArrayList<>();
    private Activity backup = getActivity();



    @Override
    public void onResume() {

        super.onResume();
        TareaAsincronaGetRecetas tarea = new TareaAsincronaGetRecetas(getActivity(), id_usuario);
        tarea.execute();
        //lista();

    }


    Context context;
    private ArrayList<Recipe> recetas = new ArrayList<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    //HashMap<String, List<String>> expandableListDetail;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();





    public recetas_fragment() {
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
    public static recetas_fragment newInstance(String param1, String param2) {

        recetas_fragment fragment = new recetas_fragment();
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

        TareaAsincronaGetRecetas tarea = new TareaAsincronaGetRecetas(this.getActivity(), id_usuario);
        tarea.execute();

        auxiliar =new Recipe();
        TareaAsincronaDevuelveIngredientes tareita = new TareaAsincronaDevuelveIngredientes(getActivity());
        tareita.execute();


        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);

        //lista();

        final List<Ingrrecipe> añadir_ingredientes_receta = new ArrayList<>();




            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    MainActivity auxacti = (MainActivity) getActivity();
                    ids_ingr = auxacti.id_ing;
                    numerito=0;
                    listaingredientes = auxacti.ingredientes;
                    if (ids_ingr.size() > 0) {
                        arg0.startAnimation(buttonClick);
                        arg0.startAnimation(buttonClick);
                        // get prompt_rellena_receta.xmlena_receta.xml view
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_rellena_receta, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set prompt_rellena_receta.xmlena_receta.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);
                        final EditText userInput2 = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput2);
                        final EditText userInput3 = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput3);
                        final EditText pasos = (EditText) promptsView
                                .findViewById(R.id.pasos_in);
                        final EditText ids = (EditText) promptsView
                                .findViewById(R.id.idreceta_in);
                        final EditText numero_ingr = (EditText) promptsView.findViewById(R.id.numero_ingredientes);
                        //numeroIng=numero_ingr.getText();


                        userInput.requestFocus();




                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Siguiente",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                if ((userInput.getText().toString().length() != 0) && (userInput2.getText().toString().length() != 0) && (userInput3.getText().toString().length() != 0) && (pasos.getText().toString().length() != 0) && (ids.getText().toString().length() != 0)) {

                                                    numeroIng = 0;
                                                    if (!numero_ingr.getText().toString().equals("")) {
                                                        numeroIng = Integer.parseInt(numero_ingr.getText().toString());
                                                    }


                                                    auxiliar.setIdRecipe(ids.getText().toString());
                                                    auxiliar.setSteps(pasos.getText().toString());
                                                    CardInfo card = new CardInfo();
                                                    card.setName(userInput.getText().toString());
                                                    card.setSummary(userInput3.getText().toString());
                                                    card.setType(userInput2.getText().toString());
                                                    auxiliar.setCard(card);


                                                    for (i = 0; i < numeroIng; i++) {



                                                        LayoutInflater li = LayoutInflater.from(context);
                                                        View promptsView = li.inflate(R.layout.prompt_rellena_receta_ingredientes, null);

                                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                context);

                                                        // set prompt_rellena_receta.xmlena_receta.xml to alertdialog builder
                                                        alertDialogBuilder.setView(promptsView);

                                                        final TextView banner_ing = (TextView) promptsView.findViewById(R.id.ingrediente_banner);

                                                        String st_aux = "\n\tSelecciona el ingrediente nº" + (numeroIng - i) + " :";

                                                        banner_ing.setText(st_aux);

                                                        final EditText userInput = (EditText) promptsView
                                                                .findViewById(R.id.id_ingrediente_receta);
                                                        final EditText userInput2 = (EditText) promptsView
                                                                .findViewById(R.id.ingrediente_cantidad);
                                                        final EditText userInput3 = (EditText) promptsView
                                                                .findViewById(R.id.ingrediente_unidad);
                                                        ArrayList<String> expanda = new ArrayList<>();

                                                        userInput.requestFocus();

                                                        alertDialogBuilder
                                                                .setCancelable(false)
                                                                .setPositiveButton("Siguiente",
                                                                        new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {

                                                                                numerito = numerito + 1;
                                                                                Ingrrecipe auxiliar2 = new Ingrrecipe();
                                                                                String id_ing = listaingredientes.get(seleccionado).getIdIngredient();
                                                                                auxiliar2.setIdIng(id_ing);
                                                                                auxiliar2.setMeasurementu(userInput3.getText().toString());
                                                                                auxiliar2.setQuantity(userInput2.getText().toString());
                                                                                auxiliar2.setIdIngrrecipe(userInput.getText().toString());
                                                                                añadir_ingredientes_receta.add(auxiliar2);


                                                                                if (numerito == numeroIng) {
                                                                                    auxiliar.setLing(añadir_ingredientes_receta);
                                                                                    TareaAsincronaAddReceta tareaux = new TareaAsincronaAddReceta(getActivity(), id_usuario, auxiliar);
                                                                                    tareaux.execute();

                                                                                    TareaAsincronaGetRecetas tarea = new TareaAsincronaGetRecetas(getActivity(), id_usuario);
                                                                                    tarea.execute();
                                                                                }


                                                                            }
                                                                        });

                                                        // create alert dialog
                                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                                        Spinner dropdown = (Spinner) promptsView.findViewById(R.id.lista_ingredientes);
                                                        if (dropdown != null) {
                                                            for (int j = 0; j < ids_ingr.size(); j++) {
                                                                expanda.add(ids_ingr.get(j)+ " - " + listaingredientes.get(j).getName());
                                                            }
                                                            String[] sacar = Arrays.copyOf(expanda.toArray(), expanda.size(), String[].class);
                                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, sacar);
                                                            dropdown.setAdapter(adapter);
                                                            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> parent, View view,
                                                                                           int position, long id) {

                                                                    seleccionado = position;
                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> parent) {
                                                                    // TODO Auto-generated method stub
                                                                }
                                                            });

                                                        }


                                                        alertDialog.show();


                                                    }


                                                }


                                            }
                                        })
                                .setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();


                        // show it
                        alertDialog.show();



                    }
                }
            });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        context = container.getContext();
        id_usuario= getArguments().getString(Constants.message0);


        return inflater.inflate(R.layout.fragment_recetas_fragment, container, false);
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


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {


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
