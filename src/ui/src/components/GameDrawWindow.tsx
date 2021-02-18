import React, { useContext } from "react";
import GameContext from "../domain/GameContext";

const GameDrawWindow = () => {
  const gameContext = useContext(GameContext);
  return (
    <>
      <img
        src={`https://kartuves.andriaus.lt/${gameContext.game?.wronglyGuessedCount()}.png`}
      />
      <br />
      {gameContext.game?.getWord()}
    </>
  );
};

export default GameDrawWindow;
