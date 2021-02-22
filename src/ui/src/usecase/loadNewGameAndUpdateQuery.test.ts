import { defaultAppState } from "../domain/AppStateType";
import * as Gateway from "../gateway/GameGateway";
import loadNewGameAndUpdateQuery from "./loadNewGameAndUpdateQuery";
import { loadMock } from "./loadGameFromId.test";

describe("test load from query", () => {
  const setup = (create: boolean, load: boolean) => {
    const mockStartGameGood: typeof Gateway.createGame = (): Promise<number> => {
      return Promise.resolve(100);
    };
    const mockStartGameBad: typeof Gateway.createGame = (): Promise<number> => {
      return Promise.reject();
    };
    const mockStartGame = jest
      .spyOn(Gateway, "createGame")
      .mockImplementation(create ? mockStartGameGood : mockStartGameBad);

    return {
      ...loadMock(load),
      mockStartGameGood,
      mockStartGameBad,
      mockStartGame,
    };
  };
  test("should load new game", async () => {
    setup(true, true);
    expect(
      (await loadNewGameAndUpdateQuery(defaultAppState)).game?.getId()
    ).toBe(100);
  });
  test("should load new game and update query", async () => {
    setup(true, true);
    const pushStateMock = jest
      .spyOn(window.history, "pushState")
      .mockImplementation((data, title, location) => {});
    await loadNewGameAndUpdateQuery(defaultAppState);
    expect(pushStateMock).toHaveBeenCalled();
  });
  test("should not load new game", async () => {
    setup(false, true);
    expect((await loadNewGameAndUpdateQuery(defaultAppState)).error).toBe(
      "Failed to create game"
    );
  });
  test("should not load new game even after creation", async () => {
    setup(false, false);
    expect((await loadNewGameAndUpdateQuery(defaultAppState)).error).toBe(
      "Failed to create game"
    );
  });
});
