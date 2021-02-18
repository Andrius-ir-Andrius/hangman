import React, { useContext } from "react";
import { guessLetter } from "../gateway/GameGateway";
import GameContext from "../domain/GameContext";
import { Button } from "@material-ui/core";

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
    <Button
      size={"large"}
      className={"game__keyboard__button"}
      variant="contained"
      color={"primary"}
      disabled={disabled}
      data-data={data}
      onClick={async () => {
        await handleClick();
      }}
    >
      {letter}
    </Button>
  );
};

export default GuessLetterButton;
