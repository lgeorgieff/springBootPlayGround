import React, { Component } from 'react';

interface Profile {
  id: string;
  email: string;
}

interface ProfileListProps { }

interface ProfileListState {
  profiles: Array<Profile>;
  isLoading: boolean;
}

class ProfileList extends Component<ProfileListProps, ProfileListState> {
  constructor(props: ProfileListProps) {
    super(props);

    this.state = {
      profiles: [],
      isLoading: false
    };
  }

  async _componentDidMount() {
    this.setState({isLoading: true});

    const response = await fetch('http://localhost:3000/api/', {headers: {accept: 'application/json'}});
    const data = await response.json();
    this.setState({profiles: data, isLoading: false});
  }

  async componentDidMount() {
      this.setState({isLoading: true});

      const response = await fetch('http://localhost:3000/api/', {headers: {accept: 'application/json'}});
      const data = await response.json();
      this.setState({profiles: data, isLoading: false});

      const socket = new WebSocket('ws://localhost:3000/ws/profiles');
      socket.addEventListener('message', async (event: any) => {
        const profile = JSON.parse(event.data);
        this.state.profiles.push(profile);
        this.setState({profiles: this.state.profiles});
      });
    }

  render() {
    const {profiles, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
      <div>
        <h2>Profile List</h2>
        {profiles.map((profile: Profile) =>
          <div key={profile.id}>
            {profile.email}<br/>
          </div>
        )}
      </div>
    );
  }
}

export default ProfileList;
