import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import SearchPage from './pages/SearchPage';
import ShopPage from './pages/ShopPage';
import './App.css'; // 전역 스타일

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/shop/:id" element={<ShopPage />} />
        </Routes>
      </Router>
  );
}


export default App;
