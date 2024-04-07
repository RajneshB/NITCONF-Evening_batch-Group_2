/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */
import React from 'react'
import './Rating.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar as solidStar } from '@fortawesome/free-solid-svg-icons';

const Rating=({name,rating})=>{
    const renderStars = () => {
        const stars = [];
        const solidStarsCount = Math.floor(rating); 
        const emptyStarsCount = 5 - solidStarsCount; 

        for (let i = 0; i < solidStarsCount; i++) {
            stars.push(
                <FontAwesomeIcon key={i} icon={solidStar} style={{ color: "#FFD43B" }} />
            );
        }

        // Render empty stars for the remaining
        for (let i = 0; i < emptyStarsCount; i++) {
            stars.push(
                <FontAwesomeIcon key={solidStarsCount + i} icon={solidStar} style={{ color: "#9a9996" }} />
            );
        }

        return stars;
    };
    return(
        <div className="rating--container">
            <span>{name}</span>
            <div className="stars-container">{renderStars()}</div>
        </div>
    )

}
export default Rating;