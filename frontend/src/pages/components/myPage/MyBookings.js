import React, { useState, useEffect } from "react";


function MyBookings() {
const [bookings, setBookings] = useState([]);

useEffect(() => {
  const requestOptions = {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MiIsImV4cCI6MTcxMzI3MDQ5OSwiaWF0IjoxNzEzMjY2ODk5fQ.UG5HFKy-3qICThWHCFWJhutbdBYXO9pwgEHLTgQ2vqM'
      }
    };

  fetch('http://localhost:8080/api/bookings/my', requestOptions)
    .then(response => response.json())
    .then(data => {
    // 받아온 데이터에서 'data' 키에 해당하는 배열을 추출
      const bookingsData = data.data || [];
      setBookings(bookingsData); // 상태 업데이트
    })
    .catch(error => {
      console.error('Error fetching data: ', error);
    });
  }, []);

return (
      <div className="booking">
        <h1>예약 내역</h1>
        <table>
          <thead>
          <tr>
            <th>예약 번호</th>
            <th>회원 번호</th>
            <th>가게 번호</th>
            <th>Ticket Number</th>
            <th>상태</th>
            <th>줄서기 시간</th>
            <th>인원</th>
          </tr>
          </thead>
          <tbody>
          {bookings.slice(0, 10).map(booking => (
              <tr key={booking.bookingId}>
                <td>{booking.bookingId}</td>
                <td>{booking.userId}</td>
                <td>{booking.shopId}</td>
                <td>{booking.ticketNumber}</td>
                <td>{booking.state}</td>
                <td>{new Date(booking.reservedDateTime).toLocaleString()}</td>
                <td>{booking.reservedParty}</td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
}

export default MyBookings;
