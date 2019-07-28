package com.example.android.appculinaria;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncTaskCulinaria  extends AsyncTask<URL,Void, ArrayList<Receita>>{

    public InterfaceAsyncTask recuperarDados=null;

    @Override
    protected ArrayList<Receita> doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        ArrayList<Receita> receitas=null;
        try {
            String stringJson=NetworkUtils.getResponseFromHttpUrl(searchUrl);
            receitas=extractFromJson(stringJson);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return receitas;
    }

    @Override
    protected void onPostExecute(ArrayList<Receita> receitas) {
        recuperarDados.executado(receitas);
        super.onPostExecute(receitas);
    }

    private ArrayList<Receita>extractFromJson (String stringJson){
        ArrayList<Receita> receitas = new ArrayList<Receita>();
        try {

            JSONArray array_receitas = new JSONArray(stringJson);
            for(int i=0;i<array_receitas.length();i++){
                JSONObject object_receitas = array_receitas.getJSONObject(i);
                Receita receita = new Receita(object_receitas.getString("name"),object_receitas.getInt("servings"));

                JSONArray array_ingredientes = object_receitas.getJSONArray("ingredients");
                for(int j=0;j<array_ingredientes.length();j++){
                    JSONObject object_ingredientes = array_ingredientes.getJSONObject(j);
                    receita.setIngredients(object_ingredientes.getInt("quantity"),object_ingredientes.getString("measure"),
                            object_ingredientes.getString("ingredient"));

                }
                JSONArray array_passos = object_receitas.getJSONArray("steps");
                for(int j=0;j<array_passos.length();j++){
                    JSONObject object_passos = array_passos.getJSONObject(j);
                    receita.setSteps(object_passos.getString("shortDescription"),object_passos.getString("description"),
                            object_passos.getString("videoURL"));

                }
                receitas.add(receita);

            }// fim for

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return receitas;
    }


}
