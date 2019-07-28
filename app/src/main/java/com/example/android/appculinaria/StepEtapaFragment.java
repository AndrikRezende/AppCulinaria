package com.example.android.appculinaria;

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


public class StepEtapaFragment extends Fragment {

    private static final int POSICAO_INDEFINIDO = -1;

    private RecyclerView mRecyclerList;
    private StepEtapaAdapter mAdapter;

    public StepEtapaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Configuration config = getResources().getConfiguration();
        int orientacao=config.orientation;
        if(orientacao==Configuration.ORIENTATION_LANDSCAPE){
            return null;
        }
        else {
            View rootView = inflater.inflate(R.layout.fragment_step_etapa, container, false);

            mRecyclerList = container.findViewById(R.id.recycler_etapa);
            mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

            Parcelable[] parcelables = getArguments().getParcelableArray("steps");
            String[] array = new String[parcelables.length];
            for (int i = 0; i < parcelables.length; i++) {
                Steps steps = Parcels.unwrap(parcelables[i]);
                array[i] = steps.getDescription();
            }

            mAdapter = new StepEtapaAdapter(getActivity(), array);
            int posicao = getArguments().getInt("posicao", POSICAO_INDEFINIDO);
            if (posicao != POSICAO_INDEFINIDO) {
                mAdapter.setPosicao(posicao);
                mRecyclerList.setAdapter(mAdapter);
            }

            return rootView;
        }
    }

    public void navegar(int posicao){
        mAdapter.setPosicao(posicao);
        mRecyclerList.setAdapter(mAdapter);
    }

}
