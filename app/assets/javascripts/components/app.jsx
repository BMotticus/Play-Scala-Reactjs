"use strict";

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return ( 
      <Screen>
      
        <NavBar>
          
        </NavBar>
        <div className="bm-content">
          {this.props.children}
        </div>
      </Screen>
    );
  }
}

class NavBar extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="bm-nav-bar">
        {this.props.children}
      </div>
    );
  }
}

class NavTab extends React.Component {
  render() {
    return ( 
        <a href={this.props.href || "#"} className={"bm-nav-tab " + (this.props.selected ? " bm-nav-tab-selected " : "")}>
          {this.props.children}
        </a>
    );
  }
}
NavTab.propTypes = {
  selected: React.PropTypes.bool
};

class Screen extends React.Component {
  constructor(props){
    super(props);
  }
  
  
  render () {
    
    return (
      <div className="bm-container">
        <div className="bm-screen">
          {this.props.children}
        </div>
      </div>
    );
  }
}