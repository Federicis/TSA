import { useState, useEffect } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import "./CreateBot.css";
import {
  CreateBotForm1,
  CreateBotForm2,
  CreateBotForm3,
} from "../../../components/CreateBotForms";
import { useNavigate } from "react-router-dom";

export default function CreateBot() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [redditUsername, setRedditUsername] = useState("");
  const [redditPassword, setRedditPassword] = useState("");
  const [redditClientId, setRedditClientId] = useState("");
  const [redditClientSecret, setRedditClientSecret] = useState("");
  const [error, setError] = useState("");
  const axios = useAxiosPrivate();
  const [step, setStep] = useState(0); // 0 = name, 1 = reddit, 2 = client, 3 = finish
  const navigate = useNavigate();

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
      navigate("/my-bots");
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

  const props = {
    name,
    setName,
    description,
    setDescription,
    error,
    setError,
    redditUsername,
    setRedditUsername,
    redditPassword,
    setRedditPassword,
    redditClientId,
    setRedditClientId,
    redditClientSecret,
    setRedditClientSecret,
    step,
    setStep,
    handleSubmit,
  };

  return (
    <main className="create-bot-main">
      <h2 className="create-bot-heading">👋Create a new bot</h2>
      {/* <div className="create-bot-container"> */}
      {step == 0 ? (
        <CreateBotForm1 {...props} />
      ) : step == 1 ? (
        <CreateBotForm2 {...props} />
      ) : (
        <CreateBotForm3 {...props} />
      )}
      {/* </div> */}
    </main>
  );
}
