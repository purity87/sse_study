import './App.css'
import {useState} from "react";

function App() {
  const [messages, setMessages] = useState([]);

  const onClick = () => {
      const eventSource = new EventSource("http://localhost:8080/sse");
      console.log(
          'click'
      )

      eventSource.addEventListener('message', (event) => {
          setMessages((prevMessages) => [...prevMessages, event.data]);
          console.log(  event.data, ' event.data' )
      });

      eventSource.onerror = () => {
          eventSource.close();  //연결 끊기
      }
  };

  return (
    <>
        <button onClick={onClick}>click</button>
        <div className="App">
            <h1>Server-Sent Events</h1>
            <ul>
                {messages.map((msg, index) => (
                    <li key={index}>{msg}</li>
                ))}
            </ul>
        </div>
    </>
  );
}

export default App
