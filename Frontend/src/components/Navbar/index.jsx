    import React from 'react'
    import './index.scss'
    import axios from 'axios'
    import { useNavigate } from 'react-router-dom'
    import {motion} from "framer-motion"
    import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
    import { faMoon, faSun } from '@fortawesome/free-solid-svg-icons'
    import { Link } from 'react-router-dom'
    const Navbar = () => {
        const navigate= useNavigate();

        const handleLogout=async ()=>{
            try{
                const response = await axios.post("http://localhost:8080/api/auth/logout", null, { withCredentials: true })
                if(response.status===200){
                    console.log("Logout Successful")
                    navigate("/login")

                }else{
                    console.error("Logout failed")
                }
            }catch(err){
                console.log(err)
            }

        }
        const [light,setLight] =React.useState(false)
        function toggleMode(){
            setLight(prevLight => !prevLight)
        }
        const spring = {
            type: "spring",
            stiffness: 700,
            damping: 30
        }

        return(
            <div className='app'>
                <div className="nav">
                    <h1>NITCONF</h1>
                    <div className='navlink-container'>
                        <Link to='/dashboard'>Home</Link>
                        <Link to='/profile'>Profile</Link>
                        <div className="toggle--bar" data-isOn={light} onClick={toggleMode}>
                            {light && <FontAwesomeIcon icon={faSun} color='white' size={'2x'} className='toggle-sun'/>}
                            <motion.div className="toggle--circle" layout transition={spring} data-isOn={light}/>
                            {!light && <FontAwesomeIcon icon={faMoon} color='black' size={'2x'} className='toggle-moon'/>}
                        </div>  
                        <button className='nav-logout' onClick={handleLogout}>Log out</button>
                    </div>
                </div>
            </div>
        )
    }

    export default Navbar;