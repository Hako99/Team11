import React, {useState} from 'react';
import { Form, Input, Inputs, Title, Wrapper } from '../components/Common';
import { styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { LoginApi } from '../api/LoginApi';
import { useNavigate } from 'react-router-dom';
import { useAuth  } from '../AuthProvider';

const Login = () => {
  const [username, setId] = useState('');
  const [password, setPW] = useState('');
  const { login } = useAuth();

  const navigate = useNavigate(); // 네비게이션 함수 초기화

  const onChangeId = (e) => {
    setId(e.target.value);
  }
  const onChangePW = (e) => {
    setPW(e.target.value);
  }

  const onClick = async () => {
    try {
      const { token } = await LoginApi(username, password);
      if (token) {
        localStorage.setItem('Authorization', token);
        login(token);
        window.location.reload();
        navigate('/search');  // 성공 시, 바로 이동
      }
    } catch (error) {
      console.error("Login failed with error", error);
    }
  };


  return (
      <Wrapper>
        <Title>로그인</Title>
        <Form>
          <Inputs>
            <Input placeholder="username" value={username} onChange={onChangeId}/>
            <Input placeholder="password" type="password" value={password} onChange={onChangePW}/>
          </Inputs>
          <Button onClick={onClick}>로그인</Button>
        </Form>
        <CustomLink to="/signup">회원가입</CustomLink>
      </Wrapper>
  )
};

export default Login;

const Button = styled.button`
  background-color: #d1b000;
  color: white;
  padding: 20px;
  border-radius: 10px;
  width: 100px;
  border: 2px solid #d1b000;
`;
const CustomLink=styled(Link)`
  margin-top: 20px;
  color: black;
  text-decoration: none;
  &:visited {
    color: black;
    text-decoration: none;
  }
`