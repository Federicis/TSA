import { useState, useEffect, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import SignUpRequest from "./SignUpRequest";
import "./SignUp.css";
import AuthContext from "../../context/AuthProvider";

export default function SignUp() {
	const [passwordVisible, setPasswordVisible] = useState(false);
	const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);

	const [usernameText, setUsernameText] = useState("");
	const [emailText, setEmailText] = useState("");
	const [passwordText, setPasswordText] = useState("");
	const [confirmPasswordText, setConfirmPasswordText] = useState("");

	const [error, setError] = useState("");
	const [successful, setSuccessful] = useState(false);

	const { auth } = useContext(AuthContext);

	const navigate = useNavigate();

	// redirect to / if already logged in
	useEffect(() => {
		if (auth.accessToken) navigate("/");
	}, []);

	// empty the error when inputs change
	useEffect(() => {
		setError("");
		setSuccessful(false);
	}, [usernameText, passwordText, confirmPasswordText, emailText]);

	// submit credentials to server
	const handleSubmit = async (e) => {
		e.preventDefault();

		if (passwordText !== confirmPasswordText) {
			setError("Passwords do not match.");
			return;
		}

		try {
			const data = await SignUpRequest(usernameText, emailText, passwordText);

			// display message if register was successful
			setSuccessful(true);
		} catch (err) {
			console.log(err);
			if (err.response) {
				if (err.response.status === 409) {
					setError("User already exists.");
				} else {
					setError("Server error.");
				}
			} else {
				setError("Unknown error.");
			}
		}
	};

	return (
		<main className="sign-up-main">
			<h1 className="sign-up-heading">Sign Up</h1>
			<form className="sign-up-form" onSubmit={handleSubmit}>
				{successful ? (
					<p className="success-div">
						Registration successful!
						<br /> Please activate your account by clicking the link sent to your
						email address.{" "}
					</p>
				) : (
					<p className="error-div">{error}</p>
				)}
				<input
					type="text"
					name="username"
					className="username-input"
					placeholder="Username"
					onChange={(e) => setUsernameText(e.target.value)}
					required
				/>
				<input
					type="email"
					name="email"
					className="email-input"
					placeholder="Email"
					onChange={(e) => setEmailText(e.target.value)}
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
				<div className="confirm-password-input-div">
					<input
						type={!confirmPasswordVisible ? "password" : "text"}
						name="confirm-password"
						className="confirm-password-input"
						placeholder="Confirm Password"
						onChange={(e) => setConfirmPasswordText(e.target.value)}
						required
					/>
					<FontAwesomeIcon
						onClick={() => setConfirmPasswordVisible(!confirmPasswordVisible)}
						className="eye-icon"
						icon={confirmPasswordVisible ? faEyeSlash : faEye}
					/>
				</div>

				<input type="submit" className="submit-input" value="Sign Up" />
			</form>

			<div className="login-div">
				<p className="login-text">
					Already have an account? Login{" "}
					<Link className="login-link" to="/login">
						here
					</Link>
				</p>
			</div>
		</main>
	);
}
