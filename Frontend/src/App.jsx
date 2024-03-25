import './App.css'
import Profile from './pages/Profile'
import Login from './components/Login'
import Paper from './pages/Paper'
import Dashboard from './pages/Dashboard/Dashboard';
import { Routes, Route, Navigate } from "react-router-dom";
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';
import { useState } from 'react';
import axios from 'axios';

function App() {
  const [loggedin,setLoggedin] = useState(false);
  const getProfile = async () => {
    try {
        const details = await axios.get("http://localhost:8080/api/profile", { withCredentials: true });
        const profDetails = details.data;
        if(profDetails!== null){
          setLoggedin(true);
        }
    } catch (error) {
        console.log(error)
    }    
  }

  const ProtectedRoute = ({ children }) => {
    getProfile();
    if (!loggedin) {
      return <Navigate to="/login" />
    }
    return children
  };

  return (
    <>
      <Routes>
        <Route path="/" >
          <Route index element={<Login />} />
          <Route path="login" element={<Login />} />
          <Route path="profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
          <Route path="dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>}  />
          <Route path="paper/:id" element={<ProtectedRoute><Paper /></ProtectedRoute>}/>
          <Route path="forgotPassword" element={<ForgotPassword />} />
          <Route path="reset-Password" element={<ResetPassword />} />
        </Route>
      </Routes>
    </>
  )
}

export default App
