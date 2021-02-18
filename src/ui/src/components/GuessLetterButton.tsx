import React, { useContext } from "react";
import CallbackButton from "./CallbackButton";
import { guessLetter } from "../gateway/GameGateway";
import GameContext from "../domain/GameContext";

interface propTypes {
  letter: string;
  data: string;
  disabled: boolean;
}

const GuessLetterButton = ({ letter, data, disabled }: propTypes) => {
  const gameContext = useContext(GameContext);
  return (
    <CallbackButton
      onFailure={(e) => {
        gameContext.updateError(e?.message + "");
      }}
      text={letter}
      callback={async () => {
        let game = await guessLetter(gameContext.game!.getId(), letter);
        gameContext.updateGame(game);
      }}
      data={data}
      disabled={disabled}
    />
  );
};

export default GuessLetterButton;
