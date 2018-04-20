package com.ibnkhaldoun.studentside.providers;


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


        markList.add(new Mark(1, "CD", "Compiler Design", 15f, 15, 15, "Benghani Abdelmalk", "Benghani Abdelmalk", "Daoud Amine"));
        markList.add(new Mark(2, "LP", "Linear Programming", 20, 19, -1, "Chikhawi ahmed", "Chikhawi ahmed", null));
        markList.add(new Mark(3, "OS", "Operating System", 18.5f, 19, 18, "Bekki khathir", "Bekki khathir", "Bekki khathir"));
        markList.add(new Mark(4, "LP", "Logical Programming", 19f, -1, 18, "Aid Lahcen", null, "Aid Lahcen"));
        markList.add(new Mark(5, "SE", "Software Engineering", 12f, 15.5f, 16.5f, "Zohra", "Zohra", "Zohra"));
        markList.add(new Mark(6, "IHM", "IHM", 14f, -1, 17, "Benouda Habib", "Benouda Habib", "Benouda Habib"));
        markList.add(new Mark(7, "PB", "Probability", 20f, 19, -1, "Mbarka", "Mbarka", "Mbarka"));
        markList.add(new Mark(8, "EN", "English", 16f, -1, -1, "Lakmech", null, null));

        studentList.add(new Student("Younes", "Charfaoui"));
        studentList.add(new Student("Ismail", "Bourbie"));
        studentList.add(new Student("Taif", "Amine"));
        studentList.add(new Student("Redouane", "Baya"));
        studentList.add(new Student("Hami", "Doulami"));



        commentList.add(new Comment(studentList.get(0).getFullName(), "You missed", "1 mar"));
        commentList.add(new Comment(studentList.get(1).getFullName(), "alright then", "1 apr"));
        commentList.add(new Comment(studentList.get(2).getFullName(), "come one", "12 Jan"));
        commentList.add(new Comment(studentList.get(3).getFullName(), "Hello from the other", "16 apr"));
        commentList.add(new Comment(professorList.get(4).getFullName(), "sure you can not do that", "16 apr"));
        commentList.add(new Comment(studentList.get(4).getFullName(), "no , it's not true", "14 feb"));
        commentList.add(new Comment(professorList.get(6).getFullName(), "try to keep it secret", "8 apr"));
        commentList.add(new Comment(studentList.get(1).getFullName(), "Hello world", "13 apr"));
        commentList.add(new Comment(professorList.get(8).getFullName(), "try to do something else", "19 apr"));

        mailList.add(new Mail(professorList.get(0), messageList));
        mailList.add(new Mail(professorList.get(1), messageList));
        mailList.add(new Mail(professorList.get(2), messageList));
        mailList.add(new Mail(professorList.get(3), messageList));
        mailList.add(new Mail(professorList.get(4), messageList));
        mailList.add(new Mail(professorList.get(5), messageList));
        mailList.add(new Mail(professorList.get(6), messageList));
        mailList.add(new Mail(professorList.get(7), messageList));
        mailList.add(new Mail(professorList.get(8), messageList));

        displayList.add(new Display(1, "19 Jan 16:31", professorList.get(0).getFullName(), "Lorem ipsum dolor sit amet, ", "1", "Crypto", 1));
        displayList.add(new Display(1, "17 Feb 15:30", professorList.get(1).getFullName(), "Lorem ipsum dolor sit amet,  eleifend viverra porta a, dapibus ut felis.", "1", "Crypto", 1));
        displayList.add(new Display(1, "13 Mar 11:39", professorList.get(2).getFullName(), "Lorem ipsum dolor sit amet,  Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "1", "Crypto", 1));
        displayList.add(new Display(1, "16 Apr 16:00", professorList.get(3).getFullName(), "Lorem ipsum dolor sit amet, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "1", "Crypto", 1));
        displayList.add(new Display(1, "03 Mai 16:30", professorList.get(4).getFullName(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum . Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "1", "Crypto", 1));
        displayList.add(new Display(1, "18 Jun 16:10", professorList.get(5).getFullName(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor ligula varius. Maecenas laoreet eros id purus mollis congue. Vivamus interdum condimentum lorem quis porttitor. Mauris tortor mi, eleifend viverra porta a, dapibus ut felis.", "1", "Crypto", 1));
        displayList.add(new Display(1, "22 Jul 16:00", professorList.get(6).getFullName(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, laoreet eros id purus mollis congue. ", "1", "Crypto", 1));
        displayList.add(new Display(1, "10 Aug 02:20", professorList.get(7).getFullName(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam imperdiet ante eu diam iaculis, ut tempor l", "1", "Crypto", 1));
        displayList.add(new Display(1, "11 Sep 04:30", professorList.get(8).getFullName(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu mi vitae ante sodales sollicitudin. Phasellus dignissim laoreet ipsum, facilisis lacinia purus hendrerit vitae. Vestibulum et interdum velit. Nam et nulla vitae mauris malesuada commodo. Quisque vulputate vitae dui eget pellentesque. Nam ac dolor porttitor, ullamcorper orci non, mollis turpis. Etiam iut felis.", "1", "Crypto", 1));

        notificationList.add(new Notification(professorList.get(0).getFullName(), "Just now", "Has published the note of the OS", 1, 0, true));
        notificationList.add(new Notification(professorList.get(1).getFullName(), "Just now", "Has published and avis of exam", 2, 0, true));
        notificationList.add(new Notification(professorList.get(3).getFullName(), "6 min Ago", "Has published the note of the OS", 1, 0, true));
        notificationList.add(new Notification(professorList.get(4).getFullName(), "30 min Ago", "Has published and avis of exam", 2, 0, true));
        notificationList.add(new Notification(professorList.get(2).getFullName(), "5 min Ago", "Has published the note of the OS", 1, 0, true));
        notificationList.add(new Notification(professorList.get(0).getFullName(), "15 min Ago", "Has publihed a course", 3, 0, true));
        notificationList.add(new Notification(professorList.get(0).getFullName(), "51 min Ago", "Has published and avis of exam", 1, 0, true));
        notificationList.add(new Notification(professorList.get(0).getFullName(), "50 min Ago", "Has published the note of the OS", 2, 0, true));
        notificationList.add(new Notification(professorList.get(0).getFullName(), "51 min Ago", "Has publihed a course", 1, 0, true));
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
