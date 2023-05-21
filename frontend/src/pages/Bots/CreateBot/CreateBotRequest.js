import useAxiosPrivate from "../../../hooks/useAxiosPrivate";

export default async function CreateBotRequest(
  name,
  description,
  redditUsername,
  password,
  clientId,
  clientSecret
) {
  const axios = useAxiosPrivate();

  const requestBody = {
    name,
    description,
    redditUsername,
    password,
    clientId,
    clientSecret,
  };

  const response = await axios.post(
    `http://localhost:8080/api/v1/bots`,
    requestBody
  );
  return response.data;
}
