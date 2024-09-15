package com.blackjack.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> A = new ArrayList<>();
        A.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));

        int k = 7;
        Integer a = null;
        Integer b = null;
        int i  =0;
        int j = A.size();

        int mid = (int) Math.floor( i+j/2);
        System.out.println(mid);

        while(i<=mid){
            if(A.get(mid)==k){
                mid=mid-1;
            } else if (A.get(mid)<k) {
                a=A.get(mid);
                mid = mid-1;
                break;
            }else if (A.get(mid-1)<k) {
                a = A.get(mid - 1);
                mid = mid - 1;
                break;
            }
                else {
                mid = mid-1;
            }
        }
        System.out.println(a);

        while(mid<=j){
            if(A.get(mid)==k){
                mid=mid+1;
            } else if (A.get(mid)>k) {
                b=A.get(mid);
                mid = mid+1;
                break;
            }else if (A.get(mid+1)>k) {
                b = A.get(mid + 1);
                mid = mid + 1;
                break;
            }
            else {
                mid = mid+1;
            }
        }
        System.out.println(b);
    }
}
