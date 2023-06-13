import { useState, useEffect, useContext } from "react";
import "./MyBots.css";

import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import AuthContext from "../../../context/AuthProvider";

import { useNavigate } from "react-router-dom";

export default function MyBotsPage() {
	const [bots, setBots] = useState([]);

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
}
