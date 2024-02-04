/* eslint-disable react/no-unescaped-entities */
/* eslint-disable no-unused-vars */
import './index.css'
import React from "react"
import Navbar from "../../components/Navbar"
import axios from 'axios'
import { useState } from 'react'


const ForgotPassword=() => {
    const [email,setEmail]= useState("")
    const emailChange= (event) =>{
        setEmail(event.target.value)
    }
    async function sentLink(event){
        event.preventDefault();
        try{
            const res=await axios.post("http://localhost:8080/api/forgotPassword/sentlink",{
                email: email
            });
            alert("email sent")
        }catch(err){
            alert(err);
        }
    }

    return(
        <>
            < Navbar />
            <div className='Password--card'>
                <h2 id='passwordReset--title'>Password Reset</h2>
                <p id='password--description'>Forgotten your password? Enter your e-mail address below, and we'll send you an e-mail allowing you to reset it.</p>
                <input id='email--input' placeholder='Email Address' type='email' value={email} onChange={emailChange}/>


                <button id='reset' onClick={sentLink}>Reset My Password</button>
            </div>

        </>
    )
}

export default ForgotPassword