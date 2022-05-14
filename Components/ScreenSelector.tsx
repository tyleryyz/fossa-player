import React from 'react';
import { View, Button } from 'react-native';

const ScreenSelector = () => {
    return (
        <View>
            <Button
                onPress={openLibrary}
                title="Library"
            />
            <Button
                onPress={openPlayer}
                title="Player"
            />

        </View>

    );
}

export default ScreenSelector;

