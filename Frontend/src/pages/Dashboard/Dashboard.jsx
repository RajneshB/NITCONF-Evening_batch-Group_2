import React from 'react';
import Navbar from '../../components/Navbar';
import './Dashboard.css'



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