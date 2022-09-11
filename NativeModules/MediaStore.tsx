import { NativeModules } from 'react-native';
const { MediaStoreModule } = NativeModules;

interface MediaStoreInterface {
    helloworld() : Promise<string>
}

export default MediaStoreModule as MediaStoreInterface
