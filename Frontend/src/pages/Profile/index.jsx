import './index.scss';
import profPic from '../../assets/images/profpic.webp'
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendar, faEnvelope, faMobile, faUser, faUserTie } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';

const Profile = () =>{
    const [light,setLight]=useState(true);
    const [edit,setEdit]=useState(false);
    const [img,setImg]=useState(profPic);
    const [data, setData] = useState({ uname: 'John Doe', contact: '9747520966', mail: 'john@gmail.com', prof: 'Professor at NIT Calicut', doj: '04-03-2003' });
    const [err, setErr] = useState(false);
    const [loading, setLoading] = useState(false);
    const [file,setFile]=useState(null);
    const navigate = useNavigate();

    const toggleMode = () => {
        setLight(!light);
    }

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
        <div className="profile">
            {edit?
            <div className='profileContainer'>
                <div className="profilePic">
                    <input type="file" id="file" style={{ display: "none" }} onChange={event => handleImg(event)} />
                    <label htmlFor="file" style={{ cursor: "pointer" }}>
                        <img src={img} alt=''/>
                    </label>
                    <button onClick={handleSave}>Save changes</button>
                </div>
                <div className="profileInfo">
                    <div onClick={toggleMode} className={light?"toggleBarLight":"toggleBarDark"}>
                        <div className={light?"toggleCircleLight":"toggleCircleDark"}></div>
                    </div>
                    <h1>User Profile</h1>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faUser} className='profIcon'/>
                        <input type="text" name="uname"  placeholder='Name' value={data.uname} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faMobile} className='profIcon'/>
                        <input type="text" name="contact"  placeholder='Contact' value={data.contact} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faEnvelope} className='profIcon'/>
                        <input type="email" name="mail" placeholder='Email' value={data.mail} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faUserTie} className='profIcon'/>
                        <input type="text" name="prof"  placeholder='Profession' value={data.prof} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faCalendar} className='profIcon'/>
                        <input type="text" name="doj"  placeholder='DOJ' value={data.doj} onChange={event => handleInputs(event)} />
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
                    <div onClick={toggleMode} className={light?"toggleBarLight":"toggleBarDark"}>
                        <div className={light?"toggleCircleLight":"toggleCircleDark"}></div>
                    </div>
                    <h1>User Profile</h1>
                    <ul>
                        <li><span>Name</span><span>{data.uname}</span></li>
                        <li><span>Contact</span><span>{data.contact}</span> </li>
                        <li><span>Email address</span><span>{data.mail}</span> </li>
                        <li><span>Profession</span><span>{data.prof}</span> </li>
                        <li><span>Joined on </span><span>{data.doj}</span> </li>
                    </ul>
                </div>
            </div>}
        </div>
    )
}

export default Profile;