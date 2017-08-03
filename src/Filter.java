/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kangj
 */
public class Filter {
    private static String[] filters = {"www.w3.org","sg.search.yahoo.com","s.yimg.com","sg.images.search.yahoo.com","schemas.live.com","choice.microsoft.com","141104777.r.bat.bing.com","0.r.bat.bing.com","52000536.r.bat.bing.com","1721889.r.bat.bing.com",".png","angularjs","10523621.collect.igodigital.com"};
    public static boolean filterMiscLinks(String w)
    {
        for(String filter : filters)
        {
            //System.out.println("checking filters : " + w  + " against " + filter);
            if(w.contains(filter))
            {
                //System.out.println("yes contains");
                return false;
            }
        }
        return true;
    }
}
