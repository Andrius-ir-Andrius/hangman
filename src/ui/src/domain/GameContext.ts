import Game from "./Game";
import React from "react";

type contextType = {
    game: Game | null
    updateGame: (game: (Game | undefined)) => void
    updateError: (error: (string | undefined)) => void
}


export const defaultGameContext: contextType = {
    game: null,
    updateGame: () => {},
    updateError : () => {}
}

const GameContext = React.createContext<contextType>(defaultGameContext)
export default GameContext