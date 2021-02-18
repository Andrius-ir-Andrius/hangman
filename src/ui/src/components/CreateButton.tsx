import React from "react";
import CallbackButton from "./CallbackButton";

const CreateButton = () => {
  return (
    <CallbackButton
      text={"Create new Game"}
      callback={async () => {
        window.location.assign(window.location.origin);
      }}
    />
  );
};

export default CreateButton;
