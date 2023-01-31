package edu.eci.arsw.threads;
import edu.eci.arsw.math.PiDigits;

import java.io.IOException;


public class PiDigitsThread extends Thread{

    private int start;
    private int count;
    byte[] digits;

    public PiDigitsThread(int start, int count){
        this.start = start;
        this.count = count;
    }

    public void run(){
        try {
            calculateDigits();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Threads End -----------------RESULT: "+ getDigits() +"----------------------");
    }


    public byte[] calculateDigits() throws IOException, InterruptedException {
        PiDigits pd = new PiDigits();
        setDigits(pd.getDigits(start, count, 1));
        return digits;

    }

    private void setDigits(byte[] digits){
        this.digits= digits;
    }

    public byte[] getDigits() {
        return digits;
    }

}
