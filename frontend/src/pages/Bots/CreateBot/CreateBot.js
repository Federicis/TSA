import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import "./CreateBot.css";

export default function CreateBot() {
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [redditUsername, setRedditUsername] = useState("");
  const [redditPassword, setRedditPassword] = useState("");
  const [redditClientId, setRedditClientId] = useState("");
  const [redditClientSecret, setRedditClientSecret] = useState("");
  const [error, setError] = useState("");
  const axios = useAxiosPrivate();

  // empty the error when inputs change
  useEffect(() => {
    setError("");
  }, [
    name,
    description,
    redditUsername,
    redditPassword,
    redditClientId,
    redditClientSecret,
  ]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const requestBody = {
        id: null,
        name,
        description,
        redditUsername,
        password: redditPassword,
        clientId: redditClientId,
        clientSecret: redditClientSecret,
      };

      const response = await axios.post(
        `http://localhost:8080/api/v1/bots`,
        requestBody
      );
      console.log(response.data);
      console.log(response.status);
    } catch (err) {
      console.log(err);
      if (err.response) {
        if (err.response.status === 403) {
          setError("Forbidden.");
        }
        setError("Server error.");
      } else {
        setError("Unknown error.");
      }
    }
  };

  return (
    <main className="create-bot-main">
      <h1 className="create-bot-heading">Create a new Bot</h1>
      <form className="create-bot-form" onSubmit={handleSubmit}>
        <p className="error-div">{error}</p>
        <label htmlFor="name" className="name-label">
          Name
        </label>
        <input
          type="text"
          placeholder="Name"
          className="name-input"
          name="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <label htmlFor="description" className="description-label">
          Description
        </label>
        <textarea
          placeholder="Description"
          className="description-input"
          name="description"
          value={description}
          rows="4"
          cols="50"
          onChange={(e) => setDescription(e.target.value)}
        />
        <label htmlFor="redditUsername" className="reddit-username-label">
          Reddit Username of the Bot
        </label>
        <input
          type="text"
          placeholder="Reddit Username"
          name="redditUsername"
          className="reddit-username-input"
          value={redditUsername}
          onChange={(e) => setRedditUsername(e.target.value)}
        />
        <label htmlFor="redditPassword" className="reddit-password-label">
          Reddit Password of the Bot
        </label>
        <div className="password-input-div">
          <input
            type={!passwordVisible ? "password" : "text"}
            name="password"
            className="password-input"
            placeholder="Password"
            onChange={(e) => setRedditPassword(e.target.value)}
            required
          />
          <FontAwesomeIcon
            onClick={() => setPasswordVisible(!passwordVisible)}
            className="eye-icon"
            icon={passwordVisible ? faEyeSlash : faEye}
          />
        </div>
        <label htmlFor="redditClientId" className="reddit-client-id-label">
          Reddit Client ID
        </label>
        <input
          type="text"
          placeholder="Reddit Client ID"
          className="reddit-client-id-input"
          name="redditClientId"
          value={redditClientId}
          onChange={(e) => setRedditClientId(e.target.value)}
        />
        <label
          htmlFor="redditClientSecret"
          className="reddit-client-secret-label"
        >
          Reddit Client Secret
        </label>
        <input
          type="text"
          placeholder="Reddit Client Secret"
          className="reddit-client-secret-input"
          name="redditClientSecret"
          value={redditClientSecret}
          onChange={(e) => setRedditClientSecret(e.target.value)}
        />
        <input type="submit" className="submit-input" value="Create Bot" />
      </form>
    </main>
  );
}
