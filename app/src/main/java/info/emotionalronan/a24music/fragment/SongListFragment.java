package info.emotionalronan.a24music.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import info.emotionalronan.a24music.R;
import info.emotionalronan.a24music.activity.OnlineMusicActivity;
import info.emotionalronan.a24music.adapter.SongListAdapter;
import info.emotionalronan.a24music.application.AppCache;
import info.emotionalronan.a24music.constants.Extras;
import info.emotionalronan.a24music.enums.LoadStateEnum;
import info.emotionalronan.a24music.model.Music;
import info.emotionalronan.a24music.model.SongListInfo;
import info.emotionalronan.a24music.receiver.StatusBarReceiver;
import info.emotionalronan.a24music.utils.NetworkUtils;
import info.emotionalronan.a24music.utils.ViewUtils;
import info.emotionalronan.a24music.utils.binding.Bind;

/**
 * 在线音乐
 */
public class SongListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final int PLAY_FRAGMNT = 546;
    @Bind(R.id.lv_song_list)
    private ListView lvSongList;
    @Bind(R.id.ll_loading)
    private LinearLayout llLoading;
    @Bind(R.id.ll_load_fail)
    private LinearLayout llLoadFail;
    private List<SongListInfo> mSongLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    protected void init() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            ViewUtils.changeViewState(lvSongList, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
            return;
        }

        mSongLists = AppCache.getSongListInfos();
        if (mSongLists.isEmpty()) {
            String[] titles = getResources().getStringArray(R.array.online_music_list_title);
            String[] types = getResources().getStringArray(R.array.online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                SongListInfo info = new SongListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mSongLists.add(info);
            }
        }
        SongListAdapter adapter = new SongListAdapter(mSongLists);
        lvSongList.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        lvSongList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SongListInfo songListInfo = mSongLists.get(position);

        Intent intent = new Intent(getContext(), OnlineMusicActivity.class);
        intent.putExtra(Extras.MUSIC_LIST_TYPE, songListInfo);


/*        Music playingMusic =  getPlayService().getPlayingMusic();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("playingMusic", playingMusic); // 传递一个Music对象
        intent.putExtras(mBundle);*/

        startActivityForResult(intent,PLAY_FRAGMNT);

    }

}
