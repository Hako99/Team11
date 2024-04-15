import React, { useState } from 'react';
import { Form, Input, Inputs, Title, Wrapper } from '../components/Common';
import { styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { LoginApi } from '../api/LoginApi';

const Login = () => {
  const [username, setId] = useState('');
  const [password, setPW] = useState('');

  const onChangeId = (e) => {
    setId(e.target.value);
  }
  const onChangePW = (e) => {
    setPW(e.target.value);
  }

  const onClick = async () => {
    const result = await LoginApi(username, password);
    const {token} = result;
    localStorage.setItem('Authorization', token);
    alert('새로고침 해주세요')
  }

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