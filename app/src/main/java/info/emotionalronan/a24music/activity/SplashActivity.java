package info.emotionalronan.a24music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import info.emotionalronan.a24music.R;
import info.emotionalronan.a24music.application.AppCache;
import info.emotionalronan.a24music.service.PlayService;
import info.emotionalronan.a24music.utils.ToastUtils;
import info.emotionalronan.a24music.utils.permission.PermissionReq;
import info.emotionalronan.a24music.utils.permission.PermissionResult;
import info.emotionalronan.a24music.utils.permission.Permissions;

public class SplashActivity extends BaseActivity {
    private static final String SPLASH_FILE_NAME = "splash";

    private ServiceConnection mPlayServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkService();
    }

    private void checkService() {
        if (AppCache.getPlayService() == null) {
            startService();

            //延迟  秒进去主界面
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 2500);
        } else {
            startMusicActivity();
            finish();
        }
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppCache.setPlayService(playService);
            PermissionReq.with(SplashActivity.this)
                    .permissions(Permissions.STORAGE)
                    .result(new PermissionResult() {
                        @Override
                        public void onGranted() {
                            //授权之后
                            scanMusic(playService);
                        }

                        @Override
                        public void onDenied() {
                            ToastUtils.show(getString(R.string.no_permission, Permissions.STORAGE_DESC, "扫描本地歌曲"));
                            finish();
                            playService.stop();
                        }
                    })
                    .request();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    //扫描音乐
    private void scanMusic(final PlayService playService) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                playService.updateMusicList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                startMusicActivity();
                finish();
            }
        }.execute();
    }

    private void startMusicActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LocalMusicActivity.class);
        intent.putExtras(getIntent());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
        super.onDestroy();
    }
}
