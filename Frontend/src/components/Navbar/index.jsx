import './index.scss'
import { Outlet } from 'react-router-dom';

const Navbar = () => {
    return(
        <div className='app'>
            <div className="nav">
                
            </div>
            <Outlet/>
        </div>
    )
}

export default Navbar;