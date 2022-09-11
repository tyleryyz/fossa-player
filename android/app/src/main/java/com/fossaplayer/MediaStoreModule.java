package com.fossaplayer;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class MediaStoreModule extends ReactContextBaseJavaModule {

    MediaStoreModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "MediaStoreModule";
    }

    @ReactMethod
    public String helloworld() {
        return ("hello world");
    }
}
