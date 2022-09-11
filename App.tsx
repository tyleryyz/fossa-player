import React from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

import FolderSelect from './Components/FolderSelect';
import MediaStoreModule from './NativeModules/MediaStore'

import ReactNative from 'react-native';



import {
  ProgressViewIOSComponent,
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
  lDir: string
}


class App extends React.Component<propsType, stateType> {

  constructor(props: propsType, state: stateType) {
    super(props)

    this.state = {
      loading: true,
      lDir: ''
    }
  }

  async getDirectory () {
    return (await MediaStoreModule.helloworld())
  }

  async componentDidMount() {
    this.setState({'lDir': await this.getDirectory()})
  }

  render() {

    let lDir = this.state.lDir
    console.log(lDir)

    if (lDir === null || lDir === undefined) {
      lDir = "I'm fed up with this worald!"
    }

    // if (this.state.lDir === 'Empty') lDir = <FolderSelect />
    // else lDir = <Text>{this.state.lDir}</Text>

    return(
    <SafeAreaView>
      <StatusBar />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic">
        <View>
          <Text>{lDir}</Text>
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
