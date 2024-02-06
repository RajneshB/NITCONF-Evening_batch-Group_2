import React from "react"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import './styles.css'
import image1 from "../../assets/image1.webp"
// import {faMoon} from "@fortawesome/free-solid-svg-icons"
import { faEnvelope } from "@fortawesome/free-solid-svg-icons"
import { faLock } from "@fortawesome/free-solid-svg-icons"
import { faEye as show } from "@fortawesome/free-solid-svg-icons"
import { faEye as hidden } from "@fortawesome/free-regular-svg-icons"
import { Link } from 'react-router-dom'

// import "./App.css"

const Login = () =>{
    const [showPass,setShowPass]=React.useState(false)
    const [email,setEmail]=React.useState("");
    const [password,setPassword]=React.useState("");
    const navigate=useNavigate();
    console.log("hello")
    async function login(event){
      event.preventDefault();
      try{
        const res = await axios.post("http://localhost:8080/api/auth/signIn", {
          email:email,
          password:password,
        },{
          withCredentials: true
        });
        console.log(res);
        navigate("/dashboard");
      }
      catch(err){
        alert("Login Error");
      }
    }
    function togglePass(){
        setShowPass(prevShow => !prevShow)
    }

    const eyeIcon= <div onClick={togglePass}><FontAwesomeIcon icon={showPass? hidden:show}  className="eye-icon"/></div>

    return(
        <>
        <div className="login--container">
        <div className="login--card">
          <img src={image1} className="login--image" />
          <div className="login--contents">
            {/* <div onClick={toggleMode} className={light?"toggle--bar--light":"toggle--bar--dark"}><div className={light?"toggle--circle--light":"toggle--circle--dark"}></div></div>
             */}
 
          
            <p className="login--text">Program Committee Login</p>
            <div className="input--login">
                <FontAwesomeIcon icon={faEnvelope}  className="input--icon"/>
                <input type="text" placeholder="Email"  className="input--text" value={email} onChange={(event) =>setEmail(event.target.value)}/>

            </div>
            <div className="input--login">
                <FontAwesomeIcon icon={faLock}  className="input--icon"/>
                <input type={showPass? "text":"password"} placeholder="Password"  className="input--text" value={password} onChange={(event) =>setPassword(event.target.value)}/>
                {eyeIcon}

            </div>
            <div className="login--extra">
                  
                    <Link to="/forgotPassword" className="forgot--pass">Forgot Password</Link>
            </div>
            <button type="submit"className="login--submit" onClick={login}>Login</button>
          </div>
        </div>
      </div>
        </>
    )
}

export default Login;