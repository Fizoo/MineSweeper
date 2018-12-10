package com.javarush.task.task07.task0713;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* 
Играем в Jолушку
1. Введи с клавиатуры 20 чисел, сохрани их в список и рассортируй по трём другим спискам:
Число нацело делится на 3 (x%3==0), нацело делится на 2 (x%2==0) и все остальные.
Числа, которые делятся на 3 и на 2 одновременно, например 6, попадают в оба списка.
Порядок объявления списков очень важен.
2. Метод printList должен выводить на экран все элементы списка с новой строки.
3. Используя метод printList выведи эти три списка на экран. Сначала тот, который для x%3, потом тот, который для x%2, потом последний.
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer>list=new ArrayList<>();
        List<Integer>list1=new ArrayList<>();
        List<Integer>list2=new ArrayList<>();
        List<Integer>list3=new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(Integer.parseInt(reader.readLine()));
        }
        list1= list.stream().filter(x->x%3==0).collect(Collectors.toList());
        list2=list.stream().filter(x->x%2==0).collect(Collectors.toList());
        list3=list.stream().filter(x->x%2!=0&&x%3!=0).collect(Collectors.toList());

        printList(list1);
        printList(list2);
        printList(list3);

        //напишите тут ваш код
    }

    public static void printList(List<Integer> list) {
        list.stream().forEach(System.out::println);
        //напишите тут ваш код
    }
}
