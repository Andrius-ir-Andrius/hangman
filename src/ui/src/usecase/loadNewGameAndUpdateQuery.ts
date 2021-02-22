import { createGame } from "../gateway/GameGateway";
import AppStateType from "../domain/AppStateType";
import loadGameFromId from "./loadGameFromId";

export default async function loadNewGameAndUpdateQuery(
  state: AppStateType
): Promise<AppStateType> {
  try {
    const gameId = await createGame();
    window.history.pushState({}, "", window.location.href + "?id=" + gameId);
    return await loadGameFromId(gameId + "", state);
  } catch (e) {
    return { ...state, error: "Failed to create game", isLoaded: true };
  }
}
