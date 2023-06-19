import "./App.css";

import Main from "./components/Main";
import PersistLogin from "./components/PersistLogin";
import RequireAuth from "./components/RequireAuth";
import { AuthProvider } from "./context/AuthProvider";
import CreateBot from "./pages/Bots/CreateBot/CreateBot";
import MyBotsPage from "./pages/Bots/MyBots/MyBotsPage";
import EditBotPage from "./pages/Bots/EditBot/EditBotPage";
import LoginPage from "./pages/Login/Login";
import SignUpPage from "./pages/SignUp/SignUp";
import Navbar from "./components/Navbar/Navbar";
import Logout from "./components/Logout";
import LandingPage from "./pages/LandingPage/LandingPage";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import CreateRoutine from "./pages/Routines/CreateRoutine/CreateRoutine";
import ShowRoutines from "./pages/Routines/ShowRoutines/ShowRoutines";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Navbar />
        <Routes>
          <Route path="/" element={<PersistLogin />}>
            <Route element={<RequireAuth />}>
              <Route path="create-bot" element={<CreateBot />} />
              <Route path="my-bots" element={<MyBotsPage />} />
              <Route path="edit-bot" element={<EditBotPage />} />
              <Route path="create-routine" element={<CreateRoutine />} />
              <Route path="show-routines" element={<ShowRoutines />} />
              <Route path="logout" element={<Logout />} />
            </Route>
            <Route index element={<LandingPage />} />
            <Route path="login" element={<LoginPage />} />
            <Route path="sign-up" element={<SignUpPage />} />
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
