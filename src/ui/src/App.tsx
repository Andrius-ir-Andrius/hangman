import React, {useEffect, useState} from 'react';
import CreateButton from "./components/CreateButton";
import Game from "./domain/Game";
import {loadGame} from "./gateway/GameGateway";
import GuessLetterButton from "./components/GuessLetterButton";


type stateType = {
    game: Game | null
    isLoaded: boolean
}

function App() {
    const [state, setState] = useState<stateType>({game: null, isLoaded: false});
    useEffect(() => {
        (async () => {
            if (localStorage.getItem('id') === null){
                setState({...state, isLoaded: true})
                return
            }
            const game = await loadGame(+localStorage.getItem('id')!)
            setState({...state, isLoaded: true, game})
        })()


    }, [])
    return (
        <>
            {!state.isLoaded ? 'Loading' :
                state.game === null ?
                    <>
                        <CreateButton/>
                    </> :
                    <>
                        <CreateButton/>
                        {JSON.stringify(state.game)}
                    </>
            }
        </>
    );
}

export default App;
