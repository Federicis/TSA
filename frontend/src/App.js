import "./App.css";

import Main from "./components/Main";
import PersistLogin from "./components/PersistLogin";
import RequireAuth from "./components/RequireAuth";
import { AuthProvider } from "./context/AuthProvider";
import CreateBot from "./pages/Bots/CreateBot/CreateBot";
import LoginPage from "./pages/Login/Login";
import SignUpPage from "./pages/SignUp/SignUp";

import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
	return (
		<BrowserRouter>
			<AuthProvider>
				<Routes>
					<Route path="/" element={<PersistLogin />}>
						<Route element={<RequireAuth />}>
							<Route index element={<Main prop1={"abcd"} prop2={"efgh"} />} />
						</Route>
						<Route path="login" element={<LoginPage />} />
						<Route path="sign-up" element={<SignUpPage />} />
						<Route path="create-bot" element={<CreateBot />} />
					</Route>
				</Routes>
			</AuthProvider>
		</BrowserRouter>
	);
}

export default App;
