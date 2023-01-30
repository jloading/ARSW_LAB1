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
public class CountThreadsMain {
    
    public static void main(String a[]){
        CountThread ct = new CountThread();
        ct.setA(0);
        ct.setB(99);
        ct.start();
        CountThread cta = new CountThread();
        cta.setA(100);
        cta.setB(199);
        cta.start();
        CountThread ctb = new CountThread();
        ctb.setA(200);
        ctb.setB(299);
        ctb.start();
    }
}