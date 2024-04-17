import React, { useState } from 'react';

function Bookings(props) {
  const [reservedDatetime, setReservedDatetime] = useState(new Date().toISOString()); // 오늘 날짜와 시간을 ISO 형식으로 설정
  const [reservedParty, setReservedParty] = useState('');
  const {shopId} = props;

  const handleSubmit = (e) => {
    e.preventDefault();

    // 입력 받은 값을 가지고 요청 보내기
    fetch('http://localhost:8080/api/bookings', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization' : 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MiIsImV4cCI6MTcxMzI3MDQ5OSwiaWF0IjoxNzEzMjY2ODk5fQ.UG5HFKy-3qICThWHCFWJhutbdBYXO9pwgEHLTgQ2vqM'
      },
      body: JSON.stringify({ reservedParty, reservedDatetime, shopId }), // 입력 받은 값들을 요청 본문에 포함
    })
    .then(response => response.json()) // JSON 데이터 추출
    .then(data => {
      console.log('Success:', data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
  };

  return (
      <div className="booking">
      <form onSubmit={handleSubmit}>
        <label>
          예약 인원 :
          <input
              className="party"
              type="text"
              value={reservedParty}
              onChange={(e) => setReservedParty(e.target.value)}
          />
        </label>
        <button className="btn" type="submit">예약하기</button>
      </form>
      </div>
  );
}

export default Bookings;
