package com.zsf.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * GET和POST提交方法
 * @author Administrator
 *GET没测试
 *POST测试通过
 */
public class PostUtils {
	
	private static Logger logger = LogManager.getLogger(PostUtils.class);
	
	public static void main(String[] args) {

		String data = "{\"inBody\": {\"acctype\": \"\",\"address\": \"\",\"bankAccountName\": \"\",\"bankAccountNo\": \"\",\"bankAccountTelNo\": \"\",\"bindFlag\": \"\",\"bindType\": \"\",\"busiTradeType\": \"U01\"},\"reqHeader\": {\"clientDate\": \"20181210\",\"clientSn\": \"zcjfU00001-14889494674819\",\"clientTime\": \"130427\",\"fileName\": \"\",\"merchantCode\": \"zcjf\",\"signTime\": \"1502440678537\",\"signature\": \"nSTNQ7TYzW4ZsdZVJTBGI8LVlggzhu-1eXLD6orDnTzNtDWbVVptPopGqE7ehdixCYMtMci28IONJoX72pvbiapc29rM_Tl7xSYOF4YxbSG04E7IyAbaeL2Us5MLi49_TBrxiTf6vW9GrRMsDrw1kMxrOcfC6dRTgE81j6vjHcY%\",\"txnType\": \"U00001\",\"version\": \"1.0\"}}";
        //发送 POST 请求
        String sr=sendGet("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=6214830000000000&cardBinCheck=true","");
        System.out.println(sr);
    }

	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
        	HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            String urlNameString="";
            if(param != null && param.length()>0){
                urlNameString= url + "?" +param;
                System.out.println("转码后"+urlNameString);
            }else{
                urlNameString=url;
            }
            URL realUrl = new URL(urlNameString);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
           /* // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }*/

