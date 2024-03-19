import './index.scss'
import React, { useState } from 'react'
import PDFViewer from '../../components/PDFViewer';
import Navbar from '../../components/Navbar'
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const Paper = () => {

    const {id} = useParams();
    const [open,setOpen]=useState(false);
    const navigate =useNavigate();
    const [paper,setPaper]= React.useState({});

    
    const [commentsArray,setCommentsArray] = React.useState([]);
    function trigger(){
        setOpen(prevOpen => !prevOpen)
    }
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
                        <h3 className='comment--author'>
                            {i.reviewer}
                            <span className='ratings'>{i.rating}/10</span>
                        </h3>
                        <p className='Review'>{i.comments}</p>
                        <div className='tag'>{i.reviewerDecision}</div>
                    </div>
                ))
            );
        }
    },[paper])

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
                            {(open)?(<div className={`dropdown ${open? 'active': 'inactive'}`}>
                                <h3 className='dropdown--title'>Reviewers</h3>
                              
                                    <div className='option' onClick={trigger}>Jason Parker</div>
                                    <div className='option' onClick={trigger}>Zoe Rodriguez</div>
                                    <div className='option' onClick={trigger}>Ryan Harris</div>
                                    <div className='option' onClick={trigger}>Chloe Turner</div>
                                    <div className='option' onClick={trigger}>Kevin Hall</div>
                                    <div className='option' onClick={trigger}>Lily Moore</div>
                                    <div className='option' onClick={trigger}>David Taylor</div>
                                    <div className='option' onClick={trigger}>Sarah Walker</div>
                                    <div className='option' onClick={trigger}>Victoria Wright</div>


                    

                            </div>) : <div/>}
                        </div>


                </div>
 

                <div className='Comments--container'>
                    <h2 id="comment--title">Comments</h2>
                    {paper && paper.status==="Unreviewed"?<div className='text-div'>No comments Yet</div>:commentsArray}
                </div>

            </div>
        </div>
 
        </>
    )
}

export default Paper;