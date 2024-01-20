package com.fossaplayer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.media3.common.MediaItem;
import androidx.media3.session.SessionToken;
import androidx.media3.session.MediaController;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MediaStoreModule extends ReactContextBaseJavaModule {

  MediaController controller = null;

  public MediaController getController() {
    return controller;
  }

  public void setController(MediaController controller) {
    this.controller = controller;
  }

  MediaStoreModule(ReactApplicationContext context) {
    super(context);
  }

  @Override
  public String getName() {
    return "MediaStoreModule";
  }

  @ReactMethod
  public void helloWorld(Promise promise) {
    promise.resolve("Oh hai Worald!");
  }

  @ReactMethod
  public void getAlbums(Promise promise) {

    String[] projection = {
        MediaStore.Audio.Albums.ALBUM,
        MediaStore.Audio.Albums.ARTIST,
        MediaStore.Audio.Albums._ID,
    };

    Cursor albumsCursor = getReactApplicationContext().getContentResolver().query(
        MediaStore.Audio.Albums.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
        projection,
        null,
        null,
        MediaStore.Audio.Albums.ARTIST
    );

    WritableNativeArray albums = new WritableNativeArray();

    albumsCursor.moveToFirst();
    while (albumsCursor.moveToNext()) {
      WritableNativeMap album = new WritableNativeMap();
      album.putString("album", albumsCursor.getString(albumsCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
      album.putString("artist", albumsCursor.getString(albumsCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));

      String[] songsProjection = {
          MediaStore.Audio.Media.ALBUM_ID,
          MediaStore.Audio.Media.DURATION
      };

      String songsSelection = MediaStore.Audio.Media.ALBUM_ID + "=?";
      String[] songsSelectionArgs = {albumsCursor.getString(albumsCursor.getColumnIndex(MediaStore.Audio.Albums._ID))};

      Cursor songsCursor = getReactApplicationContext().getContentResolver().query(
          MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
          songsProjection,
          songsSelection,
          songsSelectionArgs,
          null
      );

      int duration = 0;
      songsCursor.moveToFirst();

      while (songsCursor.moveToNext()) {
        duration += Integer.parseInt(songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
      }
      album.putString("duration", String.valueOf(duration));
//      album.putString("albumArt", albumsCursor.getString(albumsCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
      album.putString("albumId", albumsCursor.getString(albumsCursor.getColumnIndex(MediaStore.Audio.Albums._ID)));

      albums.pushMap(album);
    }

    promise.resolve(albums);
  }

  @ReactMethod
  public void getSongs(String albumId, Promise promise) {

    WritableNativeArray songs = new WritableNativeArray();


    String[] songsProjection = {
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    };

    String songsSelection = MediaStore.Audio.Media.ALBUM_ID + "=?";

    String[] songsSelectionArgs = {String.valueOf(albumId)};

    Cursor songsCursor = getReactApplicationContext().getContentResolver().query(
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
        songsProjection,
        songsSelection,
        songsSelectionArgs,
        null
    );

    songsCursor.moveToFirst();
    while (songsCursor.moveToNext()) {
      WritableNativeMap song = new WritableNativeMap();
      song.putString("title", songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
      song.putString("duration", songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
      song.putString("data", songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DATA)));

      songs.pushMap(song);
    }
    promise.resolve(songs);

  }

  @ReactMethod
  public void loadPlayer(Promise promise) {

    System.out.printf("PLAYER INIT");
    Context that = getReactApplicationContext();
    that.startService(new Intent(that, PlayerSvc.class));
    SessionToken sessionToken =
        new SessionToken(that, new ComponentName(that, PlayerSvc.class));

    ListenableFuture<MediaController> controllerFuture =
        new MediaController.Builder(that, sessionToken).buildAsync();
    controllerFuture.addListener(() -> {
      try {
        setController(controllerFuture.get());
        promise.resolve(true);
      } catch (ExecutionException e) {
        promise.reject(e);
      } catch (InterruptedException e) {
        promise.reject(e);
      }
    }, MoreExecutors.directExecutor());
  }

  @ReactMethod
  public void play(String uri, Promise promise) {
    System.out.printf("PLAYING SONG");

    MediaController controller = getController();
    MediaItem mediaItem = MediaItem.fromUri(uri);

    controller.setMediaItem(mediaItem);
    controller.prepare();
    controller.play();
  }
}