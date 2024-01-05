import { NativeModules } from 'react-native';
import { Album } from '../Interfaces/Album'
import { Song } from '../Interfaces/Song';
const { MediaStoreModule } = NativeModules;

interface MediaStoreInterface {
    getAlbums() : Promise<Array<Album>>
    getSongs(albumId:String) : Promise<Array<Song>>

}

export default MediaStoreModule as MediaStoreInterface
