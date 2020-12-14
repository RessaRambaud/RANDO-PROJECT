package com.RandoDam.rando.ActivityClass.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.RandoDam.rando.R;

/**
 * Fragment pour débug
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.navigation.Controle_Fichiers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Controle_Fichiers extends Fragment {

    TextView tv_data1, tv_data2, tv_data3, tv_data4, tv_data5, tv_data6, tv_data7, tv_data8, tv_data9, tv_data10;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Controle_Fichiers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Debug.
     */
    // TODO: Rename and change types and number of parameters
    public static Controle_Fichiers newInstance(String param1, String param2) {
       Controle_Fichiers fragment = new Controle_Fichiers();
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

    /**
     * Créée la vue debug
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controle_fichiers, container, false);
    }
}