import React, {useState} from "react";

type propTypes = {
    text : string
    callback : () => any
    onFailure? : ((e? : DOMException) => any)
    shouldDisable? : boolean
}

const CallbackButton = ({text, callback, onFailure, shouldDisable}: propTypes) => {
    const [isDisabled, setDisabled] = useState<boolean>(false)

    const handleClick = async () => {
        if(shouldDisable === undefined || shouldDisable)
            setDisabled(true);
        try {
            await callback()
        }catch (e){
            if(onFailure === undefined)
                setDisabled(false);
            else
                await onFailure(e)
        }
    }

    return (
        <button onClick={async () => {
            await handleClick()
        }} disabled={isDisabled}
        >{text}</button>
    )
}

export default CallbackButton;