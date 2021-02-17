import React from "react";
import Game from "../domain/Game";
import CallbackButton from "./CallbackButton";


const CreateButton = () => {
   return <CallbackButton
       onFailure={(e) => alert(e?.message)}
       text={"Create Game"} callback={
       async () => {
           const gameId = await Game.createGame()
           localStorage.setItem('id', gameId+'')
           window.location.reload()
       }
   } />
}

export default CreateButton;