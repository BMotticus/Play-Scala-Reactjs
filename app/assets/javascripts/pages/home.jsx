"use strict";


class Page extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <App>
        <h1>{this.props.message}</h1>

      </App>
    );
  }
}