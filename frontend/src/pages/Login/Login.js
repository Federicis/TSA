import { useState } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import "./Login.css";

export default function Login() {
	const [passwordVisible, setPasswordVisible] = useState(false);

	return (
		<main className="login-main">
			<h1 className="login-heading">Login</h1>
			<form className="login-form" onSubmit={(e) => e.preventDefault()}>
				<input
					type="text"
					name="username"
					className="username-input"
					placeholder="Username"
				/>
				<div className="password-input-div">
					<input
						type={!passwordVisible ? "password" : "text"}
						name="password"
						className="password-input"
						placeholder="Password"
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
