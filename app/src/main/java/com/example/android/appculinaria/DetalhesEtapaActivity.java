package com.example.android.appculinaria;

import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class DetalhesEtapaActivity extends AppCompatActivity {

    private static final int INGREDIENTE = 0;

    private int mPosicao=0;
    private int mTamanho;
    private StepEtapaFragment mStepEtapaFragment=null;
    private VideoFragment mVideoFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_etapa);
        setTitle(getString(R.string.receita)+" "+getIntent().getStringExtra("nomeReceita"));

        int controle=getIntent().getIntExtra("controle",0);
        mPosicao = getIntent().getIntExtra("posicao", 0);

        if(controle==INGREDIENTE) {

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_navegacao);
            linearLayout.setVisibility(View.GONE);
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.id_fragment_video);
            frameLayout.setVisibility(View.GONE);

            IngredienteFragment ingredienteFragment = new IngredienteFragment();
            Bundle bundle = new Bundle();

            Parcelable[] parcelables =getIntent().getParcelableArrayExtra("ingredients");
            bundle.putParcelableArray("ingredients",parcelables);

            ingredienteFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_etapa, ingredienteFragment)
                    .commit();

        }

        else {

            Configuration config = getResources().getConfiguration();
            int orientacao=config.orientation;
            Parcelable[] parcelables2 = getIntent().getParcelableArrayExtra("steps");
            if(orientacao==Configuration.ORIENTATION_LANDSCAPE){
                if(getSupportActionBar()!=null)
                    getSupportActionBar().hide();
            }
            else{
                if(getSupportActionBar()!=null)
                    getSupportActionBar().show();
                mStepEtapaFragment = new StepEtapaFragment();
                Bundle bundle = new Bundle();

                bundle.putParcelableArray("steps", parcelables2);

                mTamanho = parcelables2.length;
                bundle.putInt("posicao", mPosicao);

                mStepEtapaFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_etapa, mStepEtapaFragment)
                        .commit();
            }

            mVideoFragment = new VideoFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("posicao",mPosicao);
            bundle2.putParcelableArray("steps",parcelables2);
            mVideoFragment.setArguments(bundle2);

            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction()
                    .add(R.id.id_fragment_video, mVideoFragment)
                    .commit();
        }
    }

    public void botaoAnterior(View v){
        mPosicao--;
        if(mPosicao<=0)
            mPosicao=0;
        mStepEtapaFragment.navegar(mPosicao);
        mVideoFragment.navegar(mPosicao);
    }

    public void botaoProximo(View v){
        mPosicao++;
        if(mPosicao>=mTamanho)
            mPosicao=mTamanho-1;
        mStepEtapaFragment.navegar(mPosicao);
        mVideoFragment.navegar(mPosicao);
    }

}
