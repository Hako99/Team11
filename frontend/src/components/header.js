import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styled from 'styled-components';

const HeaderContainer = styled.div`
  display: flex;
  text-align: center;
  justify-content: space-between;
  align-items: center;
  width: 600px;
  background-color: #f3f3f3;
  padding: 10px 20px;
  margin: auto;
  margin-bottom: 2px;
`;

const Logo = styled.div`
  font-size: 24px;
  font-weight: bold;
`;

const NavItems = styled.div`
  display: flex;
  gap: 10px;
`;

const Button = styled.button`
  padding: 8px 10px;
  background-color: #d1b000;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 80px;
  text-align: center;
  font-size: 10px;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
  padding: 8px 10px;
  color: white;  // Ensures the Link component inherits button-like text styling
`;



function Header() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('Authorization');
    window.location.reload();
    navigate('/', { replace: true });
  };

  return (
      <HeaderContainer>
        <Logo>tabling</Logo>
        <NavItems>
          <Button as={StyledLink} to="/search">식당조회</Button>
          <Button as={StyledLink} to="/mypage">예약내역</Button>
          <Button onClick={handleLogout}>Logout</Button>
        </NavItems>
      </HeaderContainer>
  );
}

export default Header;
