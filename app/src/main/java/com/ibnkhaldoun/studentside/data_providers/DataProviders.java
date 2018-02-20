package com.ibnkhaldoun.studentside.data_providers;


import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Professor;

import java.util.ArrayList;
import java.util.List;

public final class DataProviders {

    private static final List<Mail> mailList = new ArrayList<>();
    private static final List<Message> messageList = new ArrayList<>();
    private static final List<Professor> professorList = new ArrayList<>();
    private static final List<Mark> markList = new ArrayList<>();

    static {
        markList.add(new Mark("CD", "Compiler Design", 15f, 15, 15));
        markList.add(new Mark("LP", "Linear Programming", 20, 19, 0));
        markList.add(new Mark("LP", "Logical Programming", 19f, 0, 18));
        markList.add(new Mark("SE", "Software Engineering", 12f, 0, 0));
        markList.add(new Mark("IHM", "IHM", 14f, 0, 17));
        markList.add(new Mark("PB", "Probability", 20f, 19, 0));
        markList.add(new Mark("EN", "English", 16f, 0, 0));

        messageList.add(new Message("Meeting", "Hello Sir , we need a meeting", "17:50"));
        messageList.add(new Message("Thanks", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "14:45"));
        messageList.add(new Message("Asking", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "18:22"));
        messageList.add(new Message("Noting", "dNam suscipit dui at lobortis porttitor. Donec semper, lectus ut congue vehicula, metus erat commodo justo, id iaculis ipsum mi ac elit. Maecenas consequat nisl sed leo ultrices, nec malesuada quam sollicitudin. Suspendisse ultricies augue eu massa hendrerit, at vulputate orci malesuada. Cras venenatis, tortor et semper hendrerit, quam justo tincidunt mi, at tempus ante purus non orci. Fusce ultricies blandit lectus, quis ornare enim blandit at. Pellentesque porta, nisl id ornare auctor, est risus maximus nunc, quis accumsan est lacus et erat. Suspendisse ex ipsum, pellentesque sit amet condimentum non, cursus sit amet nibh. Suspendisse potenti. In in enim enim. Maecenas et mollis lorem, semper imperdiet ligula. Praesent lobortis, dolor id cursus varius, dui sapien dignissim sem, a viverra enim odio vitae purus.", "Feb 16"));
        messageList.add(new Message("Helping", "Donec sit amet erat metus. Quisque id ipsum nunc. Donec rutrum nisi non orci volutpat egestas. Etiam condimentum in magna nec posuere. Integer at libero luctus, volutpat ipsum imperdiet, faucibus arcu. Aliquam hendrerit tellus ac laoreet bibendum. Nullam posuere libero enim, et pellentesque purus mollis nec. Proin fringilla nec neque at aliquet. In feugiat accumsan nisl. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec sodales metus purus, nec iaculis tellus pellentesque quis.", "17,5"));
        messageList.add(new Message("Consulting", "Phasellus tincidunt orci quam, quis mattis dolor pharetra a. Nam dui lectus, facilisis vel pharetra quis, elementum non augue. Proin nec sodales risus. Nunc facilisis metus dui, a interdum ligula sagittis non. Curabitur vel nibh in nisi fringilla gravida eget nec urna. Vestibulum posuere, diam sed ullamcorper aliquet, ante metus malesuada mi, eget tempus ex tortor a libero. Fusce erat arcu, varius ut sodales consequat, fringilla quis sapien. Sed mattis pellentesque bibendum. Donec quis ornare sem, in blandit magna. Morbi nulla arcu, porttitor vitae ipsum nec, dictum sodales nunc. Donec a tristique turpis, posuere scelerisque leo. Aenean cursus enim arcu, et porttitor augue scelerisque nec. Vivamus at urna vel dui ultrices vehicula ut non tellus. Praesent scelerisque justo id est semper luctus", "Feb 15"));
        messageList.add(new Message("Do the work", "Hello World", "Jan 26"));
        messageList.add(new Message("A request please", "Hello World", "Jan 13"));

        professorList.add(new Professor("Ouared", "Aek"));
        professorList.add(new Professor("Bekki", "Khathir"));
        professorList.add(new Professor("Chikhaoui", "Ahmed"));
        professorList.add(new Professor("Aid", "Lahcen"));
        professorList.add(new Professor("Dahmani", "Youcef"));
        professorList.add(new Professor("Boudaa", "Boujemaa"));
        professorList.add(new Professor("Baghani", "Abdelmalek"));
        professorList.add(new Professor("Mezzoug", "Karim"));
        professorList.add(new Professor("Siabdelhadi", "Ahmed"));

        mailList.add(new Mail(new Professor("Ouared", "Aek"), messageList));
        mailList.add(new Mail(new Professor("Bekki", "Khathir"), messageList));
        mailList.add(new Mail(new Professor("Chikhaoui", "Ahmed"), messageList));
        mailList.add(new Mail(new Professor("Aid", "Lahcen"), messageList));
        mailList.add(new Mail(new Professor("Dahmani", "Youcef"), messageList));
        mailList.add(new Mail(new Professor("Boudaa", "Boujemaa"), messageList));
        mailList.add(new Mail(new Professor("Baghani", "Abdelmalek"), messageList));
        mailList.add(new Mail(new Professor("Mezzoug", "Karim"), messageList));
        mailList.add(new Mail(new Professor("Siabdelhadi", "Ahmed"), messageList));
    }

    public static List<Mail> getMailList() {
        return mailList;
    }

    public static List<Message> getMessageList() {
        return messageList;
    }

    public static List<Professor> getProfessorList() {
        return professorList;
    }

    public static List<Mark> getMarkList() {
        return markList;
    }
}
