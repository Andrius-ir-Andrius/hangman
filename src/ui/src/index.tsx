import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import GameContext, { defaultGameContext } from "./domain/GameContext";

ReactDOM.render(
  <React.StrictMode>
    <GameContext.Provider value={defaultGameContext}>
      <App />
    </GameContext.Provider>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
