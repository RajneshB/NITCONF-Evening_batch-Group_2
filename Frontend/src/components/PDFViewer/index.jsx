/* eslint-disable no-unused-vars */
import { useState } from 'react'
import './index.scss'
import {Viewer,Worker} from '@react-pdf-viewer/core'
import {defaultLayoutPlugin,ToolbarProps, ToolbarSlot} from '@react-pdf-viewer/default-layout'
import '@react-pdf-viewer/core/lib/styles/index.css'
import '@react-pdf-viewer/default-layout/lib/styles/index.css'
import testpdf from '../../assets/Assignment2.pdf'
import { scrollModePlugin } from '@react-pdf-viewer/scroll-mode'
import { ScrollMode } from '@react-pdf-viewer/core';
import { pageNavigationPlugin, NextIcon, PreviousIcon } from '@react-pdf-viewer/page-navigation';
import '@react-pdf-viewer/page-navigation/lib/styles/index.css';
import { GoToPreviousPage, GoToNextPage, RenderGoToPageProps } from '@react-pdf-viewer/page-navigation';
import PropTypes from 'prop-types';


const PDFViewer = () => {
    const [pdfFIle,setpdfFile]=useState(null);
    const [viewPdf,setViewPdf]=useState(null);
    const fileType=['application/pdf']
    const scrollModePluginInstance = scrollModePlugin();
    const pageNavigationPluginInstance = pageNavigationPlugin();

    scrollModePluginInstance.switchScrollMode(ScrollMode.Page);
    const { GoToFirstPage, GoToLastPage, GoToNextPage, GoToPreviousPage } = pageNavigationPluginInstance;

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

    const renderToolbar = (Toolbar) => (
        <Toolbar>
            {(slots) => {
                const {
                    CurrentPageInput,
                    Download,
                    EnterFullScreen,
                    GoToNextPage,
                    GoToPreviousPage,
                    NumberOfPages,
                    ShowSearchPopover,
                    Zoom,
                    ZoomIn,
                    ZoomOut,
                } = slots;
                return (
                    <div
                        style={{
                            alignItems: 'center',
                            display: 'flex',
                            width: '100%',
                            background: 'black',
                            height: '5em',
                            color: 'white',
                        }}
                    >
                        <div style={{ padding: '0px 25px' }}>
                            <ShowSearchPopover />
                        </div>
                        <div style={{ padding: '0px 5px' }}>
                            <ZoomOut />
                        </div>
                        <div style={{ padding: '0px 5px' }}>
                            <Zoom />
                        </div>
                        <div style={{ padding: '0px 5px' }}>
                            <ZoomIn />
                        </div>
                        <div style={{ padding: '0px 5px', marginLeft: '15rem' }}>
                        <GoToPreviousPage>
                                {(props) => (
                                    <button
                                        style={{
                                            backgroundColor: props.isDisabled ? 'black' : 'gray',
                                            border: 'none',
                                            borderRadius: '4px',
                                            color: 'black',
                                            cursor: props.isDisabled ? 'not-allowed' : 'pointer',
                                            padding: '8px',
                                        }}
                                        disabled={props.isDisabled}
                                        onClick={props.onClick}
                                    >
                                        Prev
                                    </button>
                                )}
                            </GoToPreviousPage>
                        </div>
                        <div style={{ padding: '0px 5px', width: '4rem' }}>
                            <CurrentPageInput />
                        </div>
                        <div style={{ padding: '0px 5px', color: 'white' }}>
                            / <NumberOfPages />
                        </div>
                        <div style={{ padding: '0px 5px' }}>
                            <GoToNextPage>
                                {(props) => (
                                    <button
                                        style={{
                                            backgroundColor: props.isDisabled ? 'black' : 'gray',
                                            border: 'none',
                                            borderRadius: '4px',
                                            color: 'black',
                                            cursor: props.isDisabled ? 'not-allowed' : 'pointer',
                                            padding: '8px',
                                        }}
                                        disabled={props.isDisabled}
                                        onClick={props.onClick}
                                    >
                                        Next
                                    </button>
                                )}
                            </GoToNextPage>
                        </div>
                        <div style={{ padding: '0px 5px', marginLeft: '25rem' }}>
                            <EnterFullScreen />
                        </div>
                        <div style={{ padding: '0px 20px' }}>
                            <Download />
                        </div>
                    </div>
                );
            }}
        </Toolbar>
    );
    
    const defaultLayoutPluginInstance = defaultLayoutPlugin({
        renderToolbar,
        sidebarTabs: (defaultTabs) => [],
    });

    return (
        <div className="pdfContainer">
            <div className='pdfDoc'>
            <Worker workerUrl="https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js">
                    <Viewer theme='dark'  fileUrl={testpdf} plugins={[defaultLayoutPluginInstance,scrollModePluginInstance,pageNavigationPluginInstance]} />
            </Worker>
            </div>
        </div>
    )
}


export default PDFViewer;