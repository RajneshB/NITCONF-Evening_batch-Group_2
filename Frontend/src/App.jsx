import './App.css'
import Profile from './pages/Profile'
import Login from './components/Login'
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard/Dashboard';
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" >
          <Route index element={<Dashboard />} />
          <Route path="login" element={<Login />} />
          <Route path="profile" element={<Profile />} />
          <Route path="dashboard" element={<Dashboard />}  />
        </Route>
      </Routes>
    </>
  )
}

export default App
