import React, {useContext, useEffect, useState} from 'react';
import CreateButton from "./components/CreateButton";
import Game from "./domain/Game";
import GameContext from './domain/GameContext';
import GuessLetterButton from "./components/GuessLetterButton";
import {createGame, loadGame} from "./gateway/GameGateway";


type stateType = {
    game: Game | null
    isLoaded: boolean
    error: string | null
}

function App() {
    const [state, setState] = useState<stateType>({game: null, isLoaded: false, error: null});
    const gameContext = useContext(GameContext);
    useEffect(() => {
        (async () => {
            if (gameContext.game === null) {
                const gameId = await createGame().catch((e) => {
                    setState({...state, error: 'Failed to create game', isLoaded: true})
                })
                if(gameId === undefined)
                    return
                const game = await loadGame(gameId).catch((e) => {
                    setState({...state, error: 'Failed to load game', isLoaded: true})
                })
                if(game === undefined)
                    return
                setState({...state, isLoaded: true, game : game??null})
                return
            }
            const game = gameContext.game
            setState({...state, isLoaded: true, game})
        })()


    }, [])// eslint-disable-line react-hooks/exhaustive-deps

    return (
        <GameContext.Provider value={
            {
                updateGame: (game => {
                    setState({...state, isLoaded: true, game: game ?? null})
                }),
                game: state.game
            }
        }>
            <>{!state.isLoaded ? 'Loading' :
                state.game === null ?
                    <>
                        {state.error}
                    </> :
                    <>
                        {JSON.stringify(state.game)}
                        <GuessLetterButton letter={'a'}/>
                    </>
            }</>
        </GameContext.Provider>
    );
}

export default App;
