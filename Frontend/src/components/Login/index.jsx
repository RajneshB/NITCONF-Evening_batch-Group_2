import React from "react"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import './styles.css'
import image1 from "../../assets/image1.webp"
// import {faMoon} from "@fortawesome/free-solid-svg-icons"
import { faEnvelope } from "@fortawesome/free-solid-svg-icons"
import { faLock } from "@fortawesome/free-solid-svg-icons"
import { faEye as show } from "@fortawesome/free-solid-svg-icons"
import { faEye as hidden } from "@fortawesome/free-regular-svg-icons"
import Navbar from '../Navbar'
// import "./App.css"

const Login = () =>{
    const [showPass,setShowPass]=React.useState(false)


    function togglePass(){
        setShowPass(prevShow => !prevShow)
    }

    const eyeIcon= <div onClick={togglePass}><FontAwesomeIcon icon={showPass? hidden:show}  className="eye-icon"/></div>

    return(
        <>
        <Navbar/>
        <div className="login--container">
        <div className="login--card">
          <img src={image1} className="login--image" />
          <div className="login--contents">
            {/* <div onClick={toggleMode} className={light?"toggle--bar--light":"toggle--bar--dark"}><div className={light?"toggle--circle--light":"toggle--circle--dark"}></div></div>
             */}
 
          
            <p className="login--text">Program Committee Login</p>
            <div className="input--login">
                <FontAwesomeIcon icon={faEnvelope}  className="input--icon"/>
                <input type="text" placeholder="Email"  className="input--text"/>

            </div>
            <div className="input--login">
                <FontAwesomeIcon icon={faLock}  className="input--icon"/>
                <input type={showPass? "text":"password"} placeholder="Password"  className="input--text"/>
                {eyeIcon}

            </div>
            <div className="login--extra">
                    <input type="checkbox" id="remember--check"/>
                    <label htmlFor="remember--check" className="remember--text">Remember me</label>
                    <a href="#" className="forgot--pass">Forgot Password</a>
            </div>
            <button type="submit"className="login--submit">Login</button>
          </div>
        </div>
      </div>
        </>
    )
}

export default Login;