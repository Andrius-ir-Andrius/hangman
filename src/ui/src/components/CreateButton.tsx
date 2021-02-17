import React, {useContext} from "react";
import CallbackButton from "./CallbackButton";
import {createGame, loadGame} from "../gateway/GameGateway";
import GameContext from "../domain/GameContext";


const CreateButton = () => {
    const gameContext = useContext(GameContext);
    return (
        <CallbackButton
            onFailure={(e) => alert(e?.message)}
            text={"Create Game"} callback={
            async () => {
                const gameId = await createGame()
                const game = await loadGame(gameId)
                gameContext.updateGame(game)
            }
        }/>
    )
}

export default CreateButton;