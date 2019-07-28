package com.example.android.appculinaria;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    private Context mContext;
    private String [] mArrayVideo;
    private int mPosicao;
    private SimpleExoPlayer mExoPlayer;
    private String mAgente;
    private long mExoplayerPosition;
    private boolean mPlayOuPause;
    private int mCurrentWindow;
    private boolean mReleaseChamado;
    private VideoAdapter.VideoViewHolder mHolder;

    public VideoAdapter(Context context, String[] array, String agente){
        mContext=context;
        mArrayVideo=array;
        mPosicao=0;
        mAgente=agente;
        mExoplayerPosition=0;
        mCurrentWindow=0;
        mReleaseChamado=false;
        mPlayOuPause=true;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_video,viewGroup, false);
        return new VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, int position) {
        mHolder=holder;
        if(mPosicao>=0 && mPosicao<mArrayVideo.length) {
            if (mExoPlayer == null) {
                inicializarPlayer();
            }
        }

    }

    public void releasePlayer() {
        if(mExoPlayer!=null) {
            mCurrentWindow=mExoPlayer.getCurrentWindowIndex();
            mExoplayerPosition=mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
        mReleaseChamado=true;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setPosicao(int posicao){
        mPosicao=posicao;
    }

    public long getExoPlayerPosition(){
        return mExoPlayer.getCurrentPosition();
    }

    public void setExoPlayerPosition(long position){
        mExoplayerPosition=position;
    }

    public boolean getPlayOuPause(){
        return mExoPlayer.getPlayWhenReady();
    }

    public void setPlayOuPause(boolean playOuPause){
        mPlayOuPause=playOuPause;
    }

    public boolean getReleaseChamado(){
        return mReleaseChamado;
    }

    public void setReleaseChamadoFalse(){
        mReleaseChamado=false;
    }

    public void inicializarPlayer(){
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        mHolder.exoPlayerView.setPlayer(mExoPlayer);
        String userAgent = Util.getUserAgent(mContext, mAgente);
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mArrayVideo[mPosicao]), new DefaultDataSourceFactory(
                mContext, userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(mPlayOuPause);
        mExoPlayer.seekTo(mCurrentWindow,mExoplayerPosition);
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder  {

        SimpleExoPlayerView exoPlayerView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            exoPlayerView=itemView.findViewById(R.id.player_view);

        }


    }

}
