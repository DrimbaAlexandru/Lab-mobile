import PropTypes from 'prop-types';
import {requireNativeComponent, ViewPropTypes, View } from 'react-native';
import Native_bridge_test from './javaModuleWrapper';

var iface = {
  name: 'GraphView',
  propTypes: {
    dataSource: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.number)),
    ...View.propTypes, // include the default view properties
  },
};

module.exports = requireNativeComponent('RCTGraphView', iface);