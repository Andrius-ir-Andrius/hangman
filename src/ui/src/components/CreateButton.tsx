import React from "react";
import CallbackButton from "./CallbackButton";
import {createGame} from "../gateway/GameGateway";


const CreateButton = () => {
    return <CallbackButton
        onFailure={(e) => alert(e?.message)}
        text={"Create Game"} callback={
        async () => {
            const gameId = await createGame()
            localStorage.setItem('id', gameId + '')
            window.location.reload()
        }
    }/>
}

export default CreateButton;