/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread {

    private int a;

    private int b;

    public void run(){
        for (int i=a; i<=b; i++){
            System.out.println(i);
        }
    }

    public void setA(int a){
        this.a = a;
    }

    public void setB(int b){
        this.b = b;
    }


    public static void main(String[] args) {
        CountThread t = new CountThread();
        t.setA(21);
        t.setB(29);
        t.run();
    }
}
