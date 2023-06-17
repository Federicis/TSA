import { useState, useEffect, useContext } from "react";
import "./MyBots.css";

import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import AuthContext from "../../../context/AuthProvider";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";

import { useNavigate, Link } from "react-router-dom";

export default function MyBotsPage() {
  const [bots, setBots] = useState([]);
  const [currIndex, setCurrIndex] = useState(0);

  const { auth } = useContext(AuthContext);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    const getBots = async () => {
      try {
        const response = await axiosPrivate.get(
          `http://localhost:8080/api/v1/bots`
        );
        setBots(response.data);
      } catch (err) {
        console.log(err);
      }
    };
    getBots();
  }, []);

  const handleDelete = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosPrivate.delete(
        `http://localhost:8080/api/v1/bots/${bots[currIndex].id}`
      );
    } catch (err) {
      console.log(err);
    }
    bots.splice(currIndex, 1);
    setCurrIndex(0);
  };

  return (
    <main className="my-bots">
      <h1 className="my-bots-heading">My Bots</h1>
      {bots.length ? (
        <div className="bots-carousel">
          <FontAwesomeIcon
            onClick={() =>
              setCurrIndex((currIndex + bots.length - 1) % bots.length)
            }
            className="prev-bot-button"
            icon={faAngleLeft}
          />
          {[1, 2, 3, 4, 5].map((num) => (
            <div className={`bot-card bot-card-${num}`} key={num}>
              {num == 3 ? (
                <>
                  <h2 className="bot-name">{bots[currIndex].name}</h2>

                  <div className="bot-routines">
                    {bots[currIndex].routines.length ? (
                      <ul className="bot-routines-list">
                        {bots[currIndex].routines.map((routine) => (
                          <li className="routine-list-item">
                            <p className="routine-name">{routine.name}</p>
                          </li>
                        ))}
                      </ul>
                    ) : (
                      <p className="no-routines">No routines.</p>
                    )}
                  </div>
                  <button className="edit-bot-button">
                    <Link to="/edit-bot" state={{ id: bots[currIndex].id }}>
                      Edit
                    </Link>
                  </button>

                  <button className="delete-bot-button" onClick={handleDelete}>
                    Delete
                  </button>
                </>
              ) : (
                <></>
              )}
            </div>
          ))}
          <FontAwesomeIcon
            onClick={() => setCurrIndex((currIndex + 1) % bots.length)}
            className="next-bot-button"
            icon={faAngleRight}
          />
        </div>
      ) : (
        <p>You have no bots.</p>
      )}
    </main>
  );
}