            /** 通过connection连接，获取输入流 */
            InputStream  is = connection.getInputStream();
            /** 封装输入流is，并指定字符集 */
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            /** 存放数据 */
            StringBuffer sbf = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sbf.append(line);
                sbf.append("\r\n");
             }
             result = sbf.toString();

        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    public static void write(String str) throws IOException {
//        File file = new File("E:/aaa.txt");
//        FileOutputStream outt=new FileOutputStream(file,true);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String s = format.format(new Date());
//        str = s+"----->"+str;
//        outt.write("\r\n".getBytes());
//        outt.write("\r\n".getBytes());
//        outt.write("\r\n".getBytes());
//        outt.write(str.getBytes());
    }

    /**
     * 2019-11-08
     * 普兰专用请求接口（勿改）
     * @param url 地址
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @return
     */
    public static String sendPost(String url, String param) {
        System.out.println("地址："+url);
        System.out.println("参数："+param);
        OutputStreamWriter  out = null;
        InputStream is = null;
        String result = "";
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
//            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
//            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            String str=new String(param.getBytes("UTF-8"),"UTF-8");
            out.append(str);
//             write(str);
            out.flush();
            // 读取响应
            is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] temp = new byte[512];
            int readLen = 0;
            while ((readLen = is.read(temp)) > 0) {
                //System.arraycopy(temp, 0, data, destPos, readLen);
                baos.write(temp,0,readLen);
            }
            result =  baos.toString("UTF-8"); // utf-8编码
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                result = new String(data, "UTF-8"); // utf-8编码
//                System.out.println("主机返回:" + result);
//            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            logger.info("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(is!=null){
                    is.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 2019-11-08
     *
     * @param url 地址
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @return
     */
    public static String sendHttpPost(String url, String param) {
        System.out.println("地址："+url);
        System.out.println("参数："+param);
        OutputStreamWriter  out = null;
        InputStream is = null;
        String result = "";
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            String str=new String(param.getBytes("UTF-8"),"UTF-8");
            out.append(str);
            /*write(str);*/
            out.flush();
            // 读取响应
            is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] temp = new byte[512];
            int readLen = 0;
            while ((readLen = is.read(temp)) > 0) {
                //System.arraycopy(temp, 0, data, destPos, readLen);
                baos.write(temp,0,readLen);
            }
            result =  baos.toString("UTF-8"); // utf-8编码
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                result = new String(data, "UTF-8"); // utf-8编码
//                System.out.println("主机返回:" + result);
//            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            logger.info("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(is!=null){
                    is.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static Map<String, String> paramsTest(String url,String param,String sign){
        Map<String, String> result = new HashMap<>();
        result.put("url",url);
        result.put("param",param);
        result.put("sign",sign);
       return result;
    }

    public static String sendPost(String requestUrl,Map<String, String> requestText) {
        HttpURLConnection conn = null;
        InputStream input = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(5000 * 10);
            conn.setReadTimeout(5000 * 10);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("x-forwarded-for", "101.132.137.56"); // 设置接收数据的格式
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();

            // 往服务器端写内容 也就是发起http请求需要带的参数
            os = new DataOutputStream(conn.getOutputStream());
            // 请求参数部分
            writeParams(requestText, os);
            // 请求结束标志
            String endTarget = PREFIX + BOUNDARY + PREFIX + LINE_END;
            os.write(endTarget.getBytes());
            os.flush();

            // 读取服务器端返回的内容
            System.out.println("======================响应体=========================");
            System.out.println("ResponseCode:" + conn.getResponseCode()
                    + ",ResponseMessage:" + conn.getResponseMessage());
            if(conn.getResponseCode()==200){
                input = conn.getInputStream();
            }else{
                input = conn.getErrorStream();
            }

            br = new BufferedReader(new InputStreamReader( input, "UTF-8"));
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            //......
            System.out.println("返回报文:" + buffer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }

                if (os != null) {
                    os.close();
                    os = null;
                }

                if (br != null) {
                    br.close();
                    br = null;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return buffer.toString();
    }


    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String sign) {
    	OutputStreamWriter out = null;
    	
    	InputStream is = null;
        String result = "";
        try {
            System.out.println("url============"+url);
            logger.info("param============="+param);
            logger.info("sign==============="+sign);
        	HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("x-forwarded-for", "101.132.137.56"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("signdate", sign); // 设置发送数据的格式
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            String str=new String(param.getBytes("UTF-8"),"UTF-8");
            out.append(str);
           /* write(str);*/
            out.flush();

         // 读取响应
            is = connection.getInputStream();
            System.out.println("response------>"+connection.getResponseCode());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] temp = new byte[512];
            int readLen = 0;
            while ((readLen = is.read(temp)) > 0) {
                //System.arraycopy(temp, 0, data, destPos, readLen);
                baos.write(temp,0,readLen);
            }
            result =  baos.toString("UTF-8"); // utf-8编码
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                result = new String(data, "UTF-8"); // utf-8编码
//                System.out.println("主机返回:" + result);
//            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            logger.info("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(is!=null){
                	is.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        logger.info("param============="+param);
        return result;
    }
    private final static String BOUNDARY = UUID.randomUUID().toString()
            .toLowerCase().replaceAll("-", "");// 边界标识
    private final static String PREFIX = "--";// 必须存在
    private final static String LINE_END = "\r\n";
    private static void writeParams(Map<String, String> requestText,
                                    OutputStream os) throws Exception {
        try{
            String msg = "请求参数部分:\n";
            if (requestText == null || requestText.isEmpty()) {
                msg += "空";
            } else {
                StringBuilder requestParams = new StringBuilder();
                Set<Map.Entry<String, String>> set = requestText.entrySet();
                Iterator<Map.Entry<String, String>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    requestParams.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    requestParams.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey()).append("\"").append(LINE_END);
                    requestParams.append("Content-Type: text/plain; charset=utf-8")
                            .append(LINE_END);
                    requestParams.append("Content-Transfer-Encoding: 8bit").append(
                            LINE_END);
                    requestParams.append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
                    requestParams.append(entry.getValue());
                    requestParams.append(LINE_END);
                }
                os.write(requestParams.toString().getBytes());
                os.flush();

                msg += requestParams.toString();
            }

            System.out.println(msg);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    
    static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }


        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }


        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    } };

    public static class NullHostNameVerifier implements HostnameVerifier {
        /*
         * (non-Javadoc)
         * 
         * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
         * javax.net.ssl.SSLSession)
         */
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }


    /**
     * 跨域
     */
    public static void cross(HttpServletResponse response){
        try {
            response.setContentType("application/json;charset=UTF-8");
            //2019-04-23 解决跨域问题--开始
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setDateHeader("Expires", 0);
            //解决跨域问题--结束
        }catch (Exception e){
        }

    }



}
