package com.example.android.appculinaria;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ReceitaViewHolder>{

    final private ListItemClickListener mOnClickListener;
    private Context mContext;
    private ArrayList<Receita> mListReceita;

    public interface ListItemClickListener {
        void onListItemClick(ArrayList<Receita> listReceita,int position);
    }

    public ReceitaAdapter(Context context, ListItemClickListener listener){
        mOnClickListener=listener;
        mContext=context;
        mListReceita=null;
    }

    public void setListReceita(ArrayList<Receita> listReceita){
        mListReceita=listReceita;
    }

    @Override
    public ReceitaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_receita,viewGroup, false);
        return new ReceitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceitaViewHolder holder, int position) {
        if(mListReceita!=null) {
            String nome = mListReceita.get(position).getName();
            holder.mNomeReceita.setText(mContext.getString(R.string.receita)+"\n"+nome);
        }

    }

    @Override
    public int getItemCount() {
        if(mListReceita!=null)
            return mListReceita.size();
        else
            return -1;
    }

    public class ReceitaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNomeReceita;

        public ReceitaViewHolder(View itemView) {
            super(itemView);
            mNomeReceita=itemView.findViewById(R.id.nome_receita);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(mListReceita,position);
        }
    }

}
