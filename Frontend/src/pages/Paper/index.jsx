import './index.scss'
import React from 'react'
import PDFViewer from '../../components/PDFViewer';
import Navbar from '../../components/Navbar'
import { useParams } from 'react-router-dom';
import axios from 'axios';

const Paper = () => {

    const {id} = useParams();

    
    const [paper,setPaper]= React.useState({});

    
    const [commentsArray,setCommentsArray] = React.useState([]);
    
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
        } catch (error) {
          console.error('Error accepting paper:', error);
        }
    };

    const rejectPaper = async () => {
        try {
          const response = await axios.put(`http://localhost:8080/api/paper/updateDecision/${id}?newDecision=Reject`);
          console.log(response.data);
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
                        <button className='paper--buttons'>Assign Reviewers</button>

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