import React from 'react'
import './index.scss'
import {motion} from "framer-motion"
const Navbar = () => {
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
            <div className="toggle--bar" data-isOn={light} onClick={toggleMode}>
                  <motion.div className="toggle--circle" layout transition={spring} data-isOn={light}/>
             </div>
        </div>
        </div>
    )
}

export default Navbar;