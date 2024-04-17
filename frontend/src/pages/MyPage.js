import React from 'react';
import "./css/MyPage.css"

import MyBookings from "./components/myPage/MyBookings";
import MyReviews from "./components/myPage/MyReviews";
import MyFavorites from "./components/myPage/MyFavorites";

function MyPage() {
  return(
      <div>
        <MyBookings></MyBookings>
        <MyFavorites></MyFavorites>
        <MyReviews></MyReviews>
      </div>
  );
}

export default MyPage
