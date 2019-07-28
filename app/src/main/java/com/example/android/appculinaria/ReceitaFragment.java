package com.example.android.appculinaria;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.net.URL;
import java.util.ArrayList;


public class ReceitaFragment extends Fragment implements InterfaceAsyncTask, ReceitaAdapter.ListItemClickListener{

    private final int LARGURA_MINIMO=600;

    private AsyncTaskCulinaria mAsyncTaskCulinaria=new AsyncTaskCulinaria();
    private RecyclerView mRecyclerList;
    private ReceitaAdapter mAdapter;
    private ArrayList<Receita> mReceitas;

    public ReceitaFragment() {
        mReceitas=new ArrayList<Receita>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_receita, container, false);
        mRecyclerList=container.findViewById(R.id.recycler_list);

        Configuration config = getResources().getConfiguration();
        int largura=config.smallestScreenWidthDp;

        if(largura>=LARGURA_MINIMO)
            mRecyclerList.setLayoutManager(new GridLayoutManager(getActivity(),3));
        else {
            mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        }

        mAdapter=new ReceitaAdapter(container.getContext(),this);

        URL Url=NetworkUtils.buildUrl(NetworkUtils.BASE_URL);
        mAsyncTaskCulinaria.recuperarDados=this;
        mAsyncTaskCulinaria.execute(Url);

        return rootView;
    }

    @Override
    public void executado(ArrayList<Receita> saida) {
        mReceitas=saida;
        mAdapter.setListReceita(saida);
        mRecyclerList.setAdapter(mAdapter);
    }


    @Override
    public void onListItemClick(ArrayList<Receita> listReceita, int position) {
        Intent intent = new Intent(getActivity(),DetalhesReceitaActivity.class);
        Receita receitaSelecionado = mReceitas.get(position);

        String nome=receitaSelecionado.getName();
        intent.putExtra("nomeReceita", nome);

        String texto="";
        Parcelable []parcelableIngredients=new Parcelable[receitaSelecionado.IngredientsSize()];
        for(int i=0;i<receitaSelecionado.IngredientsSize();i++){
            Ingredients ingredients = receitaSelecionado.getIngredients(i);
            parcelableIngredients[i]=Parcels.wrap(ingredients);
            String string=ingredients.getQuantity()+" "+ingredients.getMeasure()+" "+ingredients.getIngredient()+"\n";
            texto=texto+string;
        }

        intent.putExtra("ingredients",parcelableIngredients);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), ReceitaWidgetProvider.class));
        ReceitaWidgetProvider.updateReceita(getActivity(),appWidgetManager,nome,texto,appWidgetIds);

        Parcelable []parcelableSteps=new Parcelable[receitaSelecionado.StepsSize()];
        for(int i=0;i<receitaSelecionado.StepsSize();i++){
            parcelableSteps[i]=Parcels.wrap(receitaSelecionado.getSteps(i));
        }
        intent.putExtra("steps",parcelableSteps);

        startActivity(intent);
    }


}
