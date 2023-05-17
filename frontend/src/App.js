import "./App.css";

import Main from "./components/Main";
import LoginPage from "./pages/Login/Login";
import SignUpPage from "./pages/SignUp/SignUp";

import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/">
					<Route index element={<Main prop1={"abcd"} prop2={"efgh"} />} />
					<Route path="login" element={<LoginPage />} />
					<Route path="sign-up" element={<SignUpPage />} />
				</Route>
			</Routes>
		</BrowserRouter>
	);
}

export default App;
