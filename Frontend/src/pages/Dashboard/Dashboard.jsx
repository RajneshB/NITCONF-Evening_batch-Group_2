import React from 'react';
import Navbar from '../../components/Navbar';
import Table from '../../components/Table/Table';
import Card from '../../components/Card/Card'
import Select from 'react-dropdown-select'
import './Dashboard.scss'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBorderAll, faFilter, faList } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';


const Dashboard = () => {

    const [reviewStatus,setReviewStatus]=React.useState(true);

    const [list,setList]=React.useState(false);
    
    const [selectedRating, setSelectedRating] = React.useState('None');
    
    const [selectedTags, setSelectedTags] = React.useState([]);

    const [selectedDecision,setSelectedDecision] = React.useState('None');

    const [papers,setPapers] = React.useState([]);

    const handleTagChange = (selectedTags) => {
        setSelectedTags(selectedTags);
    };

    const handleSelectChange = (event) => {
        setSelectedRating(event.target.value);
    };

    const handleSelectDecisionChange = (event) => {
        setSelectedDecision(event.target.value);
    }
    
    const options=[
        {id:'Science',name:'Science'},
        {id:'Technology',name:'Technology'},
        {id:'Art',name:'Art'},
        {id:'Management',name:'Management'}
    ]

    React.useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/paper/all');
                setPapers(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, []);

    const filteredByDecision = selectedDecision === 'None' ? papers : papers.filter(paper => paper.decision === selectedDecision);

    const filteredByRating = selectedRating === 'None' ? filteredByDecision : selectedRating === 'Ascending' ?
        filteredByDecision.slice().sort((a, b) => a.rating - b.rating) :
        filteredByDecision.slice().sort((a, b) => b.rating - a.rating);


        const filteredByTags = selectedTags.length === 0
        ? filteredByRating
        : filteredByRating.filter(paper => 
          paper.tags.some(paperTag => 
            selectedTags.some(selectedTag => selectedTag.id === paperTag)
          )
        );

    // Further filter by Review Status
    const finalPapers = reviewStatus?
        filteredByTags.filter(paper => paper.status === 'Reviewed'):
        filteredByTags.filter(paper => paper.status !== 'Reviewed');

    const cardArray=finalPapers.map((i) => {
        return (
            <Card id={i.id}   paperName={i.paperName} rating={i.rating} authorName={i.authorName} tags={i.tags} />
        )
    })

    const tableArray=finalPapers.map((i) => {
        return (
            <Table id={i.id}   paperName={i.paperName} rating={i.rating} authorName={i.authorName} tags={i.tags} />
        )
    })

    return (
        <>
            <Navbar />
            <div className='dashboard-layout'>
                <div className='dashboard-toggle-container'>
                        <div className={reviewStatus?'dashboard-unreviewed':'dashboard-unreviewed dashboard-toggle-active'} onClick={()=>setReviewStatus(false)}>View Unreviewed Papers</div>
                        <div className={!reviewStatus?'dashboard-reviewed':'dashboard-reviewed dashboard-toggle-active'} onClick={()=>setReviewStatus(true)}>View Reviewed Papers</div>
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
                            
                            <div className="filter-decision">
                                <FontAwesomeIcon icon={faFilter} className='filter-icon'/>
                                <div>Filter by Decision</div>
                                <select className='filter-select' value={selectedDecision} onChange={handleSelectDecisionChange}>
                                    <option value="None">None</option>
                                    <option value="Pending">Pending</option>
                                    <option value="Accept">Accept</option>
                                    <option value="Reject">Reject</option>
                                </select>
                            </div>
                            <div className="filter-rating">
                                <FontAwesomeIcon icon={faFilter} className='filter-icon'/>
                                <div>Filter by Rating</div>
                                <select className='filter-select' value={selectedRating} onChange={handleSelectChange}>
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
                                    onChange={(values) => handleTagChange(values)}
                                    value={selectedTags}
                                ></Select>
                            </div>
                        </div>
                </div>
                {!list?
                    <div className='dashboard-card-grid'>
                        {cardArray}
                    </div> :
                    <div className="dashboard-table-container">
                        <div className='dashboard-table-cl'>
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
                                <div className="table-view-container-cl">
                                    View Paper
                                </div>
                            </div>
                        </div>
                        {tableArray}
                    </div>
                }
            </div>
        </>
    )
}

export default Dashboard;