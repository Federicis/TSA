import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";

export function CreateBotForm1(props) {
  return (
    <form
      onSubmit={async (e) => {
        e.preventDefault();
        props.setStep(1);
      }}
      className="create-bot-form"
    >
      <p className="error-div">{props.error}</p>
      <div className="input-container">
        <label htmlFor="name">Name</label>
        <input
          type="text"
          id="name"
          value={props.name}
          onChange={(e) => props.setName(e.target.value)}
        />
      </div>
      <div className="input-container">
        <label htmlFor="description">Description</label>
        <textarea
          type="text"
          id="description"
          value={props.description}
          onChange={(e) => props.setDescription(e.target.value)}
        />
      </div>
      <input type="submit" className="submit-input" value="Next" />
    </form>
  );
}

export function CreateBotForm2(props) {
  const [passwordText, setPasswordText] = useState("");
  const [passwordVisible, setPasswordVisible] = useState(false);

  return (
    <form
      onSubmit={async (e) => {
        e.preventDefault();
        props.setStep(2);
      }}
      className="create-bot-form"
    >
      <p className="error-div">{props.error}</p>
      <div className="input-container">
        <label htmlFor="redditName">Bot's Reddit username</label>
        <input
          type="text"
          id="redditName"
          autoComplete="off"
          value={props.redditUsername}
          onChange={(e) => props.setRedditUsername(e.target.value)}
        />
      </div>
      <div className="input-container">
        <label htmlFor="password">Bot's Reddit password</label>
        <div className="password-input-div">
          <input
            type={!passwordVisible ? "password" : "text"}
            name="password"
            className="password-input"
            autoComplete="new-password"
            onChange={(e) => props.setRedditPassword(e.target.value)}
            required
          />
          <FontAwesomeIcon
            onClick={() => setPasswordVisible(!passwordVisible)}
            className="eye-icon"
            icon={passwordVisible ? faEyeSlash : faEye}
          />
        </div>
      </div>
      <input type="submit" className="submit-input" value="Next" />
    </form>
  );
}

export function CreateBotForm3(props) {
  const [passwordText, setPasswordText] = useState("");
  const [passwordVisible, setPasswordVisible] = useState(false);

  return (
    <form onSubmit={props.handleSubmit} className="create-bot-form">
      <p className="error-div">{props.error}</p>
      <div className="input-container">
        <label htmlFor="redditClientId">Client Id</label>
        <input
          type="text"
          id="redditClientId"
          autoComplete="off"
          value={props.redditClientId}
          onChange={(e) => props.setRedditClientId(e.target.value)}
        />
      </div>
      <div className="input-container">
        <label htmlFor="password">Client secret</label>
        <div className="password-input-div">
          <input
            type={!passwordVisible ? "password" : "text"}
            name="password"
            className="password-input"
            autoComplete="new-password"
            onChange={(e) => props.setRedditClientSecret(e.target.value)}
            required
          />
          <FontAwesomeIcon
            onClick={() => setPasswordVisible(!passwordVisible)}
            className="eye-icon"
            icon={passwordVisible ? faEyeSlash : faEye}
          />
        </div>
      </div>
      <input type="submit" className="submit-input" value="Finish" />
    </form>
  );
}
