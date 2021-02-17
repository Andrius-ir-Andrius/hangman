import Game from "./Game";
import React from "react";

type contextType = {
    game: Game | null
    updateGame: (game: (Game | undefined)) => void
}


export const defaultGameContext: contextType = {
    game: null,
    updateGame: () => {}
}

const GameContext = React.createContext<contextType>(defaultGameContext)
export default GameContext