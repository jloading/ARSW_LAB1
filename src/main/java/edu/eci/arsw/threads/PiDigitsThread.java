package edu.eci.arsw.threads;
import edu.eci.arsw.math.PiDigits;

import java.io.IOException;


public class PiDigitsThread extends Thread{

    private int start;
    private int count;
    byte[] digits = new byte[count];

    public PiDigitsThread(int start, int count){
        this.start = start;
        this.count = count;
    }

    public void run(){
        try {
            calculateDigits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public byte[] calculateDigits() throws IOException {
        PiDigits pd = new PiDigits();
        digits = pd.getDigits(start, count, 1);
        return digits;

    }

    public byte[] getDigits() {
        return digits;
    }

}
