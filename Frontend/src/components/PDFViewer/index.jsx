import { useState } from 'react'
import './index.scss'
import {Viewer,Worker} from '@react-pdf-viewer/core'
import {defaultLayoutPlugin} from '@react-pdf-viewer/default-layout'
import '@react-pdf-viewer/core/lib/styles/index.css'
import '@react-pdf-viewer/default-layout/lib/styles/index.css'
import testpdf from '../../assets/Assignment2.pdf'

const PDFViewer = () => {
    const [pdfFIle,setpdfFile]=useState(null);
    const [viewPdf,setViewPdf]=useState(null);
    const fileType=['application/pdf']
    const newplugin=defaultLayoutPlugin();

    const handleChange = (e) => {
        let selectedFile = e.target.files[0];
        if(selectedFile && fileType.includes(selectedFile.type)){
            let reader = new FileReader();
            reader.readAsDataURL(selectedFile);
            reader.onload = (e) => {
                setpdfFile(e.target.result);
            }
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if(pdfFIle !== null){
            setViewPdf(pdfFIle);
        }
        else{
            setViewPdf(null);
        }
    }

    return (
        <div className="pdfContainer">
            <Worker workerUrl="https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js">
                    <Viewer fileUrl={testpdf} plugins={[newplugin]} />
            </Worker>
        </div>
    )
}

export default PDFViewer;