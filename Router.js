import React from 'react';
import { StackNavigator } from 'react-navigation';
import LogIn from './components/LogIn/LogIn';
import Elems from './components/Elems/Elems';

export const Router = StackNavigator({
  LogIn: { screen: LogIn },
  Home: { screen: Elems },
});