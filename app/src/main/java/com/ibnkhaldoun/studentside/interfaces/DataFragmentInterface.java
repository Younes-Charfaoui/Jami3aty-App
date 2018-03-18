package com.ibnkhaldoun.studentside.interfaces;


import android.support.v4.app.Fragment;

import com.ibnkhaldoun.studentside.fragments.BaseMainFragment;
import com.ibnkhaldoun.studentside.fragments.DisplaysFragment;
import com.ibnkhaldoun.studentside.fragments.MailFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;
import com.ibnkhaldoun.studentside.models.Notification;

public interface DataFragmentInterface {

    void onNeedData(DisplaysFragment displaysFragment);

    void onNeedData(MailFragment mailFragment);

    void onNeedData(NotificationFragment notificationFragment);

    void onAttach(DisplaysFragment displaysFragment);

    void onAttach(MailFragment mailFragment);

    void onAttach(NotificationFragment notificationFragment);
}
