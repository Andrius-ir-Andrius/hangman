import Game from "./Game";

type AppStateType = {
  game: Game | null;
  isLoaded: boolean;
  error: string | null;
};

export const defaultAppState = {
  game: null,
  isLoaded: false,
  error: null,
};

export default AppStateType;
