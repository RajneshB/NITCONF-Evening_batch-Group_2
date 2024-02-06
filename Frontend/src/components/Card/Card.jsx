import React from "react";
import './Card.scss';
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar,faFilePdf } from '@fortawesome/free-solid-svg-icons';
import { useNavigate} from "react-router-dom";


const Card = ({id,paperName,rating,authorName,tags}) => {

    const [showAllTags, setShowAllTags] = useState((tags.length>2?false:true));

    const displayedTags = showAllTags ? tags : tags.slice(0, 2);
    const navigate=useNavigate();

    function handleRedirect(){
        navigate(`/paper/${id}`,{replace:true});
    }
    
    return (
        <div className='dashboard-card'>
            <div className="icon-container">
                <FontAwesomeIcon icon={faFilePdf} color='red' className="dashboard-icon"></FontAwesomeIcon>
            </div>
            <div className='dashboard-card-info'>    
                <h1>{paperName}</h1>
                <div className='rating-container'>
                    <FontAwesomeIcon icon={faStar} color='yellow'></FontAwesomeIcon>
                    <p>{rating?rating:"NA"}</p>
                </div>
                <h2>{authorName}</h2>
                <div className="tag-container">
                    {displayedTags.map((tag, index) => (
                        <span key={index}>{tag}</span>
                    ))}
                    {tags.length>2 && (showAllTags?
                        <div className="excess-button" onClick={() => setShowAllTags(false)}>Show less</div>:
                        <div className="excess-button" onClick={() => setShowAllTags(true)}>Show more</div>)
                    }
                </div>
                <div className="view-button" onClick={handleRedirect}>View Paper</div>
            </div>
        </div>  
    )
}

export default Card;


