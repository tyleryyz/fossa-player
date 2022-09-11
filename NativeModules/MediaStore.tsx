import ReactNative from 'react-native';
const { MediaStoreModule } = ReactNative.NativeModules;

interface MediaStore {
    helloworld() : string
}

export default MediaStoreModule as MediaStore
