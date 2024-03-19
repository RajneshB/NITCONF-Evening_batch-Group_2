/* eslint-disable no-unused-vars */
import "./index.css"
import React from "react"
import { useNavigate } from "react-router-dom"
import { useState } from "react"
import Navbar from "../../components/Navbar"
import axios from "axios"
import { useLocation } from "react-router-dom"

const ResetPassword= () => {
    const location= useLocation();
    const searchParams = new URLSearchParams(location.search);
    const token=searchParams.get("token");
    
    console.log(token);
    const [password,setPassword]=useState("")
    const navigate=useNavigate();
    const [confirmPass,setConfirmPass]=useState("")
    const [valid,setValid]=useState(true)
    const passwordChange= (event)=>{

        setPassword(event.target.value)
    }
    const confirmPassChange= (event)=>{
        if(event.target.value !== password){
            setValid(false)
        }else{
            setValid(true)
        }
        setConfirmPass(event.target.value)
    }
    const updatePass = async(event) =>{
        event.preventDefault();
        if(valid){
        try{
            const res = await axios.post(`http://localhost:8080/api/forgotPassword/updatePassword?token=${token}`,{
                password:password
            });
            navigate("/login")
            alert("password is updated")
        }catch(err){
            console.log(err)
        }
        }else{
            setValid(false)
        }
    }
    function handleKeyPress(event){
        if(event.key=='Enter'){
          updatePass(event);
        }
      }
    return(
        <>
            <Navbar />
            <div className="reset--card">
                    <h2 id="reset--title">Reset Password</h2>
                    <input id='reset--password' type="password" placeholder="New Password" value={password} onChange={passwordChange} onKeyDown={handleKeyPress}/>
                    <input id='confirm--password' type="password" placeholder="Confirm new Password" value={confirmPass} onChange={confirmPassChange} onKeyDown={handleKeyPress} />
                    {!valid && <h3 id="Invalid">Passwords does not match</h3>}
                    <button id='updatePass' onClick={updatePass}>Update Password</button>
            </div>

        </>
    )
}

export default ResetPassword;