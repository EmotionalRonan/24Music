package info.emotionalronan.a24music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.emotionalronan.a24music.R;

/**
 * 最近播放列表
 */
public class PlayingQueueFragment extends BaseFragment  {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing_queue, container, false);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }


}
