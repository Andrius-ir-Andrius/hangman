import React from "react";
import CallbackButton from "./CallbackButton";
import {guessLetter} from "../gateway/GameGateway";

interface propTypes {
    id: number
    letter: string
}

const GuessLetterButton = ({id, letter}: propTypes) => {
    return <CallbackButton
        onFailure={(e) => alert(e?.message)}
        text={letter} callback={
        async () => {
            await guessLetter(id, letter)
            window.location.reload()
        }
    }/>
}

export default GuessLetterButton;