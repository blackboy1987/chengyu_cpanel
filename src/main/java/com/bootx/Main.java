package com.bootx;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String[]> main()  {
        List<String[]> idioms = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("/Users/blackoy/Desktop/chengyu/chengyu_cpanel/src/main/java/com/bootx/成语大全（31648个成语解释）.Txt");
            InputStreamReader isr = new InputStreamReader(fis, "gbk");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if(StringUtils.isNotEmpty(line)){
                    String part1 = line.split("释义：")[0];
                    String[] part2 = part1.split("拼音：");
                    if(part2.length==2){
                        idioms.add(part2);
                        count++;
                    }
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return idioms;
    }
}