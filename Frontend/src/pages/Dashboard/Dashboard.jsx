import React from 'react';
import Navbar from '../../components/Navbar';
import Card from '../../components/Card/Card';
import './Dashboard.css'



const Dashboard = () => {
    const [reviewStatus,setReviewStatus]=React.useState(true);

    function handleReviewStatusToggle(){
        setReviewStatus((prevstate) => !prevstate);
    }

    const array=[   {id:1,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:2,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:3,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:4,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:5,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:6,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:7,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]},
                    {id:8,paperName:"Exploring the Cosmos",rating:9.5,authorName:"Dr Joel Joseph",tags:["Science","Technology","Art","Management"]}
                ]

    const cardArray=array.map((obj) => {
        return <Card id={obj.id} paperName={obj.paperName} rating={obj.rating} authorName={obj.authorName} tags={obj.tags} />
    })

    console.log(cardArray)

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
                    {cardArray}
                </div>  
            </div>
        </>
    )
}

export default Dashboard;