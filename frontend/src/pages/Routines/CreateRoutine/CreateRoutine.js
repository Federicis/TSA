import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { faRepeat } from "@fortawesome/free-solid-svg-icons";
import { useLocation, useNavigate } from "react-router-dom";

import "./CreateRoutine.css";

export default function CreateRoutine() {
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const [repeatable, setRepeatable] = useState(false);
  const [interval, setInterval] = useState("");
  const [createTask, setCreateTask] = useState(false);
  const axios = useAxiosPrivate();
  const location = useLocation();
  const botId = location.state.botId;
  const taskTypes = ["Comment", "Find"];
  const [taskType, setTaskType] = useState("Comment");
  const [comment, setComment] = useState("");
  const [keyword, setKeyword] = useState("");
  const [subreddit, setSubreddit] = useState("");
  const navigate = useNavigate();

  const intervalOptions = [
    { key: "DAILY", value: "daily" },
    { key: "HOURLY", value: "hourly" },
    { key: "ASAP", value: "as often as possible" },
  ];

  // empty the error when inputs change
  useEffect(() => {
    setError("");
  }, [name, repeatable, interval]);

  useEffect(() => {
    if (!repeatable) setInterval(null);
  }, [repeatable]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const requestBody = {
        id: null,
        name,
        repeatable,
        interval,
        botId,
      };
      console.log("bot id: " + botId);
      console.log(requestBody);
      const response = await axios.post(
        `http://localhost:8080/api/v1/routines`,
        requestBody
      );
      console.log(response.data);
      console.log(response.status);
      navigate("/show-routines", { state: { botId } });
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
    <main className="create-routine-main">
      <h2>
        <FontAwesomeIcon icon={faRepeat} /> Create a Routine
      </h2>
      <form className="create-routine-form">
        <p className="error-div">{error}</p>
        <div className="input-container">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div className="input-container">
          <label htmlFor="select-repeatable">
            Do you want this routine to repeat the tasks?
          </label>
          <select
            onChange={(e) =>
              e.target.value === "true"
                ? setRepeatable(true)
                : setRepeatable(false)
            }
            name="select-repeatable"
          >
            <option value="false">No</option>
            <option value="true">Yes</option>
          </select>
        </div>
        {repeatable && (
          <div className="input-container">
            <label htmlFor="select-interval">
              How often should the routine repeat?
            </label>
            <select
              onChange={(e) => setInterval(e.target.value)}
              name="select-interval"
            >
              {intervalOptions.map((option) => (
                <option key={option.key} value={option.key}>
                  {option.value}
                </option>
              ))}
            </select>
          </div>
        )}
        <input
          type="submit"
          className="submit-input"
          value="Create"
          onClick={handleSubmit}
        />
      </form>
    </main>
  );
}
