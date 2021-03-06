import _ from 'lodash';
import React, { Component } from 'react';
import {
  StyleSheet,
  View,
  Text,
  Button
} from 'react-native';

import Navigation from 'react-native-navigation';

class PushedScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickPush = this.onClickPush.bind(this);
    this.onClickPop = this.onClickPop.bind(this);
    this.onClickPopPrevious = this.onClickPopPrevious.bind(this);
    this.onClickPopToFirstPosition = this.onClickPopToFirstPosition.bind(this);
    this.onClickPopToRoot = this.onClickPopToRoot.bind(this);
  }

  render() {
    const stackPosition = this.getStackPosition();
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`Pushed Screen`}</Text>
        <Text style={styles.h2}>{`Stack Position: ${stackPosition}`}</Text>
        <Button title="Push" onPress={this.onClickPush} />
        <Button title="Pop" onPress={this.onClickPop} />
        <Button title="Pop Previous" onPress={this.onClickPopPrevious} />
        <Button title="Pop To Root" onPress={this.onClickPopToRoot} />
        {stackPosition > 2 && <Button title="Pop To Stack Position 1" onPress={this.onClickPopToFirstPosition} />}
        <Text style={styles.footer}>{`this.props.id = ${this.props.id}`}</Text>
      </View>
    );
  }

  onClickPush() {
    Navigation.push(this.props.id, {
      name: 'navigation.playground.PushedScreen',
      passProps: {
        stackPosition: this.getStackPosition() + 1,
        previousScreenIds: _.concat([], this.props.previousScreenIds || [], this.props.id)
      }
    });
  }

  onClickPop() {
    Navigation.pop(this.props.id);
  }

  onClickPopPrevious() {
    Navigation.pop(_.last(this.props.previousScreenIds));
  }

  onClickPopToFirstPosition() {
    Navigation.popTo(this.props.previousScreenIds[0]);
  }

  onClickPopToRoot() {
    Navigation.popToRoot(this.props.id);
  }

  getStackPosition() {
    return this.props.stackPosition || 1;
  }
}

const styles = {
  root: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5fcff'
  },
  h1: {
    fontSize: 24,
    textAlign: 'center',
    margin: 10
  },
  h2: {
    fontSize: 12,
    textAlign: 'center',
    margin: 10
  },
  footer: {
    fontSize: 10,
    color: '#888',
    marginTop: 10
  }
};

export default PushedScreen;
