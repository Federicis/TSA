import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import "./ShowRoutines.css";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

export default function ShowRoutines() {
  const location = useLocation();
  const botId = location.state.botId;
  const axios = useAxiosPrivate();
  const [error, setError] = useState("");
  const [routines, setRoutines] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const getRoutines = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/v1/routines/${botId}`
        );
        console.log(response.data);
        console.log(response.status);
        setRoutines(response.data);
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
    getRoutines();
  }, []);

  return (
    <main className="ShowRoutines-main">
      <h2>Routines</h2>
      {error && <p className="error">{error}</p>}
      {routines && (
        <div className="routines">
          {routines.length ? (
            <ul className="routines-list">
              {routines.map((routine) => (
                <li className="routine-list-item">
                  <p className="routine-name">{routine.name}</p>
                </li>
              ))}
            </ul>
          ) : (
            <p className="no-routines">No routines.</p>
          )}
        </div>
      )}
      <FontAwesomeIcon
        icon={faPlus}
        className="add-routine-icon"
        onClick={() => navigate("/create-routine", { state: { botId: botId } })}
      />
    </main>
  );
}
