import java.util.List;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

public class ToDo {
    public static void main(String... args) {
        Process process = new Process();
        process.start();
    }
    
}

class Process {
    private String subject,subjectadd,Task;
    private int t;
    private int count = 0;
    private int i = 1;
    private int totaltime = 0;
    private int date;//���t
    private int today = 0;
    private int judge = 0;
    private String operation;//����p
    Info get_value,info;
    Map<Integer,Info> SubjectMap = new HashMap<>();

    public Info makeinfo() {
        Info info = new Info();
        return info;
    }

    public void start() {
        Process process = new Process();
        info = makeinfo();

        process.inittime(info);

        System.out.println("�����ԋL�^�����܂����H");
        Scanner d = new Scanner(System.in);
        date = d.nextInt();

        while(i <= date) {
            if(today != i) {
                info = makeinfo();
                today++;
            }

            System.out.println(today + "����");
            System.out.println("===�����I��ł�������(���p�����œ��͂��Ă�������)===");
            System.out.println("�w�K�Ȗڂ̓���:1");
            System.out.println("�w�K���Ԃ̓���:2");
            System.out.println("�w�K�Ȗڂ̒ǉ�:3");
            System.out.println("�����̊w�K���Ԃ̕\��:4");
            System.out.println("�ǉ��Ȗڂ݂̂̊w�K���Ԃ̓���:5");
            System.out.println("���̓��̋L�^:next");
            System.out.println("�����̊w�K�ȖځE���v�w�K���Ԃ̊m�F:x");
            System.out.println("�V�X�e���̏I��:exit");

            Scanner in = new Scanner(System.in);
            operation = in.nextLine();

            switch(operation) {  
            case "1":
                if(judge == 0) {
                    judge = 1;
                }else {
                    System.out.println("���͍ς݂ł��B�V�����Ȗڂ���͂���ꍇ�̓��j���[��3�������Ă�������");
                    break;
                }
                process.Subinput(i,info);
                break;
            case "2": 
                process.Timeinput(i,info);
                break;
            case "3":
                process.Subinputadd(i,info);
                break;
            case "4":
                process.showtime(info);
                break;
            case "5":
                process.Timeinputadd(i,info);
                break;
            case "x":
                System.out.println(i + "���ڂ̋L�^");
                process.showinfo(info);
                break;
            case "next":
                i++;
                process.nextday();
                break;
            default:
                process.showall(date);
                System.exit(0);
                break;
            }
        }
    }

    //�w�K�Ȗڂ����
    public void Subinput(int date,Info info) {
        i = 0;
        //�Ȗڂ̓���
        while(true) {
            Scanner sub = new Scanner(System.in);
            System.out.println("�Ȗڂ����");
            subject = sub.nextLine();
            if(!subject.equals("�I��")) {
                info.subset(subject,i);
                System.out.println("���͂��ꂽ�ȖځF" + info.subget(i));
                info.countset();
            }else {
                break;
            }
            i++;
        }
        SubjectMap.put(date,info);
        count = info.countget();
    }

    //�ǉ��Ȗڂ����
    public void Subinputadd(int date,Info info) {
        int judge;
        i = count;
        while(true) {
            judge = 0;
            Scanner subadd = new Scanner(System.in);
            System.out.println("�Ȗڂ����");
            subjectadd = subadd.nextLine();

                if(cheakstr(subjectadd,info.showsubget(),count) == 1) {
                    System.out.println("�Ȗڂ��d�����Ă��܂��B�������Ȗږ�����͂��Ă�������");
                    judge = 1;
                }


            if(!subjectadd.equals("�I��")) {
                if(judge == 0) {
                    info.subset(subjectadd,i);
                    System.out.println("���͂��ꂽ�ȖځF" + info.subget(i));
                    info.countset();
                    i++;
                }
            }else {
                break;
            }
        }
        SubjectMap.put(date,info);
    }

