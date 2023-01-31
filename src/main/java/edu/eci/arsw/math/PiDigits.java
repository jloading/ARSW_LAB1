package edu.eci.arsw.math;
import edu.eci.arsw.threads.PiDigitsThread;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) throws IOException, InterruptedException {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[count];

        if (N == 1){
            double sum = 0;

            for (int i = 0; i < count; i++) {
                if (i % DigitsPerSum == 0) {
                    sum = 4 * sum(1, start)
                            - 2 * sum(4, start)
                            - sum(5, start)
                            - sum(6, start);

                    start += DigitsPerSum;
                }

                sum = 16 * (sum - Math.floor(sum));
                digits[i] = (byte) sum;
            }
        }
        else if (N > 1){
            int div = count / N;
            System.out.println("DIV: " + div);
            ArrayList<byte[]> results = new ArrayList<>();
            ArrayList<PiDigitsThread> threads = new ArrayList<>();

            for (int i = 0; i < N-1; i++){
                PiDigitsThread pd1 = new PiDigitsThread(div*i + start, div);
                System.out.println("Start: " + (div*i + start) + " Count: " + div);
                threads.add(pd1);
                pd1.start();
            }

            PiDigitsThread pd2 = new PiDigitsThread(div*(N-1) + start , (div + count % N));
            System.out.println("Start: " + (div*(N-1) + start) + " Count: " + (div + count % N));
            System.out.println("MOD: "+ count % N);
            threads.add(pd2);
            pd2.start();

            for (int i = 0; i < threads.size(); i++){
                threads.get(threads.size()-i-1).join();
            }

            for (int i = 0; i < threads.size() - 1; i++) {
                results.add(threads.get(i).getDigits());
            }
            results.add(pd2.getDigits());

            System.out.println(threads);
            System.out.println(results);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

            for (int i = 0; i < results.size(); i++){
                outputStream.write(results.get(i));
            }

            digits = outputStream.toByteArray();
            System.out.println("Digits (PiDigits): " + digits);

        }
        else if (N < 1) {
            throw new RuntimeException("Invalid Number of Threads");
        }

        return digits;
    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

}
