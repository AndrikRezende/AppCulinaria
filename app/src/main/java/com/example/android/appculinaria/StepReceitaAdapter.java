package com.example.android.appculinaria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StepReceitaAdapter extends RecyclerView.Adapter<StepReceitaAdapter.StepReceitaViewHolder>{

    final private StepReceitaAdapter.ListItemClickListener mOnClickListener;
    private Context mContext;
    private String [] mArrayStepReceita;
    private TextView mAnterior;

    public interface ListItemClickListener {
        void onListItemClick(String []listStepReceita,int position);
    }

    public StepReceitaAdapter(Context context, StepReceitaAdapter.ListItemClickListener listener, String[] array){
        mOnClickListener=listener;
        mContext=context;
        mArrayStepReceita=array;
    }

    @Override
    public StepReceitaAdapter.StepReceitaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_step_receita,viewGroup, false);
        return new StepReceitaAdapter.StepReceitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepReceitaAdapter.StepReceitaViewHolder holder, int position) {
        if(position>=0 && position<mArrayStepReceita.length) {
            String steps = mArrayStepReceita[position];
            holder.textView.setText(steps);
        }

    }

    @Override
    public int getItemCount() {
        return mArrayStepReceita.length;
    }

    public class StepReceitaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public StepReceitaViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.shortDescription_receita);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mAnterior!=null)
                mAnterior.setBackgroundColor(view.getResources().getColor(R.color.colorCinza));
            textView.setBackgroundColor(view.getResources().getColor(R.color.colorPrimaryDark));
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(mArrayStepReceita,position);
            mAnterior=textView;
        }
    }

}
