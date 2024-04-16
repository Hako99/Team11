import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './css/Signup.css';

function Signup() {
  const navigate = useNavigate(); // 네비게이션 함수 초기화
  // 상태 관리
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    phoneNumber: '',
  });

  const [errors, setErrors] = useState({});
  const [responseMessage, setResponseMessage] = useState('');

  // 유효성 검사 함수
  const validateInput = () => {
    const newErrors = {};

    // 사용자 이름 유효성 검사
    const usernamePattern = /^[a-z0-9]{4,10}$/;
    if (!usernamePattern.test(formData.username)) {
      newErrors.username = 'username은 숫자 및 알파벳 소문자 4~10자로 입력해주세요.';
    }

    // 이메일 유효성 검사
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(formData.email)) {
      newErrors.email = '올바른 이메일 형식이 아닙니다.';
    }

    // 비밀번호 유효성 검사
    const passwordPattern = /^[a-zA-Z0-9!@#$%^&*()_+\-=\\[\]{};':",./<>?]{8,15}$/;
    if (!passwordPattern.test(formData.password)) {
      newErrors.password = '비밀번호는 숫자 및 알파벳 대소문자 그리고 특수문자를 포함한 8~15자로 입력해주세요.';
    }

    // 전화번호 유효성 검사
    const phoneNumberPattern = /^\d+$/;
    if (!phoneNumberPattern.test(formData.phoneNumber)) {
      newErrors.phoneNumber = '전화번호는 숫자로만 구성되어야 합니다.';
    }

    return newErrors;
  };

  // 입력 변경 시 상태 업데이트 함수
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  // 회원가입 처리 함수
  const handleSubmit = async (e) => {
    e.preventDefault();

    // 유효성 검사 수행
    const validationErrors = validateInput();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    // 요청 옵션 설정
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    };

    // API 호출
    try {
      const response = await fetch('http://localhost:8080/api/users/signup', requestOptions);
      const responseData =await response.json();
      console.log(responseData);


      if (response.ok) {
        // 회원가입 성공 시 메시지 표시
        setResponseMessage('회원가입이 완료되었습니다!');
        setErrors({});
        // 입력 필드 초기화
        setFormData({
          username: '',
          email: '',
          password: '',
          phoneNumber: '',
        });
        navigate('/'); // 로그인 페이지로 리디렉트
      } else {
        // API 응답에서 받은 에러 메시지 설정
        setResponseMessage(responseData.message);
        setErrors({});
      }
    } catch (error) {
      // 에러 발생 시 에러 메시지 표시
      setResponseMessage('다시 시도해주세요');
      console.error('에러:', error);
    }
  };

  return (
      <div className="signup-form">
        <h2>회원가입</h2>
        <form onSubmit={handleSubmit}>
          {/* 사용자 이름 입력 */}
          <div className="form-group">
            <label htmlFor="username">username:</label>
            <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
                required
            />
            {errors.username && <p className="error">{errors.username}</p>}
          </div>

          {/* 이메일 입력 */}
          <div className="form-group">
            <label htmlFor="email">email:</label>
            <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
            />
            {errors.email && <p className="error">{errors.email}</p>}
          </div>

          {/* 비밀번호 입력 */}
          <div className="form-group">
            <label htmlFor="password">password:</label>
            <input
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
            />
            {errors.password && <p className="error">{errors.password}</p>}
          </div>

          {/* 전화번호 입력 */}
          <div className="form-group">
            <label htmlFor="phoneNumber">phone number:</label>
            <input
                type="text"
                id="phoneNumber"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                required
            />
            {errors.phoneNumber && <p className="error">{errors.phoneNumber}</p>}
          </div>

          {/* 회원가입 제출 버튼 */}
          <button type="submit">회원가입</button>
        </form>

        {/* 응답 메시지 표시 */}
        {responseMessage && <p className="response">{responseMessage}</p>}
      </div>
  );
}

export default Signup;