import './index.scss'
import PDFViewer from '../../components/PDFViewer';
import Navbar from '../../components/Navbar'

const Paper = () => {
    return (
        <>
        <Navbar/>
        <div className='paperPage'>
            <div className="viewPaper">
                <PDFViewer/>
            </div>
            <div className='interactive'>
                <div className='paper--buttons--container'>
                        <button className='paper--buttons'>Accept</button>
                        <button className='paper--buttons'>Reject</button>
                        <button className='paper--buttons'>Assign Reviewers</button>

                </div>
                <div className='Comments--container'>
                    <h2 id="comment--title">Comments</h2>
                    <div className='comments'>
                        <h3 className='comment--author'>@User69 <span className='ratings'>8.3/10</span></h3>
                        <p className='Review'>This is to inform you that this paper is very well done and I have accepted this paper .This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper</p>
                        <div className='tag'>Accepted</div>
                    </div>
                    <div className='comments'>
                        <h3 className='comment--author'>@User69 <span className='ratings'>8.3/10</span></h3>
                        <p className='Review'>This is to inform you that this paper is very well done and I have accepted this paper .This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper</p>
                        <div className='tag'>Rejected</div>
                    </div>
                    <div className='comments'>
                        <h3 className='comment--author'>@User69 <span className='ratings'>8.3/10</span></h3>
                        <p className='Review'>This is to inform you that this paper is very well done and I have accepted this paper .This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper</p>
                        <div className='tag'>Rejected</div>
                    </div>
                    <div className='comments'>
                        <h3 className='comment--author'>@User69 <span className='ratings'>8.3/10</span></h3>
                        <p className='Review'>This is to inform you that this paper is very well done and I have accepted this paper .This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper.This is to inform you that this paper is very well done and I have accepted this paper</p>
                        <div className='tag'>Accepted</div>
                    </div>
                    
                    
                </div>

            </div>
        </div>
 
        </>
    )
}

export default Paper;