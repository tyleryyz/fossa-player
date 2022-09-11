import React from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

import FolderSelect from './Components/FolderSelect';
import MediaStore from './NativeModules/MediaStore'

import ReactNative from 'react-native';
const { MediaStoreModule } = ReactNative.NativeModules;



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

    let result = await MediaStoreModule.helloworld()

    console.log(result)
    return result
  }

  componentDidMount() {
    
    // this.getDirectory().then((result) => {
    //   if (result === null ) this.setState({lDir: 'Empty'})
    //   else this.setState({lDir: result})
    //   this.setState({loading: false})
    // })
  }

  render() {

    let home = this.getDirectory()

    // if (this.state.lDir === 'Empty') home = <FolderSelect />
    // else home = <Text>{this.state.lDir}</Text>

    return(
    <SafeAreaView>
      <StatusBar />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic">
        <View>
          <Text></Text>
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
