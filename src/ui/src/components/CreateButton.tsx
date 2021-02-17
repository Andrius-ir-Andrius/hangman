import React from "react";
import Game from "../domain/Game";

interface propTypes {

}

const CreateButton = ({}: propTypes) => {
    return (
        <button onClick={async () => {
            await Game.createGame()
        }
        }>Create Game</button>
    )
}

export default CreateButton;