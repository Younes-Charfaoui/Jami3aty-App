package com.ibnkhaldoun.studentside.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibnkhaldoun.studentside.fragments.HomeFragment;
import com.ibnkhaldoun.studentside.fragments.MailFragment;
import com.ibnkhaldoun.studentside.fragments.NotificationFragment;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Professor;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private int mNumbersOfTabs;

    public TabLayoutAdapter(FragmentManager fm, int numbersOfTab) {
        super(fm);
        this.mNumbersOfTabs = numbersOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new NotificationFragment();
            case 2:
                List<Mail> list = new ArrayList<>();
                List<Message> messageList = new ArrayList<>();
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                messageList.add(new Message("Meeting", "Hello World", "17,5"));
                list.add(new Mail(new Professor("Ouared", "Aek"), messageList));
                list.add(new Mail(new Professor("Bekki", "Khathir"), messageList));
                list.add(new Mail(new Professor("Chikhaoui", "Ahmed"), messageList));
                list.add(new Mail(new Professor("Aid", "Lahcen"), messageList));
                list.add(new Mail(new Professor("Dahmani", "Youcef"), messageList));
                list.add(new Mail(new Professor("Boudaa", "Boujemaa"), messageList));
                list.add(new Mail(new Professor("Baghani", "Abdelmalek"), messageList));
                return MailFragment.newInstance(list);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumbersOfTabs;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
