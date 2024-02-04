import './index.scss';
import axios from 'axios';
import profPic from '../../assets/images/profpic.webp'
import Navbar from '../../components/Navbar'
import { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendar, faCircleNotch, faEnvelope, faMobile, faSpinner, faUser, faUserTie } from '@fortawesome/free-solid-svg-icons';
import { Buffer } from 'buffer';

const Profile = () =>{
    const [light,setLight]=useState(true);
    const [edit,setEdit]=useState(false);
    const [img,setImg]=useState(profPic);
    const [data, setData] = useState({ name: 'Username', contact: 'Contact', mail: 'Mail', profession: 'Profession', doj: 'DOJ' });
    const [err, setErr] = useState(false);
    const [loading, setLoading] = useState(true);
    const [file,setFile]=useState(null);

    useEffect(() => {
        getProfile();
    },[])

    const handleEdit = () =>  {
        setEdit(true);
    }

    const handleImg = (e) => {
        const selectedFile=e.target.files[0];
        if(selectedFile){
            if (selectedFile.type === "image/png") {
                setImg(URL.createObjectURL(selectedFile));
                setFile(selectedFile);
            } else {
                console.error("Invalid file type. Please select a .png file.");
            }
        }
    }

    const handleSave = () => {
        updateProfile();
        setEdit(false);
    }

    const handleInputs = (event) => {
        let inputs = { [event.target.name]: event.target.value };
        setData({ ...data, ...inputs });
    }

    const getProfile = async () => {
        try {
            const details = await axios.get("http://localhost:8080/api/profile", { withCredentials: true });
            const profDetails = details.data;
            if(profDetails.profilePic !== null){
                const pfpic = await axios.get("http://localhost:8080/api/profile/pic", { withCredentials: true, responseType: 'arraybuffer' });
                const pfpdata = pfpic.data;
                const base64String = Buffer.from(pfpdata, 'binary').toString('base64');
                const dataURL = `data:image/png;base64,${base64String}`;
                setImg(dataURL);
            }
            else setImg(profPic);
            const dojFormatted = new Date(profDetails.doj);
            const formattedDate = dojFormatted.toLocaleDateString();
            const displayData = {
                name: profDetails.username,
                contact: profDetails.contact,
                mail: profDetails.email,
                profession: profDetails.profession,
                doj: formattedDate,
            };
            setData(displayData);
            setLoading(false);
        } catch (error) {
            console.log(error)
            setErr(error.response ? error.response.data : 'An error occurred');
        }
          
    }

    const updateProfile = async () => {
        try {
            const profileData = {
                name: data.name,
                contact: data.contact,
                mail: data.mail,
                profession: data.profession,
                doj: data.doj,
            };
            setLoading(true);
            const updateStatus = await axios.put("http://localhost:8080/api/profile", profileData, { withCredentials: true });
            if (file) {
                const formData = new FormData();
                formData.append("profilePic", file);
                await axios.put("http://localhost:8080/api/profile/pic", formData, { withCredentials: true });
            }
            setLoading(false);
        } catch (error) {
            console.error("Error updating profile:", error.response ? error.response.data : error.message);
        }
    };
    

    return(
        <>
        <Navbar/>
        {loading?
        <div className='loadingIcon'>
            <FontAwesomeIcon icon={faSpinner} size='4x' className='loaderIcon'/>
        </div>
        :
        <div className="profile">
            {edit?
            <div className='profileContainer'>
                <div className="profilePic">
                    <input type="file" id="file" style={{ display: "none" }} onChange={event => handleImg(event)} accept="image/x-png" />
                    <label htmlFor="file" style={{ cursor: "pointer" }}>
                        <img src={img} alt='' title='click on image to edit'/>
                        <span>{file ? file.name : 'click to add a .png file as profile picture'}</span>                    </label>
                    <button onClick={handleSave}>Save changes</button>
                </div>
                <div className="profileInfo">
                    <h2>Click to edit</h2>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faUser} className='profIcon'/>
                        <input type="text" name="name"  placeholder='Name' value={data.name} onChange={event => handleInputs(event)} />
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
                        <input type="text" name="profession"  placeholder='Profession' value={data.profession} onChange={event => handleInputs(event)} />
                    </div>
                    <div className="inputProf">
                        <FontAwesomeIcon icon={faCalendar} className='profIcon'/>
                        <input type="date" pattern="\d{2}-\d{2}-\d{4}" name="doj" min="01/01/2000" max="04/20/2024" placeholder='DOJ' value={data.doj} onChange={event => handleInputs(event)} />
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
                            <span>{data.name}</span>
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
                            <span>{data.profession}</span>
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faCalendar} className='profIcon'/>
                            <span>{data.doj}</span>
                        </li>
                    </ul>
                </div>
            </div>}
        </div>
        }
        </>
    )
}

export default Profile;