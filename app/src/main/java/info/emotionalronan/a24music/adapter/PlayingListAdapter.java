package info.emotionalronan.a24music.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.emotionalronan.a24music.R;
import info.emotionalronan.a24music.application.AppCache;
import info.emotionalronan.a24music.model.Music;
import info.emotionalronan.a24music.service.PlayService;
import info.emotionalronan.a24music.utils.FileUtils;
import info.emotionalronan.a24music.utils.binding.Bind;
import info.emotionalronan.a24music.utils.binding.ViewBinder;

/**
 * 正在播放列表适配器
 */
public class PlayingListAdapter extends BaseAdapter {
    private OnMoreClickListener mListener;
    private int mPlayingPosition;

    @Override
    public int getCount() {
        return AppCache.getPlayingMusicList().size();
    }

    @Override
    public Object getItem(int position) {
        return AppCache.getPlayingMusicList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_playing_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == mPlayingPosition) {
            holder.vPlaying.setVisibility(View.VISIBLE);
        } else {
            holder.vPlaying.setVisibility(View.INVISIBLE);
        }
        Music music = AppCache.getPlayingMusicList().get(position);

        holder.tvTitle.setText(music.getTitle());
        String artist = FileUtils.getArtistAndAlbum(music.getArtist(), music.getAlbum());
        holder.tvArtist.setText(artist);

        holder.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMoreClick(position);
                }
            }
        });
        holder.vDivider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private boolean isShowDivider(int position) {
        return position != AppCache.getPlayingMusicList().size() - 1;
    }

    public void updatePlayingPosition(PlayService playService) {
        if (playService.getPlayingMusic() != null && playService.getPlayingMusic().getType() == Music.Type.LOCAL) {
            mPlayingPosition = playService.getPlayingPosition();
        } else {
            mPlayingPosition = -1;
        }
    }

    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }

    private static class ViewHolder {
        @Bind(R.id.v_playing)
        View vPlaying;

        @Bind(R.id.tv_title)
        TextView tvTitle;

        @Bind(R.id.tv_artist)
        TextView tvArtist;

        @Bind(R.id.iv_clear)
        ImageView ivClear;

        @Bind(R.id.v_divider)
        View vDivider;

        public ViewHolder(View view) {
            ViewBinder.bind(this, view);
        }
    }
}
