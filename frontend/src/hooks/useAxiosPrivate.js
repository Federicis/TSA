import axios from "axios";
import AuthContext from "../context/AuthProvider";
import { useContext, useEffect } from "react";
import useRefreshToken from "./useRefreshToken";

// custom hook for sending requests using access tokens and automatically replacing expired ones
export default function useAxiosPrivate() {
	// import refresh hook and auth context
	const refresh = useRefreshToken();
	const { auth } = useContext(AuthContext);

	// create new instance of axios
	const axiosPrivate = axios.create();

	// add interceptor to request
	useEffect(() => {
		const requestIntercept = axiosPrivate.interceptors.request.use(
			(config) => {
				// if no auth header, add the access token from the auth state
				if (!config.headers["Authorization"]) {
					config.headers["Authorization"] = `Bearer ${auth?.accessToken}`;
				}

				// otherwise, the response interceptor would have already dealt with it, so we just return it unchanged
				return config;
			},
			(error) => Promise.reject(error)
		);

		// clean-up
		return () => {
			axiosPrivate.interceptors.request.eject(requestIntercept);
		};
	}, [auth, refresh]);

	// add interceptor to response
	useEffect(() => {
		const responseIntercept = axiosPrivate.interceptors.response.use(
			(response) => response,
			async (error) => {
				const prevRequest = error?.config;

				// sent a request with an expired token => we should refresh it
				if (error?.response?.status === 403 && !prevRequest?.sent) {
					prevRequest.sent = true;

					// get a new access token
					const newAccessToken = await refresh();

					// replace request header
					prevRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;

					// return the new modified request
					return axiosPrivate(prevRequest);
				}

				return Promise.reject(error);
			}
		);

		// clean-up
		return () => {
			axiosPrivate.interceptors.response.eject(responseIntercept);
		};
	}, [auth, refresh]);

	return axiosPrivate;
}
