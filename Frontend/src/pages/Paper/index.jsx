import './index.scss'
import PDFViewer from '../../components/PDFViewer';
import Navbar from '../../components/Navbar'

const Paper = () => {
    return (
        <>
        <Navbar/>
        <div>
            <div className="viewPaper">
                <PDFViewer/>
            </div>
        </div>
        </>
    )
}

export default Paper;