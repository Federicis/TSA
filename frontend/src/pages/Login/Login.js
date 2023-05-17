import { useState, useEffect, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import LoginRequest from "./LoginRequest";
import AuthContext from "../../context/AuthProvider";
import "./Login.css";

export default function Login() {
	const [passwordVisible, setPasswordVisible] = useState(false);

	const [usernameText, setUsernameText] = useState("");
	const [passwordText, setPasswordText] = useState("");

	const [error, setError] = useState("");

	const navigate = useNavigate();

	const { auth, setAuth } = useContext(AuthContext);

	// redirect to / if already logged in
	useEffect(() => {
		if (auth.accessToken) navigate("/");
	}, []);

	// empty the error when inputs change
	useEffect(() => {
		setError("");
	}, [usernameText, passwordText]);

	// submit credentials to server
	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const data = await LoginRequest(usernameText, passwordText);

			setAuth(data);

			// redirect user to index page if login was successful
			navigate("/");
		} catch (err) {
			console.log(err);
			if (err.response) {
				if (err.response.status === 401) {
					setError("Invalid credentials.");
				} else if (err.response.status === 403) {
					setError("Account not activated.");
				} else {
					setError("Server error.");
				}
			} else {
				setError("Unknown error.");
			}
		}
	};

	return (
		<main className="login-main">
			<h1 className="login-heading">Login</h1>
			<form className="login-form" onSubmit={handleSubmit}>
				<p className="error-div">{error}</p>

				<input
					type="text"
					name="username"
					className="username-input"
					placeholder="Username"
					onChange={(e) => setUsernameText(e.target.value)}
					required
				/>
				<div className="password-input-div">
					<input
						type={!passwordVisible ? "password" : "text"}
						name="password"
						className="password-input"
						placeholder="Password"
						onChange={(e) => setPasswordText(e.target.value)}
						required
					/>
					<FontAwesomeIcon
						onClick={() => setPasswordVisible(!passwordVisible)}
						className="eye-icon"
						icon={passwordVisible ? faEyeSlash : faEye}
					/>
				</div>

				<input type="submit" className="submit-input" value="Login" />
			</form>

			<div className="sign-up-div">
				<p className="sign-up-text">Don't have an account? Sign up below</p>
				<Link className="sign-up-link" to="/sign-up">
					<button>Sign Up</button>
				</Link>
			</div>
		</main>
	);
}
