import React, { useContext } from "react";
import { guessLetter } from "../gateway/GameGateway";
import GameContext from "../domain/GameContext";

interface propTypes {
  letter: string;
  data: string;
  disabled: boolean;
}

const GuessLetterButton = ({ letter, data, disabled }: propTypes) => {
  const gameContext = useContext(GameContext);
  const handleClick = async () => {
    try {
      const game = await guessLetter(gameContext.game?.getId()!, letter);
      gameContext.updateGame(game);
    } catch (e) {
      gameContext.updateError(e ? e.message : "Unexpected Error");
    }
  };
  return (
    <button
      className={
        "game__keyboard__button" +
        (disabled ? " game__keyboard__button--disabled" : "")
      }
      color={"primary"}
      disabled={disabled}
      data-data={data}
      onClick={handleClick}
    >
      {letter}
    </button>
  );
};

export default GuessLetterButton;
