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
        </div>
        </>
    )
}

export default Paper;