import App from "./App";
import { act, render } from "@testing-library/react";
import ReactDOM from "react-dom";
import Game from "./domain/Game";
import * as Gateway from "./gateway/GameGateway";
import * as Util from "./util";

describe("test rendering", () => {
  afterEach(() => {
    jest.mock("./util").clearAllMocks();
    jest.spyOn(Gateway, "createGame").mockReset();
    jest.spyOn(Gateway, "loadGame").mockReset();
  });

  test("should create prerendered game", () => {
    expect(render(<App />).getByText("Loading")).toBeInTheDocument();
  });
  test("should create empty game", async () => {
    {
      jest.spyOn(Util, "getIdFromQuery").mockImplementation(() => null);

      const mockCreateGame: typeof Gateway.createGame = (): Promise<number> => {
        return Promise.resolve(100);
      };

      jest.spyOn(Gateway, "createGame").mockImplementation(mockCreateGame);

      const mockLoadGame: typeof Gateway.loadGame = (id): Promise<Game> => {
        return Promise.resolve(new Game("____", [], id));
      };

      jest.spyOn(Gateway, "loadGame").mockImplementation(mockLoadGame);
    }
    let container = document.createElement("div");
    document.body.appendChild(container);
    await act(async () => {
      await ReactDOM.render(<App />, container);
    });
    expect(/_ _ _ _/.test(container.textContent!)).toBeTruthy();
    expect(
      /https:\/\/kartuves\.andriaus\.lt\/0\.png/.test(container.innerHTML)
    ).toBeTruthy();
  });

  test("should load game with id", async () => {
    {
      jest.spyOn(Util, "getIdFromQuery").mockImplementation(() => "100");

      const mockLoadGame: typeof Gateway.loadGame = (id): Promise<Game> => {
        return Promise.resolve(new Game("____", ["A"], id));
      };

      jest.spyOn(Gateway, "loadGame").mockImplementation(mockLoadGame);
    }
    let container = document.createElement("div");
    document.body.appendChild(container);
    await act(async () => {
      await ReactDOM.render(<App />, container);
    });
    expect(/_ _ _ _/.test(container.textContent!)).toBeTruthy();
    expect(
      /https:\/\/kartuves\.andriaus\.lt\/1\.png/.test(container.innerHTML)
    ).toBeTruthy();
  });

  test("should not load game with wrong id", async () => {
    {
      jest.spyOn(Util, "getIdFromQuery").mockImplementation(() => "100");

      const mockLoadGame: typeof Gateway.loadGame = (id): Promise<Game> => {
        return Promise.reject();
      };

      jest.spyOn(Gateway, "loadGame").mockImplementation(mockLoadGame);
    }
    let container = document.createElement("div");
    document.body.appendChild(container);
    await act(async () => {
      await ReactDOM.render(<App />, container);
    });
    expect(/_ _ _ _/.test(container.textContent!)).toBeFalsy();
    expect(
      /https:\/\/kartuves\.andriaus\.lt/.test(container.innerHTML)
    ).toBeFalsy();
    expect(container.textContent).toBe("Failed to load game");
  });

  test("should not create game when server's not accessible", async () => {
    {
      jest.spyOn(Util, "getIdFromQuery").mockImplementation(() => null);
      const mockCreateGame: typeof Gateway.createGame = (): Promise<number> => {
        return Promise.reject();
      };

      jest.spyOn(Gateway, "createGame").mockImplementation(mockCreateGame);
    }
    let container = document.createElement("div");
    document.body.appendChild(container);
    await act(async () => {
      await ReactDOM.render(<App />, container);
    });
    expect(/_ _ _ _/.test(container.textContent!)).toBeFalsy();
    expect(
      /https:\/\/kartuves\.andriaus\.lt/.test(container.innerHTML)
    ).toBeFalsy();
    expect(container.textContent).toBe("Failed to create game");
  });
});
