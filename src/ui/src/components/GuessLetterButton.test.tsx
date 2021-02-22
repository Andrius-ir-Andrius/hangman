import GuessLetterButton from "./GuessLetterButton";
import { fireEvent, render } from "@testing-library/react";
import * as Gateway from "../gateway/GameGateway";
import Game from "../domain/Game";
import GameContext from "../domain/GameContext";

describe("test Guessing Button renders", () => {
  test("should render button", () => {
    const element = render(
      <GuessLetterButton letter={"a"} data={"a"} disabled={false} />
    );
    expect(element.getByRole("button")).toBeInTheDocument();
  });

  test("should render button with text", () => {
    const element = render(
      <GuessLetterButton letter={"petras"} data={"a"} disabled={false} />
    );
    expect(element.getByText("petras")).toBeInTheDocument();
  });

  test("should render disabled button", () => {
    const element = render(
      <GuessLetterButton letter={"a"} data={"a"} disabled={true} />
    );
    expect(element.getByRole("button")).toHaveAttribute("disabled");
    expect(element.getByRole("button")).toHaveAttribute(
      "class",
      "game__keyboard__button game__keyboard__button--disabled"
    );
  });
});

describe("test Guessing Button click", () => {
  const setup = (success: boolean) => {
    const gameMock = (jest.mock("../domain/Game") as unknown) as Game;
    const mockGuessLetterGood: typeof Gateway.guessLetter = (): Promise<Game> => {
      return Promise.resolve(gameMock);
    };
    const mockGuessLetterBad: typeof Gateway.guessLetter = (): Promise<Game> => {
      return Promise.reject();
    };

    const mockGuessLetter = jest
      .spyOn(Gateway, "guessLetter")
      .mockImplementation(success ? mockGuessLetterGood : mockGuessLetterBad);
    const updateGameMock = jest.fn().mockImplementation((game: Game) => {});
    const updateErrorMock = jest.fn().mockImplementation((e: string) => {});
    const element = render(
      <GameContext.Provider
        value={{
          updateGame: updateGameMock,
          updateError: updateErrorMock,
          game: null,
        }}
      >
        <GuessLetterButton letter={"a"} data={"a"} disabled={false} />
      </GameContext.Provider>
    ).getByRole("button");
    return { element, mockGuessLetter, updateGameMock, updateErrorMock };
  };

  test("should allow click", () => {
    const { element, mockGuessLetter } = setup(true);
    fireEvent.click(element);
    expect(mockGuessLetter).toHaveBeenCalled();
  });

  test("should update game after click", async () => {
    const { element, mockGuessLetter, updateGameMock } = setup(true);
    await fireEvent.click(element);
    expect(mockGuessLetter).toHaveBeenCalled();
    expect(updateGameMock).toHaveBeenCalled();
  });

  test("should update error after click", async () => {
    const { element, mockGuessLetter, updateErrorMock } = setup(false);
    await fireEvent.click(element);
    expect(mockGuessLetter).toHaveBeenCalled();
    expect(updateErrorMock).toHaveBeenCalled();
  });
});
