package com.example.android.appculinaria;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;


public class StepReceitaFragment extends Fragment implements StepReceitaAdapter.ListItemClickListener{

    private final int LARGURA_MINIMO=600;

    private RecyclerView mRecyclerList;
    private StepReceitaAdapter mAdapter;
    public InterfaceFragment mNavegar=null;

    public StepReceitaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_step_receita, container, false);

        mRecyclerList=container.findViewById(R.id.recycler_list);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        Parcelable[]parcelables=getArguments().getParcelableArray("steps");
        String []array = new String[parcelables.length];
        for(int i=0;i<parcelables.length;i++){
            Steps steps = Parcels.unwrap(parcelables[i]);
            array[i]=steps.getShortDescription();
        }

        mAdapter=new StepReceitaAdapter(container.getContext(),this,array);
        mRecyclerList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onListItemClick(String []listStepReceita, int position) {
        Configuration config = getResources().getConfiguration();
        //int orientacao=config.orientation;
        int largura=config.smallestScreenWidthDp;
        //Configuration.ORIENTATION_LANDSCAPE
        if(largura>=LARGURA_MINIMO ) {
            mNavegar.interfaceNavegar(position);
        }
        else{
            Intent intent = new Intent(getActivity(), DetalhesEtapaActivity.class);
            String nome = getArguments().getString("nomeReceita");
            intent.putExtra("nomeReceita", nome);

            intent.putExtra("posicao", position);

            intent.putExtra("controle", getArguments().getInt("controle"));

            Parcelable[]parcelableSteps=getArguments().getParcelableArray("steps");
            intent.putExtra("steps",parcelableSteps);

            startActivity(intent);
        }

    }

}
