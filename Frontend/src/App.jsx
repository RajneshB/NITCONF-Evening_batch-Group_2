import './App.css'
import Profile from './pages/Profile'
import { Routes, Route } from "react-router-dom";
import Login from './components/Login'

function App() {
  return (
    <>
      <Routes>
        <Route path="/" >
          <Route index element={<Profile />} />
          <Route path="login" element={<Login />} />
          <Route path="profile" element={<Profile />} />
        </Route>
      </Routes>
    </>
  )
}

export default App
