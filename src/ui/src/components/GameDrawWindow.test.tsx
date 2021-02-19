import GameDrawWindow from "./GameDrawWindow";
import { render } from "@testing-library/react";
import GameContext from "../domain/GameContext";
import Game from "../domain/Game";

describe("test rendering", () => {
  const setup = () => {
    const gameMock = new Game("", [], -5);
    jest.spyOn(gameMock, "getWord").mockImplementation(() => "L___");
    jest.spyOn(gameMock, "wronglyGuessedCount").mockImplementation(() => 4);
    return render(
      <GameContext.Provider
        value={{ game: gameMock, updateGame: () => {}, updateError: () => {} }}
      >
        <GameDrawWindow />
      </GameContext.Provider>
    );
  };
  test("should render div with class", () => {
    const drawWindow = setup();
    expect(
      drawWindow.container.getElementsByClassName("game__drawing-canvas").length
    ).toBe(1);
  });

  test("should render the word with underscores", () => {
    const drawWindow = setup();
    expect(drawWindow.container).toHaveTextContent("L _ _ _");
  });

  test("should render the the image", () => {
    const drawWindow = setup();
    expect(drawWindow.container.getElementsByTagName("img")[0]).toHaveAttribute(
      "src",
      "https://kartuves.andriaus.lt/4.png"
    );
  });

  test("should render the the image with alt", () => {
    const drawWindow = setup();
    expect(drawWindow.container.getElementsByTagName("img")[0]).toHaveAttribute(
      "alt",
      "6 incorrect guesses left"
    );
  });
});
