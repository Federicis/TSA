import useAxiosPrivate from "../hooks/useAxiosPrivate";
import AuthContext from "../context/AuthProvider";
import { useNavigate } from "react-router-dom";
import { useEffect, useContext } from "react";

export default function Logout() {
	const navigate = useNavigate();
	const { setAuth } = useContext(AuthContext);

	const axiosPrivate = useAxiosPrivate();

	useEffect(() => {
		const logout = async () => {
			try {
				await axiosPrivate.post("http://localhost:8080/api/v1/auth/logout");
				setAuth({});
				navigate("/");
			} catch (err) {
				console.log(err);
			}
		};
		logout();
	}, []);
}
