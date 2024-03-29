import { useContext, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import AuthContext from "../context/AuthProvider";
import { useNavigate } from "react-router-dom";

export default function Main({ prop1, prop2 }) {
	const { setAuth } = useContext(AuthContext);

	const axios = useAxiosPrivate();
	const navigate = useNavigate();

	const logout = async () => {
		setAuth({});
		try {
			axios.defaults.withCredentials = true;
			const response = await axios.post(
				`http://localhost:8080/api/v1/auth/logout`,
				{ withCredentials: true }
			);
		} catch (err) {
			console.log(err);
		}
	};

	return (
		<main className="main-component">
			<p>Hello World!</p>
		</main>
	);
}
