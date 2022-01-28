package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() throws IOException {
        BufferedReader reader = null;
        String finalLine=null;
        try {
            reader=new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"));
            String line=reader.readLine();
            String regExp="\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";
            Pattern pattern= Pattern.compile(regExp);
            Matcher matcher=pattern.matcher(line);
            String[] billNumber=new String[2]; //заранее знаем, что у нас два совпадения, если было бы неизвестно, то создали бы массив
            int i=0;
            while (matcher.find()) {
                billNumber[i]=matcher.group();
                System.out.println(billNumber[i]);
                i++;
            }
            //для промежуточных результатов
            StringBuilder billNumberClosed1=new StringBuilder(100); //заранее знаем, что у нас два совпадения, если было бы неизвестно, то создали бы массив
            StringBuilder billNumberClosed2=new StringBuilder(100);
            billNumberClosed1.append(billNumber[0]);
            billNumberClosed2.append(billNumber[1]);
            for (int j=5; j<14; j++){
                if(j!=9) {
                    billNumberClosed1.setCharAt(j,'*');
                    billNumberClosed2.setCharAt(j,'*');
                }
            }
            String lineMid=line.replaceAll(billNumber[0], billNumberClosed1.toString());
            finalLine=lineMid.replaceAll(billNumber[1], billNumberClosed2.toString());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader!=null) {
                reader.close();
            }
        }
        return finalLine;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance)  {
        BufferedReader reader = null;
        String line=null;
        try {
            reader=new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"));
            line= reader.readLine();
            String regExp="\\$\\{payment_amount}";
            Pattern pattern= Pattern.compile(regExp);
            Matcher matcher=pattern.matcher(line);


            String regExp2="\\$\\{balance}";
            Pattern pattern2=Pattern.compile(regExp2);
            Matcher matcher2=pattern2.matcher(matcher.replaceAll(String.format("%.0f", paymentAmount)));
            line=matcher2.replaceAll(String.format("%.0f",balance));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return line;

    }
}
