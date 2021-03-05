
import java.awt.Robot;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

//Matan Kedar
//01.01.2021

public class stockSearch {


    public static void main(String[] args) {
        String[] ticker = new String[15];
        Scanner sc = new Scanner(System.in);

        System.out.println("Live? (y/n)");
        String live = sc.next();

        System.out.println("Number of stocks to display (1-15)");
        int cap = sc.nextInt();

        Boolean liveCount = false;
        System.out.println("Stock Ticker/s:");
        if (live.equals("y")) {
            liveCount = true;
        } else if (live.equals("n")) {
            liveCount = false;
        } else {
            System.out.println("Something went wrong");
            return;
        }

        for (int i = 0; i < cap; i++) {
            ticker[i] = sc.next();
        }


        try {
            int counter = 0;

            if(liveCount != true) {
                header(stockData(ticker[0]));
                for (int x = 0; x < cap; x++) {
                    prnt(stockData(ticker[x]));
                }
            }

            while (liveCount) {
                header(stockData(ticker[0]));
                for (int x = 0; x < cap; x++) {
                    prnt(stockData(ticker[x]));
                }
                for (long i = 0; i < 4294967294L; i++) {
                        for(int j = 0; j < 2147483647; j++){

                        }
                }
                clear();
                counter++;


            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static String[] stockData(String ticker) throws IOException {


        URL url = new URL("https://www.marketwatch.com/investing/stock/" + ticker + "?mod=over_search");
        URLConnection urlConn = url.openConnection();
        InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
        BufferedReader buff = new BufferedReader(inStream);


        String[] data = new String[19];
        int test = 0;
        for (int x = 0; x < 19; x++) {
            data[x] = x + "****************************************************************************************************";
        }

        String price = "NA";
        String line = buff.readLine();

        while (line != null) {
            if (line.contains(" <meta name=\"price\" content=\"")) {
                int target = line.indexOf(" <meta name=\"price\" content=\"");
                int deci = line.indexOf(".", target);
                int start = deci;
                while (line.charAt(start) != '\"') {
                    start--;
                }
                price = line.substring(start + 2, deci + 3);
            }


            if (line.contains("  <span class=\"primary \"")) {

                //System.out.println(line);

                if (test <= 13) {
                    data[0 + test] = line;
                    test++;

                }

            }

            if (line.contains("  <meta name=\"name\" content=\"")) {
                data[14] = line;

            }
            if (line.contains("  <meta name=\"tickerSymbol\" content=")) {
                data[15] = line;

            }
            if ((line.contains("   <span class=\"primary is-na\">N/A</span>"))) {
                data[8] = "N/A";
            }
            if (line.contains(" <meta name=\"priceChangePercent\" content=\"")&&(line.contains("%"))){
                data[16] = line;
            }
            if (line.contains("<meta name=\"priceChange\" content=\"")){
                data[17] = line;
            }
            if (line.contains("<meta name=\"quoteTime\" content=")){
                data[18] = line;
            }

            line = buff.readLine();

        }

        data[0] = price;

        int test2 = 0;
        int end = 0;
        int start = 43;


        for (int i = 1; i < data.length; i++) {


            end = data[i].length();


            if (data[i].contains("</span>")) {
                end = end - 7;
            }
            if (data[i].contains("<meta name=\"name\" content=\"")) {
                end = end - 4;
                start = start - 8;
            }
            if (data[i].contains("<meta name=\"tickerSymbol\" content=")) {
                end = end - 4;
                start = start + 8;
            }
            if (data[i].contains("priceChange\"")){
                end = end - 4;
                start = start - 7;
            }
            if (data[i].contains("<meta name=\"priceChangePercent\" content=")){
               end = end - 4;
               start = start + 6;
            }
            if (data[i].contains("<meta name=\"quoteTime\" content=")){
                end = end - 4;
                start = start + 6;
            }

            if (data[i] != "N/A"){

                    data[i] = data[i].substring(start, end);
                    //System.out.println(data[i]);

            }





        }
        return data;
    }

    public static void prnt(String[] data) {
        String space = "     ";
        if (data[14].length() > 15){
            data[14] = data[14].substring(0, 14) + "...";
        }
        if(data[14].length() < 17 ){
            while (data[14].length() < 17){
                data[14] = data[14] + " ";
            }
        }
        if(data[15].length() < 7 ){
            while (data[15].length() < 10){
                data[15] = data[15] + " ";
            }
        }

        System.out.printf(data[15] + "%-16s%-16s%-16s%-16s%-16s%-16s%-16s",data[14],data[0],data[16],data[17],data[8],data[3],data[1]);
        System.out.println();
    }

    public static void clear() {
        String space = "   ";
        for (int i = 0; i < 50; i++) {
            System.out.println(space);
        }
    }
    public static void header(String data[]){
        System.out.println("As of " + data[18]);
        System.out.println("Ticker      Company         Price         Change%        Change$           P/E            Market Cap        Day Range               ");
        System.out.println("***************************************************************************************************************************** ");
    }                      //TSLA      Tesla Inc.       793.53         -5.02%         -41.90         N/A            $791.9B       780.10 - 842.4









}










