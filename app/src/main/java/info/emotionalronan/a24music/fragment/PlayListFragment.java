package info.emotionalronan.a24music.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import info.emotionalronan.a24music.R;
import info.emotionalronan.a24music.adapter.LocalMusicAdapter;
import info.emotionalronan.a24music.adapter.OnMoreClickListener;
import info.emotionalronan.a24music.adapter.PlayingListAdapter;
import info.emotionalronan.a24music.application.AppCache;
import info.emotionalronan.a24music.model.Music;
import info.emotionalronan.a24music.utils.binding.Bind;

import static info.emotionalronan.a24music.application.AppCache.getPlayService;

/**
 * 播放列表界面
 */
public class PlayListFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnMoreClickListener {

    @Bind(R.id.lv_playing_queue)
    private ListView lvplayinglMusic;
    @Bind(R.id.tv_empty)
    private TextView tvEmpty;
    @Bind(R.id.play_list_count)
    private TextView count;

    private PlayingListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      /*  //设置播放列表为本地的所有
        getPlayService().setmPlayingMusicList(AppCache.getMusicList());*/

        return inflater.inflate(R.layout.fragment_playing_queue, container, false);
    }
    @Override
    protected void init() {
        mAdapter = new PlayingListAdapter();
        mAdapter.setOnMoreClickListener(this);
        lvplayinglMusic.setAdapter(mAdapter);
        if (getPlayService().getPlayingMusic() != null && getPlayService().getPlayingMusic().getType() == Music.Type.LOCAL) {
            lvplayinglMusic.setSelection(getPlayService().getPlayingPosition());
        }
        updateView();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    @Override
    protected void setListener() {

        lvplayinglMusic.setOnItemClickListener(this);

    }

    private void updateView() {
        if (AppCache.getPlayingMusicList().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            count.setVisibility(View.GONE);

        } else {
            tvEmpty.setVisibility(View.GONE);
            count.setText("播放列表  共 "+AppCache.getPlayingMusicList().size()+" 首");
        }
        mAdapter.updatePlayingPosition(getPlayService());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getPlayService().play(position);
        onMusicListUpdate();

    }
    //歌曲列表的菜单点击
    @Override
    public void onMoreClick(final int position) {


        final Music music = AppCache.getPlayingMusicList().get(position);

        AppCache.getPlayingMusicList().remove(music);

        updateView();
   /*
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(music.getTitle());
        int itemsId = (position == getPlayService().getPlayingPosition()) ? R.array.local_music_dialog_without_delete : R.array.local_music_dialog;
        dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                switch (which) {
                    case 0:// 分享
                        shareMusic(music);
                        break;

                }
            }
        });
        dialog.show();
        */


    }

    public void onItemPlay() {
        if (isAdded()) {
            updateView();
            if (getPlayService().getPlayingMusic().getType() == Music.Type.LOCAL) {
                lvplayinglMusic.smoothScrollToPosition(getPlayService().getPlayingPosition());
            }
        }
    }

    public void onMusicListUpdate() {
        if (isAdded()) {
            updateView();
        }
    }

    /**
     * 分享音乐
     */
    private void shareMusic(Music music) {
        File file = new File(music.getPath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }


}
