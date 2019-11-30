import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import ProfileList from './ProfileList';

import { GoogleLogout, GoogleLogin } from 'react-google-login';

const clientId = '976336796517-tlc6t1h3fba1ejid0cgh3tivi8nfrlie.apps.googleusercontent.com';


const successHandler = (response: any) => {
  console.log(response)
}

const errorHandler = (response: any) => {
  console.error(response)
}

const logoutHandler = () => {
  console.log('logout')
}

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />

          <GoogleLogin onSuccess={successHandler} onFailure={errorHandler} clientId={clientId} />
          <GoogleLogout buttonText="Logout" onLogoutSuccess={logoutHandler} clientId={clientId} />

          <ProfileList/>
        </header>
      </div>
    );
  }
}

export default App;