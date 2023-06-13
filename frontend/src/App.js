import "./App.css";

import Main from "./components/Main";
import PersistLogin from "./components/PersistLogin";
import RequireAuth from "./components/RequireAuth";
import { AuthProvider } from "./context/AuthProvider";
import CreateBot from "./pages/Bots/CreateBot/CreateBot";
import MyBotsPage from "./pages/Bots/MyBots/MyBotsPage";
import LoginPage from "./pages/Login/Login";
import SignUpPage from "./pages/SignUp/SignUp";
import Navbar from "./components/Navbar/Navbar";

import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
	return (
		<BrowserRouter>
			<AuthProvider>
				<Navbar />
				<Routes>
					<Route path="/" element={<PersistLogin />}>
						<Route element={<RequireAuth />}>
							<Route index element={<Main prop1={"abcd"} prop2={"efgh"} />} />
							<Route path="create-bot" element={<CreateBot />} />
							<Route path="my-bots" element={<MyBotsPage />} />
						</Route>
						<Route path="login" element={<LoginPage />} />
						<Route path="sign-up" element={<SignUpPage />} />
					</Route>
				</Routes>
			</AuthProvider>
		</BrowserRouter>
	);
}

export default App;
