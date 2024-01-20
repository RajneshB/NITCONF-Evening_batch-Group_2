import './App.css'
import Profile from './pages/Profile'
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" >
          <Route index element={<Profile/>} />
          <Route path="login" element={<Profile />} />
          <Route path="profile" element={<Profile />} />
        </Route>
      </Routes>
    </>
  )
}

export default App
