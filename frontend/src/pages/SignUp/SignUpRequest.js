import axios from "axios";

export default async function SignUpRequest(username, email, password) {
	const requestBody = { username, email, password };

	const response = await axios.post(
		`http://localhost:8080/api/v1/auth/register`,
		requestBody
	);
	return response.data;
}
