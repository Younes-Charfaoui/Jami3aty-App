package com.ibnkhaldoun.studentside.interfaces;


import com.ibnkhaldoun.studentside.models.Mark;

public interface MarkFragmentSetting {

    void OnMarksFinished(Mark mark);

    void OnMarksChanged(Mark mark);
}
