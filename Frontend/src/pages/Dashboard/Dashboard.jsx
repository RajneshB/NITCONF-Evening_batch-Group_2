import React from 'react';
import Navbar from '../../components/Navbar';
import './Dashboard.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';



const Dashboard = () => {
    const [reviewStatus,setReviewStatus]=React.useState(true);

    function handleReviewStatusToggle(){
        setReviewStatus((prevstate) => !prevstate);
    }

    return (
        <>
            <Navbar />
            <div className='dashboard-layout'>
                <div className='dashboard-toggle-container'>
                        <div className={reviewStatus?'dashboard-unreviewed':'dashboard-unreviewed dashboard-toggle-active'} onClick={handleReviewStatusToggle}>View Unreviewed Papers</div>
                        <div className={!reviewStatus?'dashboard-reviewed':'dashboard-reviewed dashboard-toggle-active'} onClick={handleReviewStatusToggle}>View Reviewed Papers</div>
                </div>
                <div className='dashboard-filter-container'>
                        {/* <div>Filter by Tags</div>
                        <div>Filter by Review Rating</div> */}
                </div>
                <div className='dashboard-card-grid'>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                    <div className='dashboard-card'>
                        <iframe src='/Assignment2.pdf' className='dashboard-card-pdf'/>
                        <div className='dashboard-card-info'>    
                            <h1 className='dashboard-card-pname'>Paper Name</h1>
                            <div className='rating-container'>
                                <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                                <p>Rating amount</p>
                            </div>
                            <h2 className='dashboard-card-aname'>Author Name</h2>
                            <p>Tags</p>
                        </div>
                    </div>
                </div>  
            </div>
            {/* <div className='card-container'>
                <iframe src='/Assignment2.pdf'className='card-pdf'/>
            </div> */}
        </>
    )
}

export default Dashboard;