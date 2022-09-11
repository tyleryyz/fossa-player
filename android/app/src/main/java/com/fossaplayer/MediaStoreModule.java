package com.fossaplayer;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

public class MediaStoreModule extends ReactContextBaseJavaModule {

    MediaStoreModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "MediaStoreModule";
    }

    @ReactMethod
    public void getArtists(Promise promise) {
        String hello = "oh hai mark";
        promise.resolve(hello);
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
