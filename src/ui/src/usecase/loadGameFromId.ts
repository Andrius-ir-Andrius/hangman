import { loadGame } from "../gateway/GameGateway";
import AppStateType from "../domain/AppStateType";

export default async function loadGameFromId(
  id: string,
  state: AppStateType
): Promise<AppStateType> {
  try {
    const game = await loadGame(+id);
    return { ...state, isLoaded: true, game: game ?? null };
  } catch (e) {
    return { ...state, error: "Failed to load game", isLoaded: true };
  }
}
