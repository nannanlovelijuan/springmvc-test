package www.ezrpro.com.util;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  16:50:39
* 
*/

public class EncodeUtil{

    /**
     *  转化为16进制
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp ;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
               }
            stringBuffer.append(temp);
            }
       return stringBuffer.toString();
       }

}