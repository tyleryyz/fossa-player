import React from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

import MediaStoreModule from './NativeModules/MediaStore'
import { Album } from './Interfaces/Album'
import { Song } from './Interfaces/Song';


import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  View,
} from 'react-native';


interface propsType {}
interface stateType {
  loading: boolean,
  songs: Array<Song>,
  albums: Array<Album>,
}


class App extends React.Component<propsType, stateType> {

  constructor(props: propsType, state: stateType) {
    super(props)

    this.state = {
      loading: true,
      songs: [],
      albums: [],
    }
  }

  async getSongs (albumId:string) {
    return (await MediaStoreModule.getSongs(albumId))
  }

  async getAlbums () {
    return (await MediaStoreModule.getAlbums())
  }


  async componentDidMount() {
    this.setState({'albums': await this.getAlbums()})
    this.setState({'songs': await this.getSongs(this.state.albums[1]?.albumId)})

  }

  render() {

    let songs = this.state.songs

    let npSong = songs[0]?.title
    // if (this.state.lDir === 'Empty') lDir = <FolderSelect />
    // else lDir = <Text>{this.state.lDir}</Text>

    return(
    <SafeAreaView>
      <StatusBar />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic">
        <View>
          {/* tabs go here */}
          {/* <Queue Component (play queue)> */}
          {/* <Vueue Component (library view) */}
          <Text>{npSong}</Text>
        </View>
      </ScrollView>
    </SafeAreaView>
    )
  }
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
