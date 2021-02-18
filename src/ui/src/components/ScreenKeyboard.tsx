import React, { useContext, useEffect, useRef } from "react";
import Keyboard from "../domain/Keyboard";
import GuessLetterButton from "./GuessLetterButton";
import GameContext from "../domain/GameContext";

interface propTypes {
  id: number;
}

const ScreenKeyboard = ({ id }: propTypes) => {
  const gameContext = useContext(GameContext);
  const divRef = useRef<HTMLDivElement>(null);
  const shouldDisableButton = (letter: string): boolean => {
    const game = gameContext.game!;
    return (
      (game.getGuessedLetters().includes(letter.toUpperCase()) ?? false) ||
      game.hasFinished()
    );
  };
  const buttonListenerFunc = async (e: KeyboardEvent) => {
    for (const letter of Keyboard.layout.flat()) {
      if (e.key === letter) {
        await Array.from(divRef.current!.getElementsByTagName("button"))
          .filter((button) => button.dataset.data === letter)[0]
          .click();
      }
    }
  };
  useEffect(() => {
    window.addEventListener("keypress", buttonListenerFunc);
    return () => {
      window.removeEventListener("keypress", buttonListenerFunc);
    };
  }, []);
  return (
    <div className={"game__keyboard"} ref={divRef}>
      {Keyboard.layout.map((row, i) => (
        <div key={"keyboard" + i} className={"game__keyboard-row"}>
          {row.map((letter) => (
            <GuessLetterButton
              letter={letter}
              key={letter}
              data={letter}
              disabled={shouldDisableButton(letter)}
            />
          ))}
        </div>
      ))}
    </div>
  );
};

export default ScreenKeyboard;
