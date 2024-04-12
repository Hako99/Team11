import React, { useState } from 'react';
import './css/SearchPage.css';

function SearchPage() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzEyMDM5NjcyfQ.yOcE3LoI-bcuAy3I9zepf0QUqqUuXus-lh7808J6MOs'; // 이는 예시입니다. 실제 토큰을 여기에 설정하세요.
  const encodedToken = encodeURIComponent(token);
  const handleSearch = (e) => {
    e.preventDefault();

    fetch(`http://localhost:8080/api/shops?search=${query}`,
        {
          method: 'GET', // 또는 'POST', 'PUT', 'DELETE' 등
          credentials: 'include', // 쿠키 정보를 함께 보냄
          headers: {
            'Authorization': `Bearer ${encodedToken}`, // 헤더에 Authorization 추가
            // 필요한 경우 추가 헤더를 여기에 설정
          }
        })
    .then(response => response.json())
    .then(data => {
      setResults(data.data.documents);
      console.log('Fetching data with query:', data.data.documents);
    })
    .catch(error => console.error('Error:', error));
  };


  return (
      <div className="search-container">
        <h1><strong>식당 검색하기</strong></h1>
        <form onSubmit={handleSearch}>
          <input
              type="text"
              placeholder="검색어를 입력하세요"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className="search-input"
          />
          <button type="submit" className="search-button">검색</button>
        </form>
        <div className="results-container">
          {results?.map((result, index) => (
              <div key={index} className="result-item">
                <h3>{result.place_name}</h3>
                <p>{result.address_name}</p>
                <p>{result.phone}</p>
              </div>
          ))}
        </div>
      </div>
  );
}

export default SearchPage;

