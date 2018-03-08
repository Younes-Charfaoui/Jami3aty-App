package com.ibnkhaldoun.studentside.data_providers;


import com.ibnkhaldoun.studentside.enums.ClassTypes;
import com.ibnkhaldoun.studentside.enums.PostTypes;
import com.ibnkhaldoun.studentside.models.Comment;
import com.ibnkhaldoun.studentside.models.Display;
import com.ibnkhaldoun.studentside.models.Mail;
import com.ibnkhaldoun.studentside.models.Mark;
import com.ibnkhaldoun.studentside.models.Message;
import com.ibnkhaldoun.studentside.models.Notification;
import com.ibnkhaldoun.studentside.models.Professor;
import com.ibnkhaldoun.studentside.models.ScheduleItem;
import com.ibnkhaldoun.studentside.models.Student;

import java.util.ArrayList;
import java.util.List;

public final class DataProviders {
    private static final List<Mail> mailList = new ArrayList<>();
    private static final List<Message> messageList = new ArrayList<>();
    private static final List<Professor> professorList = new ArrayList<>();
    private static final List<Mark> markList = new ArrayList<>();
    private static final List<Display> displayList = new ArrayList<>();
    private static final List<Notification> notificationList = new ArrayList<>();
    private static final List<Comment> commentList = new ArrayList<>();
    private static final List<Student> studentList = new ArrayList<>();

    private static final List<ScheduleItem> sundayList = new ArrayList<>();
    private static final List<ScheduleItem> mondayList = new ArrayList<>();
    private static final List<ScheduleItem> tuesdayList = new ArrayList<>();
    private static final List<ScheduleItem> wednesdayList = new ArrayList<>();
    private static final List<ScheduleItem> thursdayList = new ArrayList<>();

