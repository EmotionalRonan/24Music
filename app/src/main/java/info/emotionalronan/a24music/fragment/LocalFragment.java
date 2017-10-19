package info.emotionalronan.a24music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.emotionalronan.a24music.R;
import info.emotionalronan.a24music.adapter.FragmentAdapter;
import info.emotionalronan.a24music.utils.binding.Bind;

/**
 * 本地音乐
 */
public class LocalFragment extends BaseFragment  {
    private static final int REQUEST_WRITE_SETTINGS = 1;
    @Bind(R.id.lv_local)
    private ListView lvLocal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    @Override
    protected void init() {

        SimpleAdapter mAdapter =new SimpleAdapter(getContext(), getData(), R.layout.view_holder_local,new String[]{"locl_img","local_txt"},new int[]{R.id.iv_local, R.id.local_txt});

        lvLocal.setAdapter(mAdapter);
        lvLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "点击了"+position, Toast.LENGTH_SHORT).show();

                FragmentAdapter adapter = new FragmentAdapter(getFragmentManager());



                // 创建 Fragment 并为其添加一个参数，用来指定应显示的文章
                LocalMusicFragment localMusicFragment = new LocalMusicFragment();

             /*   Bundle args = new Bundle();
                //传递参数
                args.putInt(localMusicFragment.ARG_POSITION, position);
                localMusicFragment.setArguments(args);

                */

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // 将 fragment_container View 中的内容替换为此 Fragment ，
                // 然后将该事务添加到返回堆栈，以便用户可以向后导航
//                transaction.replace( , localMusicFragment);

                transaction.addToBackStack(null);

                // 执行事务
                transaction.commit();
            }
        });

    }

    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list =new ArrayList<>();

        Map<String, Object> map =new HashMap<>();
        map.put("locl_img", R.drawable.ic_local_music);
        map.put("local_txt","本地音乐");

        Map<String, Object> map1 =new HashMap<>();
        map1.put("locl_img", R.drawable.ic_local_playing_queue);
        map1.put("local_txt","最近播放");

        Map<String, Object> map2 =new HashMap<>();
        map2.put("locl_img", R.drawable.ic_local_collect);
        map2.put("local_txt","收藏列表");

        Map<String, Object> map3 =new HashMap<>();
        map3.put("locl_img", R.drawable.ic_local_download);
        map3.put("local_txt","下载列表");

        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);

        return list;
    }

    @Override
    protected void setListener() {

    }


}
