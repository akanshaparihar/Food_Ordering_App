import React from 'react';
import FoodCategories from '../components/FoodCategories';
import Navbar from '../components/Navbar';

const WelcomePage = () =>{
    return(
        <div className="blurry-background">
            <Navbar/>
            <FoodCategories />
        </div>

    );
};

export default WelcomePage;