import './App.css'
import Profile from './pages/Profile'
import Login from './components/Login'
import Paper from './pages/Paper'
import Dashboard from './pages/Dashboard/Dashboard';
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" >
          <Route index element={<Login />} />
          <Route path="login" element={<Login />} />
          <Route path="profile" element={<Profile />} />
          <Route path="dashboard" element={<Dashboard />}  />
          <Route path="paper" element={<Paper/>} />
        </Route>
      </Routes>
    </>
  )
}

export default App
