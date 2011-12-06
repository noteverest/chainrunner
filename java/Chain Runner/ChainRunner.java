import java.util.*;
import java.io.*;
import java.nio.*;
import com.google.common.io.Files;
import com.google.common.base.Charsets;
public class ChainRunner
{
    List<String> lines;
    List<MovieTitle> Titles = new ArrayList<MovieTitle>();
    List<String> LongChain = new ArrayList<String>();
    int numUsed = 0;
    MovieTitle curMovie;
    int biggestCombo = 0;
    String bigStr = "";
    boolean b;
    boolean c;
    public ChainRunner()
    {
        
        MovieTitle m1 = new MovieTitle("revenge of the nerds like");
        MovieTitle m2 = new MovieTitle("nerds like take manhattan");
        //boolean b1 = m2.makesChain(m1);
        
        try
        {
            lines = Files.readLines(new File("movies.LST"), Charsets.UTF_8);
        }
        catch(IOException ex)
        {
            System.out.println("badFile");
        }
        lines.remove(0);
        for(int i = 0; i < lines.size(); i++)
        {
            Titles.add( new MovieTitle(lines.get(i)));
        }
        System.out.println("Made Big Array");
        
        
       
       
       
        
    }
    public void letItRide()
    {
        for(int i = 1000; i < Titles.size(); i++)
       {
           
           chainsWith(i, Titles, new ArrayList<Integer>(), 0, Titles.get(i).fullTitle);
           System.out.println("Big Size: " + biggestCombo);
           System.out.println(bigStr);
           String[] strs = bigStr.split("->");
           LongChain = Arrays.asList(strs);
           
          /**
           List<MovieTitle> listz = Titles;
           listz.remove(Titles.get(i));
           
           String s = chainz(Titles.get(i), listz, Titles.get(i).fullTitle, 0);
           */
        }
    }
    public String chainz(MovieTitle curTitle, List<MovieTitle> titleList, String str, int size)
    {
        for(int i = 0; i < titleList.size(); i++)
        {
            
            MovieTitle other = titleList.get(i);
            if(curTitle.equals(other) == false)
            {
            if(curTitle.makesChain(other))
            {
                List<MovieTitle> newL = titleList;
                newL.remove(curTitle);
                chainz(other, newL, str + " -> " + other.fullTitle, size+1);
            }
        }
        
        }
        //System.out.println("Size = " + size);
        if(size > biggestCombo)
                    {
                        System.out.println("Size = " + size);
                        System.out.println(str);
                        biggestCombo = size;
                        bigStr = str;
                    }
        //System.out.println(str);
        return str;
    }
    public void chainsWith(int index, List<MovieTitle> titleList, List<Integer> used, int size, String str)
    {
        MovieTitle compare = titleList.get(index);
        List<Integer> daUsed = used;
        daUsed.add(index);
        for(int i = 0; i < titleList.size(); i++)
        {
            //System.out.println("looking at - " + titleList.get(i).fullTitle);
            if(used.contains(i) == false)
            {
                MovieTitle other = titleList.get(i);
                //System.out.println("REALLY looking at - " + titleList.get(i).fullTitle);
                if(compare.makesChain(other) == true)
                {
                    
                    if(size > biggestCombo)
                    {
                        biggestCombo = size;
                        bigStr = str;
                        
                        
                        //String[] strs = bigStr.split("->");
                        //LongChain = Arrays.asList(strs);
                        System.out.println("Size = " + size);
                        System.out.println(str);
                    }
                    //System.out.println("Size = " + size);
                    //System.out.println(str);
                    chainsWith(i, titleList, daUsed, size+1, str + " -> " + other.fullTitle);
                }
            }
            
        }
        
    }
   
}
