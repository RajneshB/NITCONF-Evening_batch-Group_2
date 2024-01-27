import './index.scss';
import profPic from '../../assets/images/profpic.webp'
import Navbar from '../../components/Navbar'
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendar, faEnvelope, faMobile, faUser, faUserTie } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';

const Profile = () =>{
    const [light,setLight]=useState(true);
    const [edit,setEdit]=useState(false);
    const [img,setImg]=useState(profPic);
    const [data, setData] = useState({ uname: 'John Doe', contact: '+91 9747525206', mail: 'john@gmail.com', prof: 'Professor at NIT Calicut', doj: '2012-09-11' });
    const [err, setErr] = useState(false);
    const [loading, setLoading] = useState(false);
    const [file,setFile]=useState(null);
    const navigate = useNavigate();

    const handleEdit = () =>  {
        setEdit(true);
    }

    const handleImg = (e) => {
        setImg(URL.createObjectURL(e.target.files[0]));
        setFile(event.target.files[0]);
    }

    const handleSave = () => {
        setEdit(false);
    }

    const handleInputs = (event) => {
        let inputs = { [event.target.name]: event.target.value };
        setData({ ...data, ...inputs });
    }

    return(
        <>
        <Navbar/>
        <div className="profile">
            {edit?
            <div className='profileContainer'>
                <div className="profilePic">
                    <input type="file" id="file" style={{ display: "none" }} onChange={event => handleImg(event)} />
                    <label htmlFor="file" style={{ cursor: "pointer" }}>
                        <img src={img} alt='' title='click on image to edit'/>
                    </label>
                    <button onClick={handleSave}>Save changes</button>
                </div>
                <div className="profileInfo">
                    <h2>Click to edit</h2>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faUser} className='profIcon'/>
                        <input type="text" name="uname"  placeholder='Name' value={data.uname} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faEnvelope} className='profIcon'/>
                        <input type="email" name="mail" placeholder='Email' value={data.mail} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faMobile} className='profIcon'/>
                        <input type="text" name="contact"  placeholder='Contact' value={data.contact} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faUserTie} className='profIcon'/>
                        <input type="text" name="prof"  placeholder='Profession' value={data.prof} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faCalendar} className='profIcon'/>
                        <input type="date" required pattern="\d{4}-\d{2}-\d{2}" name="doj" min="2000-01-01" max="2024-04-20" placeholder='DOJ' value={data.doj} onChange={event => handleInputs(event)} />
                    </div>
                </div>
            </div>
            :
            <div className="profileContainer">                
                <div className="profilePic">
                    <img src={img} alt='profile picture'/>
                    <button onClick={handleEdit}>Edit Profile</button>
                </div>
                <div className="profileInfo">
                    <h1>User Profile</h1>
                    <ul>
                        <li>
                            <FontAwesomeIcon icon={faUser} className='profIcon'/>
                            <span>{data.uname}</span>
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faEnvelope} className='profIcon'/>
                            <span>{data.mail}</span>
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faMobile} className='profIcon'/>
                            <span>{data.contact}</span>
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faUserTie} className='profIcon'/>
                            <span>{data.prof}</span>
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faCalendar} className='profIcon'/>
                            <span>{data.doj}</span>
                        </li>
                    </ul>
                </div>
            </div>}
        </div>
        </>
    )
}

export default Profile;