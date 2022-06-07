package com.example.mycomposecookbook.example;

import java.util.Collections;
import java.util.HashMap;

public class Demo {
    public static void main(String[] args) {
        int[] numbers = new int[]{5, 10, 15, 20, 30, 45, 10, 11, 13, 14, 7, 6, 5, 4, 5};
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            String key = String.valueOf(numbers[i]);
            if (map.containsKey(key)) {
                int newValue = map.get(key);
                newValue += 1;
                map.put(key, newValue);
            } else {
                map.put(key, 1);
            }
        }


        for (String key : map.keySet()) {

            System.out.println(key + ":" + map.get(key).toString());
        }
    }

}