    //�w�K���Ԃ����
    public void Timeinput(int date,Info info) {
        if(info.countget() != 0) {
            for(i = 0;i < info.countget();i++) {
                Scanner t = new Scanner(System.in);
                System.out.println(info.subget(i) + "�̊w�K���Ԃ����");
                this.t = t.nextInt();
                info.timeset(i, this.t);
            }
        }else {
            System.out.println("���͂ł���f�[�^������܂���");
        }
        SubjectMap.put(date,info);
    }

    //�ǉ��Ȗڂ̂݊w�K���Ԃ��L�^
    public void Timeinputadd(int date,Info info) {
        if(info.countget() != 0) {
            for(i = count;i < info.countget();i++) {
                Scanner t = new Scanner(System.in);
                System.out.println(info.subget(i) + "�̊w�K���Ԃ����");
                this.t = t.nextInt();
                info.timeset(i, this.t);
            }
        }else {
            System.out.println("���͂ł���f�[�^������܂���");
        }
        SubjectMap.put(date,info);
        count = info.countget();
    }

    //�Ȗږ��̕׋����Ԃ��o��
    public void showtime(Info info) {
        System.out.println("");
        if(info.timeget(0) != Integer.MAX_VALUE) {
            for(i = 0;i < count;i++) {
                if(info.timeget(i) != Integer.MAX_VALUE) {
                    System.out.print(info.subget(i) + " | " + info.timeget(i) + " ����");
                }else {
                    System.out.print(info.subget(i) + " | " + " 0 ����");
                }
                System.out.println("");
            }
        }else{
            System.out.println("�\���ł���f�[�^������܂���");
        }
        System.out.println("");
    }

    //���ׂĂ̏����o��
    public void showinfo(Info info) {
        System.out.println("|==�w�K�Ȗڈꗗ==");
        for(i = 0;i < count;i++) {
            System.out.println("| " + info.subget(i));

            if(info.timeget(i) != Integer.MAX_VALUE) {
                totaltime = totaltime + info.timeget(i);
            }else {
                totaltime = totaltime + 0;
            }
        }
        System.out.println("|================");
        System.out.println("���v�w�K����: " + totaltime + " ����");
        System.out.println("");
    }

    //�z��time�̏�����
    public void inittime(Info info) {
        for(i = 0;i < 100;i++) {
            info.timeset(i,Integer.MAX_VALUE);
        }
    }

    public void showall(int date) {
        totaltime = 0;//���Z�b�g
        for (int i = 1; i <= date; i++) {
            get_value = SubjectMap.get(i);
            if (get_value != null) {
                System.out.println(i + "���ڂ̊w�K�Ȗ�:");
                for (int j = 0; j < get_value.countget(); j++) {
                    System.out.println(get_value.subget(j));
                    totaltime += get_value.timeget(j); 
                }
                System.out.println(date + "���Ԃ̊w�K���ԁF" + totaltime + " ����");
            } else {
                System.out.println(i + "���ڂ̊w�K�Ȗڂ͂���܂���B");
            }
        }
    }
    

    public void nextday() {
        count = 0;
    }

    public int cheakstr(String text,String[] str,int i) {
        for(int j = 0;j < i;j++) {
            if(str[j].equals(text)) {
                return 1;
            }
        }
        return 0;
    }
}


class Info {
    private String[] subject = new String[100];
    private int[] time = new int[100];
    private int count = 0;
    private int task_count[] = new int[100];
    private String[] tasks = new String[100];

    public void subset(String sub,int i) {
        subject[i] = sub;
    }

    public void timeset(int i,int t) {
        time[i] = t;
    }

    public void countset() {
        count++;
    }

    public String subget(int i) {
        return subject[i];
    }
    
    public String[] showsubget() {
        return subject;
    }

    public int timeget(int i) {
        return time[i];
    }

    public int countget() {
        return count;
    }

    public void countreset() {
        count = 0;
    }
}
