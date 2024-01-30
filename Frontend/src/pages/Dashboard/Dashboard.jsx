import React from 'react';
import Navbar from '../../components/Navbar';
import Table from '../../components/Table/Table';
import Card from '../../components/Card/Card'
import Select from 'react-dropdown-select'
import './Dashboard.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBorderAll, faFilter, faList } from '@fortawesome/free-solid-svg-icons';



const Dashboard = () => {
    const [reviewStatus,setReviewStatus]=React.useState(true);

    const [list,setList]=React.useState(false);

    const [selectedOption, setSelectedOption] = React.useState('None');

    const options=[
        {id:'Science',name:'Science'},
        {id:'Technology',name:'Technology'},
        {id:'Art',name:'Art'},
        {id:'Management',name:'Management'}
    ]

    const handleSelectChange = (event) => {
        setSelectedOption(event.target.value);
    };

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
    const tableArray=array.map((obj) => {
        return <Table id={obj.id} paperName={obj.paperName} rating={obj.rating} authorName={obj.authorName} tags={obj.tags} />
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
                        <div className="view-container">
                            <div className={`list-view ${list ? 'lactive' : ''}`} onClick={()=>setList(true)}>
                                <FontAwesomeIcon icon={faList} className='view-icon' />
                                <div>List View</div>
                            </div>
                            <div className={`grid-view ${!list ? 'gactive' : ''}`} onClick={() => setList(false)}>
                                <FontAwesomeIcon icon={faBorderAll} className='view-icon' />
                                <span>Grid View</span>
                            </div>
                        </div>
                        <div className="filter-container">
                            <div className="filter-rating">
                                <FontAwesomeIcon icon={faFilter} className='filter-icon'/>
                                <div>Filter by Rating</div>
                                <select className='filter-select' value={selectedOption} onChange={handleSelectChange}>
                                    <option value="None">None</option>
                                    <option value="Ascending">Ascending</option>
                                    <option value="Descending">Descending</option>
                                </select>
                            </div>
                            <div className="filter-tag">
                                <FontAwesomeIcon icon={faFilter} className='filter-icon'/>
                                <div>Filter by Tags</div>
                                <Select
                                    className='tag-select'
                                    name="select"
                                    options={options}
                                    labelField='id'
                                    valueField='name'
                                    multi
                                    color='rgb(21,199,113)'
                                    searchable='true'
                                    placeholder='Select your tags'
                                ></Select>
                            </div>
                        </div>
                </div>
                {!list?
                    <div className='dashboard-card-grid'>
                        {cardArray}
                    </div> :
                    <div className="dashboard-table-container">
                        {/* <div className='dashboard-table-cl'>
                            <div className="table-icon-container-cl">
                                Paper
                            </div>
                            <div className='dashboard-table-info-cl'>
                                <div className="table-h1-cl">
                                    <h1>Title</h1>
                                </div>
                                <div className='table-rating-container-cl'>
                                    <p>Rating</p>
                                </div>
                                <div className="table-h2-cl">
                                    <h2>Author</h2>
                                </div>
                                <div className="table-tag-container-cl">
                                    Tags
                                </div>
                            </div>
                        </div>   */}
                        {tableArray}
                    </div>
                }
            </div>
        </>
    )
}

export default Dashboard;