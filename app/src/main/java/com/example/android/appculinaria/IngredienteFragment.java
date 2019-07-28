package com.example.android.appculinaria;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;


public class IngredienteFragment extends Fragment {

    public IngredienteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingrediente, container, false);

        Parcelable [] parcelables=getArguments().getParcelableArray("ingredients");
        String texto="";
        for(int i=0;i<parcelables.length;i++){
            Ingredients ingredients = Parcels.unwrap(parcelables[i]);
            texto = texto + ingredients.getQuantity() +" "+ ingredients.getMeasure() +" of "+ ingredients.getIngredient() +"\n";
        }

        TextView textView = rootView.findViewById(R.id.text_view_ingrediente);
        textView.setText(texto);
        return rootView;
    }

}
