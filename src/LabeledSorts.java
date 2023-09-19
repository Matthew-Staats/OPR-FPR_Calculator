import java.util.ArrayList;
import java.util.List;

public class LabeledSorts {

    public static <I,E extends Comparable<E>> void bubblesort (ArrayList<Key<I,E>> list) {
        if(list==null){
            throw new IllegalArgumentException();
        }

        if(list.size()==0||list.size()==1){
//            System.out.println("Small");
            return;
        }

        boolean done = true;
        do {
            done = true;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i - 1).getValue().compareTo(list.get(i).getValue()) < 0) {
                    Key<I,E> temp = list.get(i-1);
                    list.set(i-1,list.get(i));
                    list.set(i,temp);
//                    int temp = list.get(i-1);
//                    String temp2 = labels.get(i-1);
//                    list.set(i-1, list.get(i));
//                    labels.set(i-1, labels.get(i));
//                    list.set(i, temp);
//                    labels.set(i, temp2);
                    done = false;
                }
            }
        } while(!done);
    }

    public static <E extends Comparable<E>> void bubblesort (List<E> list,List<String> labels) {
        if(list==null){
            throw new IllegalArgumentException();
        }

        if(list.size()==0||list.size()==1){
//            System.out.println("Small");
            return;
        }

        boolean done = true;
        do {
            done = true;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                    E temp = list.get(i-1);
                    String temp2 = labels.get(i-1);
                    list.set(i-1, list.get(i));
                    labels.set(i-1, labels.get(i));
                    list.set(i, temp);
                    labels.set(i, temp2);
                    done = false;
                }
            }
        } while(!done);
    }
}

