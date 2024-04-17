import React from 'react';
import { useParams } from 'react-router-dom';
import "./css/ShopPage.css"
import ShopReviews from "./components/shop/ShopReviews";
import ShopInfo from "./components/shop/ShopInfo";
import Bookings from "./components/shop/ShopBooking";


function ShopPage() {
  const { id } = useParams(); // 동적인 값을 받아옴
  return (
      <div>
        <ShopInfo shopId={id}></ShopInfo>
        <Bookings shopId={id}></Bookings>
        <ShopReviews shopId={id}></ShopReviews>
      </div>
  );
}

export default ShopPage;
