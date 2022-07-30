import React from 'react'
import { ScrollView, Text } from 'react-native'

interface propsType {}
interface stateType {
    lDir: string
}

class FolderSelect extends React.Component<propsType, stateType> {
    constructor(props: propsType, state: stateType) {
        super(props)
    
        this.state = {
            lDir: ''
        }
    }

    render() {
        return(
            <ScrollView>
                <Text>
                    Folder Select Component
                </Text>
            </ScrollView>
        )
    }
}

export default FolderSelect