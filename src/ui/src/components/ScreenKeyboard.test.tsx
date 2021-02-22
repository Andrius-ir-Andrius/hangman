import ScreenKeyboard from "./ScreenKeyboard";
import Game from "../domain/Game";
import { fireEvent, render } from "@testing-library/react";
import GameContext from "../domain/GameContext";

describe("test renders", () => {
  const setup = () => {
    const gameMock = new Game("", [], -5);
    return render(
      <GameContext.Provider
        value={{
          game: gameMock,
          updateGame: () => {},
          updateError: () => {},
        }}
      >
        <ScreenKeyboard />
      </GameContext.Provider>
    );
  };
  test("should render the main div", () => {
    const element = setup();
    expect(
      element.container.getElementsByClassName("game__keyboard").length
    ).toBe(1);
  });

  test("should render the keyboard rows", () => {
    const element = setup();
    expect(
      element.container.getElementsByClassName("game__keyboard-row").length
    ).toBe(3);
  });

  test("should render the keyboard buttons", () => {
    const element = setup();
    expect(
      element.container.getElementsByClassName("game__keyboard__button").length
    ).toBe(26);
  });
});

describe("test disabled letters", () => {
  const setup = (hasFinished: boolean) => {
    const gameMock = new Game("", [], -5);
    jest
      .spyOn(gameMock, "getGuessedLetters")
      .mockImplementation(() => ["L", "A"]);
    jest.spyOn(gameMock, "hasFinished").mockImplementation(() => hasFinished);
    return render(
      <GameContext.Provider
        value={{
          game: gameMock,
          updateGame: () => {},
          updateError: () => {},
        }}
      >
        <ScreenKeyboard />
      </GameContext.Provider>
    );
  };
  test("should disable two buttons only", () => {
    const element = setup(false);
    expect(
      element.container.getElementsByClassName(
        "game__keyboard__button--disabled"
      ).length
    ).toBe(2);
  });
  test("should disable all buttons", () => {
    const element = setup(true);
    expect(
      element.container.getElementsByClassName(
        "game__keyboard__button--disabled"
      ).length
    ).toBe(26);
  });
});

describe("test button disable after guess", () => {
  let fakeGame = new Game("", [], 0);
  const setup = () => {
    const element = render(
      <GameContext.Provider
        value={{
          game: fakeGame,
          updateGame: (game: Game | undefined) => {
            fakeGame = game!;
          },
          updateError: () => {},
        }}
      >
        <ScreenKeyboard />
      </GameContext.Provider>
    );
    return { element };
  };
  test("should disable Button after clicking on it", async () => {
    const { element } = setup();
    const buttonR = Array.from(
      element.container.getElementsByTagName("button")
    ).filter((e) => e.dataset.data?.toLowerCase() === "r")[0];
    await fireEvent.click(buttonR);
    expect(buttonR).toHaveAttribute(
      "class",
      "game__keyboard__button game__keyboard__button--disabled"
    );
  });
  test("should have Button css change on down", () => {
    const { element } = setup();
    const buttonR = Array.from(
      element.container.getElementsByTagName("button")
    ).filter((e) => e.dataset.data?.toLowerCase() === "r")[0];
    window.dispatchEvent(new KeyboardEvent("keydown", { key: "r" }));
    expect(
      buttonR.classList.contains("game__keyboard__button--pressed")
    ).toBeTruthy();
  });
  test("should disable Button after keyboard press", async () => {
    const { element } = setup();
    const buttonR = Array.from(
      element.container.getElementsByTagName("button")
    ).filter((e) => e.dataset.data?.toLowerCase() === "w")[0];
    window.dispatchEvent(new KeyboardEvent("keyup", { key: "w" }));
    expect(buttonR).toHaveAttribute(
      "class",
      "game__keyboard__button game__keyboard__button--disabled"
    );
  });
});
