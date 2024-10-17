package com.blackjack.game;

public class Test {

    public static void partition(int[] A, int odd, int even) {
        int[] B = new int[8];
        for(int i=0;i<A.length;i++){
            if(A[i]<0){
                B[odd]=A[i];
                odd+=2;
            }else {
                B[even]=A[i];
                even+=2;
            }
        }
        for(int i=0;i<B.length;i++){
            System.out.print(B[i]);
        }
    }

    public static void main(String[] args) {
        int[] A ={-1,7,5,-3,8,4,-1,-6};
        partition(A,0,1);
    }
}
