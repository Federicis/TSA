import { useState, useEffect, useContext } from "react";
import "./MyBots.css";

import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import AuthContext from "../../../context/AuthProvider";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";

import { useNavigate } from "react-router-dom";

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
				console.log(response.data);
				setBots(response.data);
			} catch (err) {
				console.log(err);
			}
		};
		getBots();
	}, []);

	return (
		<main className="my-bots">
			<h1 className="my-bots-heading">My Bots</h1>
			{bots.length ? (
				<div className="bots-carousel">
					<FontAwesomeIcon
						onClick={() => setCurrIndex((currIndex + bots.length - 1) % bots.length)}
						className="prev-bot-button"
						icon={faAngleLeft}
					/>
					{[1, 2, 3, 4, 5].map((num) => (
						<div className={`bot-card bot-card-${num}`} key={num}>
							{num == 3 ? (
								<h2 className="bot-name">
									{bots[(currIndex + num - 1) % bots.length].name}
								</h2>
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
