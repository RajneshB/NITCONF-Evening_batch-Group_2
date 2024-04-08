import './index.scss'
import React, { useState } from 'react'
import PDFViewer from '../../components/PDFViewer';
import Navbar from '../../components/Navbar'
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Rating from '../../components/Rating/Rating'
const Paper = () => {

    const {id} = useParams();
    const [open,setOpen]=useState(false);
    const navigate =useNavigate();
    const [paper,setPaper]= React.useState({});
    const [assignedReviewer, setAssignedReviewer] = useState(null);



    
    const [commentsArray,setCommentsArray] = React.useState([]);
    function trigger(){
        setOpen(prevOpen => !prevOpen)
    }
    
    const selectReviewer = (reviewerName) => {
        if(reviewerName!=assignedReviewer){
            setAssignedReviewer(reviewerName);
        }
        trigger();
    };



    React.useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/paper/${id}`);
                setPaper(response.data);
                
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
    
        fetchData();
    }, []);

    React.useEffect(() => {
        if (paper.reviews) {
            setCommentsArray(
                paper.reviews.map((i) => (
                    <div className='comments' key={i.reviewer}>
                        {i.comments===null?
                        <div className='no-re'>
                            <h3 className='comment--author'>
                            {i.reviewer} - No Review Yet
                            </h3>
                            <button>Send Reminder </button>
                        </div>:<div>
                        <h3 className='comment--author'>
                            {i.reviewer}
                            <span className='ratings'>{i.overallScore}/10</span>
                        </h3>
                        <div className='comments--rating'>
                            <Rating name="Originality" rating={i.originalityScore}/>
                            <Rating name="Significance" rating={i.significanceScore}/>
                            <Rating name="Methodology" rating={i.methodologyScore}/>
                            <Rating name="Clarity And Organization" rating={i.clarityAndOrganizationScore}/>
                            <Rating name="Literature Review" rating={i.literatureReviewScore}/>
                            <Rating name="Results And Discussion" rating={i.resultsAndDiscussionScore}/>
                            <Rating name="References And Citations" rating={i.referencesAndCitationsScore}/>
                            <Rating name="Ethical Considerations" rating={i.ethicalConsiderationsScore}/>
                        </div>
                        <p className='Review'>{i.comments}</p>
                        <div className='tag'>{i.reviewerDecision}</div>
                        </div>}
                    </div>
                ))
            );
        }
    },[paper,assignedReviewer])

    const assign = async () => {
        try {
            const response = axios.put(`http://localhost:8080/api/paper/addReviewer/${id}?newReviewerName=${assignedReviewer}`);
            console.log(response.data);
            const response2 = await axios.get(`http://localhost:8080/api/paper/${id}`);
            setPaper(response2.data);
        } catch (error) {
            console.error('Error accepting paper:', error);
          }
    }
    React.useEffect(() => {
        if(assignedReviewer){
            assign();
        }
    },[assignedReviewer])

    

    const acceptPaper = async () => {
        try {
          const response = await axios.put(`http://localhost:8080/api/paper/updateDecision/${id}?newDecision=Accept`);
          console.log(response.data);
          navigate("/Dashboard");
        } catch (error) {
          console.error('Error accepting paper:', error);
        }
    };

    const rejectPaper = async () => {
        try {
          const response = await axios.put(`http://localhost:8080/api/paper/updateDecision/${id}?newDecision=Reject`);
          console.log(response.data);
          navigate("/Dashboard");

        } catch (error) {
          console.error('Error rejecting paper:', error);
        }
    };

    return (
        <>
        <Navbar/>
        <div className='paperPage'>
            <div className="viewPaper">
                {paper && <PDFViewer pdf={paper.pdfFile}/>}
            </div>
            <div className='interactive'>
                <div className='paper--buttons--container'>
                        <button className='paper--buttons accept' onClick={acceptPaper}>Accept</button>
                        <button className='paper--buttons reject'onClick={rejectPaper}>Reject</button>
                        <div className='Assign'>
                            <button className={`paper--buttons ${open?'selected':''}`} onClick={trigger}>Assign Reviewers</button>
                        </div>
                </div>
                

                <div className='d-container'>

                {(open)?(<div className={`dropdown ${open? 'active': 'inactive'}`}>
                    <h3 className='dropdown--title'>Reviewers</h3>
                
                    <div className='option' onClick={() => selectReviewer('Jason Parker')}>
                        <div className='c'>
                            Jason Parker
                            <div className='t'> 
                                <span>Science</span>
                                <span>Technology</span>
                            </div>
                        </div>

                    </div>
                    <div className='option' onClick={() => selectReviewer('Zoe Rodriguez')}>
                        <div className='c'>
                            Zoe Rodriguez
                            <div className='t'> 
                                <span>Art</span>
                                <span>Technology</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('Ryan Harris')}>
                    <div className='c'>
                            Ryan Harris
                            <div className='t'> 
                                <span>Technology</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('Chloe Turner')}>
                        <div className='c'>
                            Chloe Turner
                            <div className='t'> 
                                <span>Science</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('Kevin Hall')}>
                        
                        <div className='c'>
                            Kevin Hall
                            <div className='t'> 
                                <span>Management</span>
                                <span>Technology</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('Lily Moore')}>
                        <div className='c'>
                            Lily Moore
                            <div className='t'> 
                                <span>Management</span>
                                <span>Art</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('David Taylor')}>
                        <div className='c'>
                            David Taylor
                            <div className='t'> 
                                <span>Management</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('Sarah Walker')}>
                        <div className='c'>
                            Sarah Walker
                            <div className='t'> 
                                <span>Art</span>
                            </div>
                        </div>
                    </div>
                    <div className='option' onClick={() => selectReviewer('Victoria Wright')}>
                        <div className='c'>
                            Victoria Wright
                            <div className='t'> 
                                <span>Management</span>
                                <span>Science</span>
                            </div>
                        </div>
                    </div>
            </div>) : <div/>}
                </div>

                <div className='Comments--container'>
                    <h2 id="comment--title">Comments</h2>
                    {paper && commentsArray && commentsArray.length===0 ? <div className='text-div'>No Reviewers Assigned Yet</div> : commentsArray}
                </div>

            </div>
        </div>
 
        </>
    )
}

export default Paper;