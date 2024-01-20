package com.fossaplayer;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaSessionService;

public class PlayerSvc extends MediaSessionService {
  private MediaSession mediaSession = null;

  // Create your Player and MediaSession in the onCreate lifecycle event
  @Override
  public void onCreate() {
    super.onCreate();
    ExoPlayer player = new ExoPlayer.Builder(this).build();
    mediaSession = new MediaSession.Builder(this, player).build();
  }

  // The user dismissed the app from the recent tasks
  @Override
  public void onTaskRemoved(@Nullable Intent rootIntent) {
    Player player = mediaSession.getPlayer();
    if (!player.getPlayWhenReady() || player.getMediaItemCount() == 0) {
      // Stop the service if not playing, continue playing in the background
      // otherwise.
      stopSelf();
    }
  }

  // Remember to release the player and media session in onDestroy
  @Override
  public void onDestroy() {
    mediaSession.getPlayer().release();
    mediaSession.release();
    mediaSession = null;
    super.onDestroy();
  }

  @Nullable
  @Override
  public MediaSession onGetSession(MediaSession.ControllerInfo controllerInfo) {
    return mediaSession;
  }
}
