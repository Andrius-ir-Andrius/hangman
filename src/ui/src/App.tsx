import React, { useEffect, useState } from "react";
import Game from "./domain/Game";
import GameContext from "./domain/GameContext";
import { createGame, loadGame } from "./gateway/GameGateway";
import ScreenKeyboard from "./components/ScreenKeyboard";
import GameDrawWindow from "./components/GameDrawWindow";
import "./App.scss";

type stateType = {
  game: Game | null;
  isLoaded: boolean;
  error: string | null;
};

function App() {
  const [state, setState] = useState<stateType>({
    game: null,
    isLoaded: false,
    error: null,
  });

  const loadNewGameAndUpdateQuery = async () => {
    const gameId = await createGame().catch((e) => {
      setState({ ...state, error: "Failed to create game", isLoaded: true });
    });
    if (gameId === undefined) return;
    const game = await loadGame(gameId).catch((e) => {
      setState({ ...state, error: "Failed to load game", isLoaded: true });
    });
    if (game === undefined) return;
    setState({ ...state, isLoaded: true, game: game ?? null });
    window.history.pushState({}, "", window.location.href + "?id=" + gameId);
  };

  const loadGameFromQuery = async (id: string) => {
    const game = await loadGame(+id).catch((e) => {
      setState({ ...state, error: "Failed to load game", isLoaded: true });
    });
    if (game === undefined) {
      return;
    }
    setState({ ...state, isLoaded: true, game: game ?? null });
  };

  useEffect(() => {
    (async () => {
      const urlParams = new URLSearchParams(window.location.search);
      const id = urlParams.get("id");
      if (id === null) await loadNewGameAndUpdateQuery();
      else await loadGameFromQuery(id);
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
