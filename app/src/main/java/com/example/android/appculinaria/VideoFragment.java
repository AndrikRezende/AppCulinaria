package com.example.android.appculinaria;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

public class VideoFragment extends Fragment {

    private static final int POSICAO_INDEFINIDO = -1;
    private static final String VIDEO_POSICAO="videoPosicao";
    private static final String VIDEO_PLAY="videoPlay";

    private RecyclerView mRecyclerList;
    private VideoAdapter mAdapter;

    public VideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        mRecyclerList=rootView.findViewById(R.id.recycler_video);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        String nome=getArguments().getString("nomeReceita");

        Parcelable[]parcelables=getArguments().getParcelableArray("steps");
        String []array = new String[parcelables.length];
        for(int i=0;i<parcelables.length;i++){
            Steps steps = Parcels.unwrap(parcelables[i]);
            array[i]=steps.getVideoURL();
        }

        mAdapter = new VideoAdapter(getActivity(), array,nome);
        int posicao=getArguments().getInt("posicao",POSICAO_INDEFINIDO);
        if(posicao!=POSICAO_INDEFINIDO) {
            mAdapter.setPosicao(posicao);
            if(savedInstanceState!=null) {
                mAdapter.setExoPlayerPosition(savedInstanceState.getLong(VIDEO_POSICAO));
                mAdapter.setPlayOuPause(savedInstanceState.getBoolean(VIDEO_PLAY));
            }
            mRecyclerList.setAdapter(mAdapter);
        }

        return rootView;
    }

    public void navegar(int posicao){
        mAdapter.releasePlayer();
        mAdapter.setPosicao(posicao);
        mRecyclerList.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(VIDEO_POSICAO,mAdapter.getExoPlayerPosition());
        outState.putBoolean(VIDEO_PLAY,mAdapter.getPlayOuPause());
    }

    @Override
    public void onResume() {
        if(mAdapter.getReleaseChamado()) {
            int posicao=getArguments().getInt("posicao",POSICAO_INDEFINIDO);
            mAdapter.setPosicao(posicao);
            mAdapter.inicializarPlayer();
            mAdapter.setReleaseChamadoFalse();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(Util.SDK_INT<=23)
            mAdapter.releasePlayer();
        super.onPause();
    }

    @Override
    public void onStop() {
        if(Util.SDK_INT>23)
            mAdapter.releasePlayer();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mAdapter.releasePlayer();
        super.onDestroy();
    }

}
