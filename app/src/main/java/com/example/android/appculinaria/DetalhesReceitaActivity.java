package com.example.android.appculinaria;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetalhesReceitaActivity extends AppCompatActivity implements InterfaceFragment {

    private final int LARGURA_MINIMO=600;
    private static final int INGREDIENTE = 0;
    private static final int ETAPAS = 1;
    private static final int POSICAO_INDEFINIDO = -1;

    private StepReceitaFragment mStepReceitaFragment = new StepReceitaFragment();;
    private StepEtapaFragment mStepEtapaFragment;
    private VideoFragment mVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_receita);
        setTitle(getString(R.string.receita)+" "+getIntent().getStringExtra("nomeReceita"));

        mStepReceitaFragment.mNavegar=this;
        Bundle bundle = new Bundle();
        String nome = getIntent().getStringExtra("nomeReceita");
        bundle.putString("nomeReceita",nome);
        bundle.putInt("controle",ETAPAS);

        Parcelable[] parcelables =getIntent().getParcelableArrayExtra("steps");
        bundle.putParcelableArray("steps",parcelables);

        mStepReceitaFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_list, mStepReceitaFragment)
                .commit();

        Configuration config = getResources().getConfiguration();
        int largura=config.smallestScreenWidthDp;

        if(largura>=LARGURA_MINIMO){
            mStepEtapaFragment = new StepEtapaFragment();

            Bundle bundle2 = new Bundle();
            bundle2.putInt("posicao", POSICAO_INDEFINIDO);
            bundle2.putParcelableArray("steps",parcelables);

            mStepEtapaFragment.setArguments(bundle);
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction()
                    .add(R.id.fragment_etapa, mStepEtapaFragment)
                    .commit();


            mVideoFragment = new VideoFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putInt("posicao",POSICAO_INDEFINIDO);

            bundle3.putParcelableArray("steps",parcelables);

            mVideoFragment.setArguments(bundle3);
            FragmentManager fragmentManager3 = getSupportFragmentManager();
            fragmentManager3.beginTransaction()
                    .add(R.id.id_fragment_video, mVideoFragment)
                    .commit();
        }

    }

    public void listarIngredientes(View v){
        Intent intent = new Intent(this,DetalhesEtapaActivity.class);
        String nome=getIntent().getStringExtra("nomeReceita");
        intent.putExtra("nomeReceita", nome);

        Parcelable[] parcelables =getIntent().getParcelableArrayExtra("ingredients");
        intent.putExtra("ingredients",parcelables);

        intent.putExtra("controle", INGREDIENTE);

        startActivity(intent);
    }

    @Override
    public void interfaceNavegar(int posicao){
        mStepEtapaFragment.navegar(posicao);
        mVideoFragment.navegar(posicao);
    }
}
