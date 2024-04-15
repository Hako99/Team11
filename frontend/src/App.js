import React from 'react';
import {
  BrowserRouter as Router,
  Navigate,
  Route,
  Routes
} from 'react-router-dom';
import Login from './pages/Login';
import MyPage from './pages/MyPage';
import SearchPage from './pages/SearchPage';
import ShopPage from './pages/ShopPage';
import './App.css';
import Signup from "./pages/Signup";
import PrivateRoute from "./router/PrivateRoute";

function App() {
  const access = localStorage.getItem('Authorization');
  const isAuthenticated = Boolean(access);
  return (
      <Router>
        <Routes>
          <Route path="/" element={isAuthenticated ? <Navigate to="/search" /> : <Login />} />
          <Route path="/signup" element={isAuthenticated ? <Navigate to="/search" /> : <Signup />} />
          <Route path="/mypage" element={<PrivateRoute authenticated={access} component={<MyPage />} />} />
          <Route path="/search" element={<PrivateRoute authenticated={access} component={<SearchPage />} />} />
          <Route path="/shop" element={<PrivateRoute authenticated={access} component={<ShopPage />} />} />
        </Routes>
      </Router>
  );
}


export default App;
