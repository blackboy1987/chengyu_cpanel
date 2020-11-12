package com.bootx;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String[]> main()  {
        List<String[]> idioms = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("E:\\IdeaProjects\\me\\chengyu_cpanel\\src\\main\\java\\com\\bootx\\成语大全（31648个成语解释）.Txt");
            InputStreamReader isr = new InputStreamReader(fis, "gbk");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
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

    public static void main(String[] args) throws IOException, InterruptedException {
        int count = 0;
        String [] strs = new String[]{
             "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
        };

        for (String str:strs) {
            for (int i=1;i<=60;i++){
                String url = "http://www.esk365.com/chengyu/cysot.asp?t="+str+"&page="+i;
                Thread.sleep(300);
                System.out.println(str+"==============="+i);
                Document document = Jsoup.parse(new URL(url), 5000);
                Element ls_a5 = document.getElementsByClass("ls_a5").first();
                if(ls_a5!=null){
                    Elements li = ls_a5.getElementsByTag("li");
                    for (Element element:li) {
                        System.out.println(element.text());
                        count ++;
                    }
                }else{
                    break;
                }
            }
        }

        System.out.println("================================================================================="+count);

    }

}