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
    private int date;//日付
    private int today = 0;
    private int judge = 0;
    private String operation;//操作用
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

        System.out.println("何日間記録をしますか？");
        Scanner d = new Scanner(System.in);
        date = d.nextInt();

        while(i <= date) {
            if(today != i) {
                info = makeinfo();
                today++;
            }

            System.out.println(today + "日目");
            System.out.println("===操作を選んでください(半角数字で入力してください)===");
            System.out.println("学習科目の入力:1");
            System.out.println("学習時間の入力:2");
            System.out.println("学習科目の追加:3");
            System.out.println("今日の学習時間の表示:4");
            System.out.println("追加科目のみの学習時間の入力:5");
            System.out.println("次の日の記録:next");
            System.out.println("今日の学習科目・合計学習時間の確認:x");
            System.out.println("システムの終了:exit");

            Scanner in = new Scanner(System.in);
            operation = in.nextLine();

            switch(operation) {  
            case "1":
                if(judge == 0) {
                    judge = 1;
                }else {
                    System.out.println("入力済みです。新しい科目を入力する場合はメニューで3を押してください");
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
                System.out.println(i + "日目の記録");
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

    //学習科目を入力
    public void Subinput(int date,Info info) {
        i = 0;
        //科目の入力
        while(true) {
            Scanner sub = new Scanner(System.in);
            System.out.println("科目を入力");
            subject = sub.nextLine();
            if(!subject.equals("終了")) {
                info.subset(subject,i);
                System.out.println("入力された科目：" + info.subget(i));
                info.countset();
            }else {
                break;
            }
            i++;
        }
        SubjectMap.put(date,info);
        count = info.countget();
    }

    //追加科目を入力
    public void Subinputadd(int date,Info info) {
        int judge;
        i = count;
        while(true) {
            judge = 0;
            Scanner subadd = new Scanner(System.in);
            System.out.println("科目を入力");
            subjectadd = subadd.nextLine();

                if(cheakstr(subjectadd,info.showsubget(),count) == 1) {
                    System.out.println("科目が重複しています。正しい科目名を入力してください");
                    judge = 1;
                }


            if(!subjectadd.equals("終了")) {
                if(judge == 0) {
                    info.subset(subjectadd,i);
                    System.out.println("入力された科目：" + info.subget(i));
                    info.countset();
                    i++;
                }
            }else {
                break;
            }
        }
        SubjectMap.put(date,info);
    }

    //学習時間を入力
    public void Timeinput(int date,Info info) {
        if(info.countget() != 0) {
            for(i = 0;i < info.countget();i++) {
                Scanner t = new Scanner(System.in);
                System.out.println(info.subget(i) + "の学習時間を入力");
                this.t = t.nextInt();
                info.timeset(i, this.t);
            }
        }else {
            System.out.println("入力できるデータがありません");
        }
        SubjectMap.put(date,info);
    }

    //追加科目のみ学習時間を記録
    public void Timeinputadd(int date,Info info) {
        if(info.countget() != 0) {
            for(i = count;i < info.countget();i++) {
                Scanner t = new Scanner(System.in);
                System.out.println(info.subget(i) + "の学習時間を入力");
                this.t = t.nextInt();
                info.timeset(i, this.t);
            }
        }else {
            System.out.println("入力できるデータがありません");
        }
        SubjectMap.put(date,info);
        count = info.countget();
    }

    //科目毎の勉強時間を出力
    public void showtime(Info info) {
        System.out.println("");
        if(info.timeget(0) != Integer.MAX_VALUE) {
            for(i = 0;i < count;i++) {
                if(info.timeget(i) != Integer.MAX_VALUE) {
                    System.out.print(info.subget(i) + " | " + info.timeget(i) + " 時間");
                }else {
                    System.out.print(info.subget(i) + " | " + " 0 時間");
                }
                System.out.println("");
            }
        }else{
            System.out.println("表示できるデータがありません");
        }
        System.out.println("");
    }

    //すべての情報を出力
    public void showinfo(Info info) {
        System.out.println("|==学習科目一覧==");
        for(i = 0;i < count;i++) {
            System.out.println("| " + info.subget(i));

            if(info.timeget(i) != Integer.MAX_VALUE) {
                totaltime = totaltime + info.timeget(i);
            }else {
                totaltime = totaltime + 0;
            }
        }
        System.out.println("|================");
        System.out.println("合計学習時間: " + totaltime + " 時間");
        System.out.println("");
    }

    //配列timeの初期化
    public void inittime(Info info) {
        for(i = 0;i < 100;i++) {
            info.timeset(i,Integer.MAX_VALUE);
        }
    }

    public void showall(int date) {
        totaltime = 0;//リセット
        for (int i = 1; i <= date; i++) {
            get_value = SubjectMap.get(i);
            if (get_value != null) {
                System.out.println(i + "日目の学習科目:");
                for (int j = 0; j < get_value.countget(); j++) {
                    System.out.println(get_value.subget(j));
                    totaltime += get_value.timeget(j); 
                }
                System.out.println(date + "日間の学習時間：" + totaltime + " 時間");
            } else {
                System.out.println(i + "日目の学習科目はありません。");
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
