import { Link } from "react-router-dom";
import "./LandingPage.css";

export default function LandingPage() {
	return (
		<main className="landing-page-main">
			<div className="first-screen-wrapper">
				<div class="white-section-1">
					<h1 className="landing-page-heading">Automate Reddit with Ease</h1>
					<p className="first-section-text">
						Create powerful Reddit bots without coding and unlock a new level of
						automation.
					</p>
					<Link to="/sign-up" className="sign-up-button">
						Sign Up Now
					</Link>
				</div>
				<div class="red-section">
					<p className="second-section-text">
						Why spend hours learning programming languages and tinkering with code
						when you can effortlessly automate your Reddit activities?
					</p>
					<p className="second-section-text">
						Whether you're a community manager, a content creator, or a passionate
						Redditor, TSA empowers you to unleash the full potential of your Reddit
						presence.
					</p>
				</div>
			</div>
			<div class="white-section-2">
				<h1 className="third-section-heading">
					Our intuitive interface makes bot creation a breeze
				</h1>
				<div className="cards-div">
					<div className="card card-code">
						<img src={"/code.jpeg"} className="card-image" />
						<p className="card-text">
							<b>Coding Approach:</b> Complex coding, debugging, and API integration
							requirements make bot development time-consuming and challenging.
						</p>
					</div>
					<div className="card card-automate">
						<img src={"/code.jpeg"} className="card-image" />
						<p className="card-text">
							Effortless Bot Creation with Our App:
							<br /> <b>No coding necessary.</b>
						</p>
					</div>
				</div>
			</div>
		</main>
	);
}
