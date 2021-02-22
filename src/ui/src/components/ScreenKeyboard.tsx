import React, { useContext, useEffect, useRef } from "react";
import KeyboardLayout from "../domain/KeyboardLayout";
import GuessLetterButton from "./GuessLetterButton";
import GameContext from "../domain/GameContext";

const ScreenKeyboard = () => {
  const gameContext = useContext(GameContext);
  const divRef = useRef<HTMLDivElement>(null);
  const shouldDisableButton = (letter: string): boolean => {
    const game = gameContext.game!;
    return (
      (game.getGuessedLetters().includes(letter.toUpperCase()) ?? false) ||
      game.hasFinished()
    );
  };
  const keyUpListener = async (e: KeyboardEvent) => {
    for (const letter of KeyboardLayout.layout.flat()) {
      if (e.key === letter) {
        const button = Array.from(
          divRef.current!.getElementsByTagName("button")
        ).filter((button) => button.dataset.data === letter)[0];
        await button.click();
        button.setAttribute(
          "class",
          Array.from(button.classList)
            .join(" ")
            .replace(/game__keyboard__button--pressed/g, "")
        );
      }
    }
  };
  const keyDownListener = async (e: KeyboardEvent) => {
    for (const letter of KeyboardLayout.layout.flat()) {
      if (e.key === letter) {
        const button = Array.from(
          divRef.current!.getElementsByTagName("button")
        ).filter((button) => button.dataset.data === letter)[0];
        button.setAttribute(
          "class",
          Array.from(button.classList).join(" ") +
            " game__keyboard__button--pressed"
        );
      }
    }
  };
  useEffect(() => {
    window.addEventListener("keyup", keyUpListener);
    window.addEventListener("keydown", keyDownListener);
    return () => {
      window.removeEventListener("keyup", keyUpListener);
      window.removeEventListener("keydown", keyDownListener);
    };
  }, []);
  return (
    <div className={"game__keyboard"} ref={divRef}>
      {KeyboardLayout.layout.map((row, i) => (
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
