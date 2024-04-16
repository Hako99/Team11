import React, { createContext, useContext, useState, useEffect } from 'react';

// Context 생성
const AuthContext = createContext(null);

// Context를 사용하기 쉽게 하는 Custom Hook
export const useAuth = () => useContext(AuthContext);

// Provider 컴포넌트
export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // 로컬 스토리지에서 인증 상태 초기화
  useEffect(() => {
    const token = localStorage.getItem('Authorization');
    setIsAuthenticated(!!token);  // Boolean 변환: token이 있으면 true, 없으면 false
  }, []);

  // 인증 상태를 변경하는 메소드
  const login = (token) => {
    localStorage.setItem('Authorization', token);
    setIsAuthenticated(true);
  };

  const logout = () => {
    localStorage.removeItem('Authorization');
    setIsAuthenticated(false);
  };

  // Context 값 제공
  return (
      <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
        {children}
      </AuthContext.Provider>
  );
};

export default AuthProvider;