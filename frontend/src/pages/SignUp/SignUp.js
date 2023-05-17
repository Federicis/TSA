import { useState } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import "./SignUp.css";

export default function SignUp() {
	const [passwordVisible, setPasswordVisible] = useState(false);
	const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);

	return (
		<main className="sign-up-main">
			<h1 className="sign-up-heading">Sign Up</h1>
			<form className="sign-up-form" onSubmit={(e) => e.preventDefault()}>
				<input
					type="text"
					name="username"
					className="username-input"
					placeholder="Username"
					required
				/>
				<input
					type="email"
					name="email"
					className="email-input"
					placeholder="Email"
					required
				/>
				<div className="password-input-div">
					<input
						type={!passwordVisible ? "password" : "text"}
						name="password"
						className="password-input"
						placeholder="Password"
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
