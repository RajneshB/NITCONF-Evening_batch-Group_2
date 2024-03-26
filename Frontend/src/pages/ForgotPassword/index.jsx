/* eslint-disable react/no-unescaped-entities */
/* eslint-disable no-unused-vars */
import './index.css'
import React from "react"
import Navbar from "../../components/Navbar"
import axios from 'axios'
import { useState } from 'react'
import { Bounce,ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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
            toast.success('Email Successfully Sent', {
                position: "top-center",
                autoClose: 4000,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "colored",
                transition: Bounce,
                });
        }catch(err){
            toast.error('That email does not have an account', {
                position: "top-center",
                autoClose: 4000,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "colored",
                transition: Bounce,
                });
        }
    }
    function handleKeyPress(event){
        if(event.key=='Enter'){
          sentLink(event);
        }
      }

    return(
        <>
            < Navbar />
            <div className='Password--card'>
                <h2 id='passwordReset--title'>Password Reset</h2>
                <p id='password--description'>Forgotten your password? Enter your e-mail address below, and we'll send you an e-mail allowing you to reset it.</p>
                <input id='email--input' placeholder='Email Address' type='email' value={email} onChange={emailChange} onKeyDown={handleKeyPress}/>


                <button id='reset' onClick={sentLink}>Reset My Password</button>
            </div>
        <ToastContainer />
        </>
    )
}

export default ForgotPassword