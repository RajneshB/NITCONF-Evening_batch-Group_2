import './index.scss';
import profPic from '../../assets/images/profpic.webp'

const Profile = () =>{
    return(
        <div className="profile">
            <div className="profileContainer">
                <div className="profilePic">
                    <img src={profPic} alt='profile picture'/>
                    <button>Edit Profile</button>
                </div>
                <div className="profileInfo">
                    <h1>Profile</h1>
                    <ul>
                        <li>name</li>
                        <li>contact</li>
                        <li>email address</li>
                        <li>joined on</li>
                        <li>profession</li>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default Profile;