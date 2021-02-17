import React, {useContext} from "react";
import CallbackButton from "./CallbackButton";
import {guessLetter} from "../gateway/GameGateway";
import GameContext from "../domain/GameContext";

interface propTypes {
    letter: string
}

const GuessLetterButton = ({letter}: propTypes) => {
    const gameContext = useContext(GameContext);
    return (
        <CallbackButton
            onFailure={(e) => alert(e?.message)}
            text={letter} callback={
            async () => {
                let game = await guessLetter(gameContext.game!.getId(), letter)
                gameContext.updateGame(game)
            }
        }/>)
}

export default GuessLetterButton;