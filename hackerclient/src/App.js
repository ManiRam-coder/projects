import React from 'react'; 
import ReactDOM from 'react-dom'; 
  
// This is a functional component 
/*function Welcome(props) {
    return <h1>Hello, {props.name}</h1>;
  }*/

  const Welcome = (props) =>{
    return(<h1>Hello, {props.name}</h1>);
  };
  
  const element = <Welcome name="Sara1" />;
  ReactDOM.render(element, document.getElementById('root'));
  


export default Welcome;