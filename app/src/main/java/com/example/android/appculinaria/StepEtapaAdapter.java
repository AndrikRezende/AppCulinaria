package com.example.android.appculinaria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StepEtapaAdapter extends RecyclerView.Adapter<StepEtapaAdapter.StepEtapaViewHolder>{

    private Context mContext;
    private String [] mArrayStepReceita;
    private int mPosicao;

    public StepEtapaAdapter(Context context, String[] array){
        mContext=context;
        mArrayStepReceita=array;
        mPosicao=0;
    }

    @Override
    public StepEtapaAdapter.StepEtapaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_step_etapa,viewGroup, false);
        return new StepEtapaAdapter.StepEtapaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepEtapaAdapter.StepEtapaViewHolder holder, int position) {
        if(mPosicao>=0 && mPosicao<mArrayStepReceita.length) {
            String steps = mArrayStepReceita[mPosicao];
            holder.textView.setText(steps);
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setPosicao(int posicao){
        mPosicao=posicao;
    }

    public class StepEtapaViewHolder extends RecyclerView.ViewHolder  {

        TextView textView;

        public StepEtapaViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_view_descricao);
        }

    }
}
