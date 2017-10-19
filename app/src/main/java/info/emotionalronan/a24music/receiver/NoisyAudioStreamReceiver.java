package info.emotionalronan.a24music.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import info.emotionalronan.a24music.constants.Actions;
import info.emotionalronan.a24music.service.PlayService;

/**
 * 来电/耳机拔出时暂停播放
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PlayService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
