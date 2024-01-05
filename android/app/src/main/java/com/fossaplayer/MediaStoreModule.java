package com.fossaplayer;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.List;


public class MediaStoreModule extends ReactContextBaseJavaModule {

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
  public void play(Uri uri, Promise promise) {

    // start service here by creating an intent

    Intent intent = new Intent();
    intent.setAction("com.example.action.PLAY");

    intent.putExtra("playbackFile", uri.toString());
    PlaybackService playbackService = new PlaybackService();

  }
}
