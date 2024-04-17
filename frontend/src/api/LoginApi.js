import axios from 'axios';
export const LoginApi = async (username, password) => {
  const result = await axios.post('http://localhost:8080/api/users/login', {username, password});
  return result.data.data;
}