    static {

        professorList.add(new Professor("Phd.", "Ouared", "Aek"));
        professorList.add(new Professor("Dr.", "Bekki", "Khathir"));
        professorList.add(new Professor("Pr.", "Chikhaoui", "Ahmed"));
        professorList.add(new Professor("Dr.", "Aid", "Lahcen"));
        professorList.add(new Professor("Pr.", "Dahmani", "Youcef"));
        professorList.add(new Professor("Dr.", "Boudaa", "Boujemaa"));
        professorList.add(new Professor("Dr.", "Baghani", "Abdelmalek"));
        professorList.add(new Professor("Dr.", "Mezzoug", "Karim"));
        professorList.add(new Professor("Dr.", "Siabdelhadi", "Ahmed"));
        professorList.add(new Professor("Dr.", "Benoudaa", "Habib"));
        sundayList.add(new ScheduleItem("8:00", "Amphis C", "Compilation", ClassTypes.COURSE, professorList.get(6)));
        sundayList.add(new ScheduleItem("9:30", "Amphis B", "Operating System", ClassTypes.COURSE, professorList.get(1)));
        sundayList.add(new ScheduleItem("11:00", "Class 14", "Operating System", ClassTypes.TD, professorList.get(1)));
        sundayList.add(new ScheduleItem("14:00", "Amphis C", "Compilation", ClassTypes.TD, professorList.get(6)));
        sundayList.add(new ScheduleItem("15:30", "Labo 4", "IHM", ClassTypes.TP, professorList.get(9)));

        mondayList.add(new ScheduleItem("9:30", "Class 7", "Linear Programming", ClassTypes.TD, professorList.get(2)));
        mondayList.add(new ScheduleItem("11:00", "Labo 3", "Compilation", ClassTypes.TP, professorList.get(6)));
        mondayList.add(new ScheduleItem("15:30", "Labo 2", "Logical programming", ClassTypes.TP, professorList.get(3)));

        tuesdayList.add(new ScheduleItem("8:00", "Amphi A", "IHM", ClassTypes.COURSE, professorList.get(9)));
        tuesdayList.add(new ScheduleItem("9:30", "Amphi B", "Logical programming", ClassTypes.COURSE, professorList.get(3)));
        tuesdayList.add(new ScheduleItem("11:00", "Amphi B", "Linear programming", ClassTypes.COURSE, professorList.get(2)));

        wednesdayList.add(new ScheduleItem("9:30", "Class 12", "Software Engineering", ClassTypes.TD, professorList.get(2)));
        wednesdayList.add(new ScheduleItem("11:00", "Amphi C", "Compilation", ClassTypes.COURSE, professorList.get(6)));
        wednesdayList.add(new ScheduleItem("14:00", "Labo 3", "Operating System", ClassTypes.TP, professorList.get(5)));

        thursdayList.add(new ScheduleItem("8:00", "Amphi A", "Probability", ClassTypes.COURSE, professorList.get(7)));
        thursdayList.add(new ScheduleItem("9:00", "Class 14", "Probability", ClassTypes.TD, professorList.get(7)));


        markList.add(new Mark("CD", "Compiler Design", 15f, 15, 15));
        markList.add(new Mark("LP", "Linear Programming", 20, 19, -1));
        markList.add(new Mark("OS", "Operating System", 18.5f, 19, 18));
        markList.add(new Mark("LP", "Logical Programming", 19f, -1, 18));
        markList.add(new Mark("SE", "Software Engineering", 12f, 15.5f, 16.5f));
        markList.add(new Mark("IHM", "IHM", 14f, -1, 17));
        markList.add(new Mark("PB", "Probability", 20f, 19, -1));
        markList.add(new Mark("EN", "English", 16f, -1, -1));

        studentList.add(new Student("Younes", "Charfaoui"));
        studentList.add(new Student("Ismail", "Bourbie"));
        studentList.add(new Student("Taif", "Amine"));
        studentList.add(new Student("Redouane", "Baya"));
        studentList.add(new Student("Hami", "Doulami"));


        messageList.add(new Message("Meeting", "Hello Sir , we need a meeting", "Jan 26", new Student("Younes", "Charfaoui"), professorList.get(0)));
        messageList.add(new Message("Thanks", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "Jan 26", professorList.get(0), new Student("Younes", "Charfaoui")));
        messageList.add(new Message("Asking", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "Jan 26", professorList.get(0), new Student("Younes", "Charfaoui")));
        messageList.add(new Message("Noting", "dNam suscipit dui at lobortis porttitor. Donec semper, lectus ut congue vehicula, metus erat commodo justo, id iaculis ipsum mi ac elit. Maecenas consequat nisl sed leo ultrices, nec malesuada quam sollicitudin. Suspendisse ultricies augue eu massa hendrerit, at vulputate orci malesuada. Cras venenatis, tortor et semper hendrerit, quam justo tincidunt mi, at tempus ante purus non orci. Fusce ultricies blandit lectus, quis ornare enim blandit at. Pellentesque porta, nisl id ornare auctor, est risus maximus nunc, quis accumsan est lacus et erat. Suspendisse ex ipsum, pellentesque sit amet condimentum non, cursus sit amet nibh. Suspendisse potenti. In in enim enim. Maecenas et mollis lorem, semper imperdiet ligula. Praesent lobortis, dolor id cursus varius, dui sapien dignissim sem, a viverra enim odio vitae purus.", "Feb 16", new Student("Younes", "Charfaoui"), professorList.get(0)));
        messageList.add(new Message("Helping", "Donec sit amet erat metus. Quisque id ipsum nunc. Donec rutrum nisi non orci volutpat egestas. Etiam condimentum in magna nec posuere. Integer at libero luctus, volutpat ipsum imperdiet, faucibus arcu. Aliquam hendrerit tellus ac laoreet bibendum. Nullam posuere libero enim, et pellentesque purus mollis nec. Proin fringilla nec neque at aliquet. In feugiat accumsan nisl. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec sodales metus purus, nec iaculis tellus pellentesque quis.", "Jan 26", new Student("Younes", "Charfaoui"), professorList.get(0)));
        messageList.add(new Message("Consulting", "Phasellus tincidunt orci quam, quis mattis dolor pharetra a. Nam dui lectus, facilisis vel pharetra quis, elementum non augue. Proin nec sodales risus. Nunc facilisis metus dui, a interdum ligula sagittis non. Curabitur vel nibh in nisi fringilla gravida eget nec urna. Vestibulum posuere, diam sed ullamcorper aliquet, ante metus malesuada mi, eget tempus ex tortor a libero. Fusce erat arcu, varius ut sodales consequat, fringilla quis sapien. Sed mattis pellentesque bibendum. Donec quis ornare sem, in blandit magna. Morbi nulla arcu, porttitor vitae ipsum nec, dictum sodales nunc. Donec a tristique turpis, posuere scelerisque leo. Aenean cursus enim arcu, et porttitor augue scelerisque nec. Vivamus at urna vel dui ultrices vehicula ut non tellus. Praesent scelerisque justo id est semper luctus", "Feb 15", new Student("Younes", "Charfaoui"), professorList.get(0)));
        messageList.add(new Message("Do the work", "Hello World for all the reason", "Jan 26", new Student("Younes", "Charfaoui"), professorList.get(0)));
        messageList.add(new Message("A request please", "Hello World every body is here", "Jan 13", new Student("Younes", "Charfaoui"), professorList.get(0)));

        commentList.add(new Comment(studentList.get(0), "You missed", "1 mar"));
        commentList.add(new Comment(studentList.get(1), "alright then", "1 apr"));
        commentList.add(new Comment(studentList.get(2), "come one", "12 Jan"));
        commentList.add(new Comment(studentList.get(3), "Hello from the other", "16 apr"));
        commentList.add(new Comment(professorList.get(4), "sure you can not do that", "16 apr"));
        commentList.add(new Comment(studentList.get(4), "no , it's not true", "14 feb"));
        commentList.add(new Comment(professorList.get(6), "try to keep it secret", "8 apr"));
        commentList.add(new Comment(studentList.get(1), "Hello world", "13 apr"));
        commentList.add(new Comment(professorList.get(8), "try to do something else", "19 apr"));

        mailList.add(new Mail(professorList.get(0), messageList));
        mailList.add(new Mail(professorList.get(1), messageList));
        mailList.add(new Mail(professorList.get(2), messageList));
        mailList.add(new Mail(professorList.get(3), messageList));
        mailList.add(new Mail(professorList.get(4), messageList));
        mailList.add(new Mail(professorList.get(5), messageList));
        mailList.add(new Mail(professorList.get(6), messageList));
        mailList.add(new Mail(professorList.get(7), messageList));
        mailList.add(new Mail(professorList.get(8), messageList));

        displayList.add(new Display(1, professorList.get(0), "19 Jan 16:31", "Lorem ipsum dolor sit amet, ", commentList));
        displayList.add(new Display(1, professorList.get(1), "17 Feb 15:30", "Lorem ipsum dolor sit amet,  eleifend viverra porta a, dapibus ut felis.", commentList));
        displayList.add(new Display(1, professorList.get(2), "13 Mar 11:39", "Lorem ipsum dolor sit amet,  Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", commentList));
        displayList.add(new Display(1, professorList.get(3), "16 Apr 16:00", "Lorem ipsum dolor sit amet, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", commentList));
        displayList.add(new Display(1, professorList.get(4), "03 Mai 16:30", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum . Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", commentList));
        displayList.add(new Display(1, professorList.get(5), "18 Jun 16:10", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", commentList));
        displayList.add(new Display(1, professorList.get(6), "22 Jul 16:00", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, laoreet eros id purus mollis congue. ", commentList));
        displayList.add(new Display(1, professorList.get(7), "10 Aug 02:20", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor l", commentList));
        displayList.add(new Display(1, professorList.get(8), "11 Sep 04:30", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam iut felis.", commentList));

        notificationList.add(new Notification(professorList.get(0), "Just now", "Has published the note of the OS", PostTypes.CONSULTATION));
        notificationList.add(new Notification(professorList.get(1), "Just now", "Has published and avis of exam", PostTypes.AVIS));
        notificationList.add(new Notification(professorList.get(3), "6 min Ago", "Has published the note of the OS", PostTypes.CONSULTATION));
        notificationList.add(new Notification(professorList.get(4), "30 min Ago", "Has published and avis of exam", PostTypes.AVIS));
        notificationList.add(new Notification(professorList.get(2), "5 min Ago", "Has published the note of the OS", PostTypes.MARKS));
        notificationList.add(new Notification(professorList.get(0), "15 min Ago", "Has publihed a course", PostTypes.CONSULTATION));
        notificationList.add(new Notification(professorList.get(0), "51 min Ago", "Has published and avis of exam", PostTypes.MARKS));
        notificationList.add(new Notification(professorList.get(0), "50 min Ago", "Has published the note of the OS", PostTypes.AVIS));
        notificationList.add(new Notification(professorList.get(0), "51 min Ago", "Has publihed a course", PostTypes.MARKS));
    }

    public static List<Comment> getCommentList() {
        return commentList;
    }

    public static List<Student> getStudentList() {
        return studentList;
    }

    public static List<ScheduleItem> getSundayList() {
        return sundayList;
    }

    public static List<ScheduleItem> getMondayList() {
        return mondayList;
    }

    public static List<ScheduleItem> getTuesdayList() {
        return tuesdayList;
    }

    public static List<ScheduleItem> getWednesdayList() {
        return wednesdayList;
    }

    public static List<ScheduleItem> getThursdayList() {
        return thursdayList;
    }

    public static List<Notification> getNotificationList() {
        return notificationList;
    }

    public static List<Display> getDisplayList() {
        return displayList;
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
