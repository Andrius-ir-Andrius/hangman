import loadGameFromId from "./loadGameFromId";
import { defaultAppState } from "../domain/AppStateType";
import * as Gateway from "../gateway/GameGateway";
import Game from "../domain/Game";

const loadMock = (load: boolean) => {
  const mockLoadGameGood: typeof Gateway.loadGame = (id): Promise<Game> => {
    return Promise.resolve(new Game("____", [], id));
  };
  const mockLoadGameBad: typeof Gateway.loadGame = (id): Promise<Game> => {
    return Promise.reject();
  };
  const mockLoadGame = jest
    .spyOn(Gateway, "loadGame")
    .mockImplementation(load ? mockLoadGameGood : mockLoadGameBad);

  return {
    mockLoadGameGood,
    mockLoadGameBad,
    mockLoadGame,
  };
};

describe("test load from query", () => {
  test("should load game", async () => {
    loadMock(true);
    expect((await loadGameFromId("100", defaultAppState)).game?.getId()).toBe(
      100
    );
  });
  test("should not load game", async () => {
    loadMock(false);
    expect((await loadGameFromId("100", defaultAppState)).error).toBe(
      "Failed to load game"
    );
  });
});

export { loadMock };
