package com.app.sample.insta.model;

import java.util.List;

/**
 *  https://www.youtube.com/watch?v=9rJf0ZzcjAs
 */
public class AbstractAPIListener implements APIListener
{
    @Override
    public void onLogin(User user) { }

    @Override
    public void onOrdersLoaded(List<Order> order) {    }

    @Override
    public void ScanQR(int orderID) {

    }


}
