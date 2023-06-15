import { useState, useEffect, useContext } from "react";
import "./Navbar.css";
import { Link } from "react-router-dom";
import AuthContext from "../../context/AuthProvider";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
	faHome,
	faSignOutAlt,
	faPlus,
	faRobot,
} from "@fortawesome/free-solid-svg-icons";

export default function Navbar() {
	const [windowWidth, setWindowWidth] = useState(window.innerWidth);

	const { auth } = useContext(AuthContext);

	useEffect(() => {
		const handleResize = () => {
			setWindowWidth(window.innerWidth);
		};

		window.addEventListener("resize", handleResize);

		return () => {
			window.removeEventListener("resize", handleResize);
		};
	}, []);

	const isMobile = windowWidth < 750;

	return auth.accessToken ? (
		<nav className="navbar">
			<div className="links">
				<Link to="/">{isMobile ? <FontAwesomeIcon icon={faHome} /> : "Home"}</Link>
				<Link to="my-bots">
					{isMobile ? <FontAwesomeIcon icon={faRobot} /> : "My Bots"}
				</Link>
				<Link to="create-bot">
					{isMobile ? <FontAwesomeIcon icon={faPlus} /> : "Create Bot"}
				</Link>
			</div>
			<Link to="logout">
				{isMobile ? <FontAwesomeIcon icon={faSignOutAlt} /> : "Log Out"}
			</Link>
		</nav>
	) : (
		<></>
	);
}
