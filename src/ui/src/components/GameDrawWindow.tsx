import React, { useContext } from "react";
import GameContext from "../domain/GameContext";

const GameDrawWindow = () => {
  const gameContext = useContext(GameContext);
  return (
    <div className={"game__drawing-canvas"}>
      <img
        src={`https://kartuves.andriaus.lt/${gameContext.game?.wronglyGuessedCount()}.png`}
        alt={`${
          10 - gameContext.game?.wronglyGuessedCount()!
        } incorrect guesses left`}
      />
      <br />
      {gameContext.game?.getWord().split("").join(" ")}
    </div>
  );
};

export default GameDrawWindow;
