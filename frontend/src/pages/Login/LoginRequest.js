import axios from "axios";

export default async function LoginRequest(username, password) {
	const requestBody = { username, password };

	const response = await axios.post(
		`http://localhost:8080/api/v1/auth/login`,
		requestBody
	);
	return response.data;
}
