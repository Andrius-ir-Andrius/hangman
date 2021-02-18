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
    guessLetter(gameContext.game?.getId()!, letter)
      .then((game) => {
        gameContext.updateGame(game);
      })
      .catch((e) => {
        gameContext.updateError(e.message);
      });
  };
  return (
    <button
      disabled={disabled}
      data-data={data}
      onClick={async () => {
        await handleClick();
      }}
    >
      {letter}
    </button>
  );
};

export default GuessLetterButton;
