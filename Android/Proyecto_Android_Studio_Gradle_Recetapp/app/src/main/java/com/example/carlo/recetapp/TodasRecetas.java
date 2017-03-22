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

import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaAddReceta;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetRecetas;
import com.example.carlo.recetapp.TareasAsincronas.TareaAsincronaGetTodasRecetas;
import com.example.carlo.recetapp.recipesendpoint.model.CardInfo;
import com.example.carlo.recetapp.recipesendpoint.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlo on 03/09/2016.
 */
public class TodasRecetas extends Fragment{


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
        private ArrayList<Recipe> recetas = new ArrayList<>();
        ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        List<String> expandableListTitle;
        //HashMap<String, List<String>> expandableListDetail;
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();



        public TodasRecetas() {
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
        public static TodasRecetas newInstance(String param1, String param2) {

            TodasRecetas fragment = new TodasRecetas();
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

            TareaAsincronaGetTodasRecetas tarea = new TareaAsincronaGetTodasRecetas(this.getActivity());
            tarea.execute();

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment




            context = container.getContext();
            id_usuario= getArguments().getString(Constants.message0);


            return inflater.inflate(R.layout.fragment_todas_recetas, container, false);
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



