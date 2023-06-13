import { useState } from "react";
import "./Navbar.css";
import { Link } from "react-router-dom";

export default function Navbar() {
	return (
		<nav className="navbar">
			<div className="links">
				<Link to="/">Home</Link>
				<Link to="my-bots">My Bots</Link>
				<Link to="create-bot">Create Bot</Link>
			</div>
			<Link to="logout">Logout</Link>
		</nav>
	);
}
