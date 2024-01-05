import { NativeModules } from 'react-native';
import { Album } from '../Interfaces/Album'
import { Song } from '../Interfaces/Song';
const { MediaStoreModule } = NativeModules;

interface MediaStoreInterface {
    getAlbums() : Promise<Array<Album>>
    getSongs(albumId: string) : Promise<Array<Song>>
    play(uri: string): void

}

export default MediaStoreModule as MediaStoreInterface
