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
import Header from './components/header';
import AuthProvider from './AuthProvider';

function App() {
  const access = localStorage.getItem('Authorization');
  const isAuthenticated = Boolean(access);
  console.log('isAuthenticated:', isAuthenticated);
  return (
      <AuthProvider>
      <Router>
        <Header />
        <Routes>
          <Route path="/" element={isAuthenticated ? <Navigate to="/search"/> : <Login />} />
          <Route path="/signup" element={isAuthenticated ? <Navigate to="/"/> : <Signup />} />
          <Route path="/mypage" element={<PrivateRoute authenticated={access}><MyPage /></PrivateRoute>} />
          <Route path="/search" element={<PrivateRoute authenticated={access}><SearchPage /></PrivateRoute>} />
          <Route path="/shop" element={<PrivateRoute authenticated={access}><ShopPage /></PrivateRoute>} />
        </Routes>
      </Router>
      </AuthProvider>
  );
}


export default App;
