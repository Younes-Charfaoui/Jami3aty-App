package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MessageFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;

public interface IDataFragment {

    void onNeedData(DisplaysFragment displaysFragment);

    void onNeedData(MessageFragment messageFragment);

    void onNeedData(NotificationFragment notificationFragment);

    void onAttach(DisplaysFragment displaysFragment);

    void onAttach(MessageFragment messageFragment);

    void onAttach(NotificationFragment notificationFragment);
}
