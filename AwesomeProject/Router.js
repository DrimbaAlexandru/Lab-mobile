import React from 'react';
import { StackNavigator } from 'react-navigation';
import LogIn from './components/LogIn/LogIn';
import Elems from './components/Elems/Elems';
import ObjectsChart from './components/Chart/Chart';
import Management from './components/Management/Management';

export const Router = StackNavigator({
  LogIn: { screen: LogIn },
  Home: { screen: Elems },
  Chart: { screen: ObjectsChart },
  Management: { screen: Management },
});