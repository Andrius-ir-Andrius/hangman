import React, {useContext, useEffect, useState} from 'react';
import CreateButton from "./components/CreateButton";
import Game from "./domain/Game";
import GameContext from './domain/GameContext';
import GuessLetterButton from "./components/GuessLetterButton";


type stateType = {
    game: Game | null
    isLoaded: boolean
}

function App() {
    const [state, setState] = useState<stateType>({game: null, isLoaded: false});
    const gameContext = useContext(GameContext);
    useEffect(() => {
        (async () => {
            if (gameContext.game === null) {
                setState({...state, isLoaded: true})
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
                        <CreateButton/>
                    </> :
                    <>
                        {JSON.stringify(state.game)}
                        <GuessLetterButton letter={'a'} />
                    </>
            }</>
        </GameContext.Provider>
    );
}

export default App;
