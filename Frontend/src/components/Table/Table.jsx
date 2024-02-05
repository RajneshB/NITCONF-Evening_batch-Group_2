import React from "react";
import './Table.scss';
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar,faFilePdf } from '@fortawesome/free-solid-svg-icons';


const Table = ({id,paperName,rating,authorName,tags}) => {

    const [showAllTags, setShowAllTags] = useState((tags.length>2?false:true));

    const displayedTags = showAllTags ? tags : tags.slice(0, 2);
    
    return (
        <div className='dashboard-table'>
            <div className="table-icon-container">
                <FontAwesomeIcon icon={faFilePdf} color='red' className="dashboard-table-icon"></FontAwesomeIcon>
            </div>
            <div className='dashboard-table-info'>
                <div className="table-h1">
                    <h1>{paperName}</h1>
                </div>
                <div className='table-rating-container'>
                    <p>{rating}</p>
                </div>
                <div className="table-h2">
                    <h2>{authorName}</h2>
                </div>
                <div className="table-tag-container">
                    {displayedTags.map((tag, index) => (
                        <span key={index}>{tag}</span>
                    ))}
                    {tags.length>2 && (showAllTags?
                        <div className="excess-button" onClick={() => setShowAllTags(false)}>Show less</div>:
                        <div className="excess-button" onClick={() => setShowAllTags(true)}>Show more</div>)
                    }
                </div>
            </div>
        </div>  
    )
}

export default Table;


