import React, { useEffect, useState } from "react";
import GameContext from "./domain/GameContext";
import ScreenKeyboard from "./components/ScreenKeyboard";
import GameDrawWindow from "./components/GameDrawWindow";
import "./App.scss";
import AppStateType, { defaultAppState } from "./domain/AppStateType";
import loadNewGameAndUpdateQuery from "./usecase/loadNewGameAndUpdateQuery";
import loadGameFromId from "./usecase/loadGameFromId";
import { getIdFromQuery } from "./util";

function App() {
  const [state, setState] = useState<AppStateType>(defaultAppState);

  useEffect(() => {
    (async () => {
      const id = getIdFromQuery();
      if (id === null) setState(await loadNewGameAndUpdateQuery(state));
      else setState(await loadGameFromId(id, state));
    })();
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

  return (
    <GameContext.Provider
      value={{
        updateGame: (game) => {
          setState({
            ...state,
            isLoaded: true,
            game: game ?? null,
            error: null,
          });
        },
        updateError: (error) => {
          setState({ ...state, error: error ?? null });
        },
        game: state.game,
      }}
    >
      <div className={"game"}>
        {!state.isLoaded ? (
          <div className={"game__error"}>Loading</div>
        ) : state.game === null ? (
          <div className={"game__error"}>{state.error}</div>
        ) : (
          <>
            <GameDrawWindow />
            <div className={"game__error"}>{state.error ?? ""}</div>
            <ScreenKeyboard />
            {state.game.hasFinished() ? <a href={"/"}>Create game</a> : ""}
          </>
        )}
      </div>
    </GameContext.Provider>
  );
}

export default App;
