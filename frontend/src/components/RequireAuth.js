import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";

import AuthContext from "../context/AuthProvider";

// wrapper component around protected routes
export default function RequireAuth() {
	const { auth } = useContext(AuthContext);

	return auth?.accessToken ? <Outlet /> : <Navigate to="/login" />;
}
