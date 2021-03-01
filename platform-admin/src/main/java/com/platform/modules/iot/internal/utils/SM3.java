package com.platform.modules.iot.internal.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SM3
{

    private static Log logger = LogFactory.getLog(SM3.class);

    private static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
        'D', 'E', 'F'};

    private static final String ivHexStr = "7380166f 4914b2b9 172442d7 da8a0600 a96f30bc 163138aa e38dee4d b0fb0e4e";

    private static final BigInteger IV = new BigInteger(ivHexStr.replaceAll(" ", ""), 16);

    private static final Integer Tj15 = Integer.valueOf("79cc4519", 16);

    private static final Integer Tj63 = Integer.valueOf("7a879d8a", 16);

    private static final byte[] FirstPadding = {(byte)0x80};

    private static final byte[] ZeroPadding = {(byte)0x00};

    private static int T(int j)
    {
        if (j >= 0 && j <= 15)
        {
            return Tj15.intValue();
        }
        else if (j >= 16 && j <= 63)
        {
            return Tj63.intValue();
        }
        else
        {
            throw new RuntimeException("data invalid");
        }
    }

    private static Integer FF(Integer x, Integer y, Integer z, int j)
    {
        if (j >= 0 && j <= 15)
        {
            return Integer.valueOf(x.intValue() ^ y.intValue() ^ z.intValue());
        }
        else if (j >= 16 && j <= 63)
        {
            return Integer.valueOf((x.intValue() & y.intValue()) | (x.intValue() & z.intValue())
                                   | (y.intValue() & z.intValue()));
        }
        else
        {
            throw new RuntimeException("data invalid");
        }
    }

    private static Integer GG(Integer x, Integer y, Integer z, int j)
    {
        if (j >= 0 && j <= 15)
        {
            return Integer.valueOf(x.intValue() ^ y.intValue() ^ z.intValue());
        }
        else if (j >= 16 && j <= 63)
        {
            return Integer.valueOf((x.intValue() & y.intValue()) | (~x.intValue() & z.intValue()));
        }
        else
        {
            throw new RuntimeException("data invalid");
        }
    }

    private static Integer P0(Integer x)
    {
        return Integer.valueOf(x.intValue() ^ Integer.rotateLeft(x.intValue(), 9)
                               ^ Integer.rotateLeft(x.intValue(), 17));
    }

    private static Integer P1(Integer x)
    {
        return Integer.valueOf(x.intValue() ^ Integer.rotateLeft(x.intValue(), 15)
                               ^ Integer.rotateLeft(x.intValue(), 23));
    }

    private static byte[] padding(byte[] source)
        throws IOException
    {
        if (source.length >= 0x2000000000000000l)
        {
            throw new RuntimeException("src data invalid.");
        }
        long l = source.length * 8;
        long k = 448 - (l + 1) % 512;
        if (k < 0)
        {
            k = k + 512;
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("k = " + k);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(source);
        baos.write(FirstPadding);
        long i = k - 7;
        while (i > 0)
        {
            baos.write(ZeroPadding);
            i -= 8;
        }
        baos.write(long2bytes(l));
        if (logger.isDebugEnabled())
        {
            logger.debug("paded size = " + baos.size());
        }
        return baos.toByteArray();
    }

    private static byte[] long2bytes(long l)
    {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++ )
        {
            bytes[i] = (byte)(l >>> ((7 - i) * 8));
        }
        return bytes;
    }

    public static byte[] hash(byte[] source)
        throws IOException
    {
        byte[] m1 = padding(source);
        int n = m1.length / (512 / 8);
        if (logger.isDebugEnabled())
        {
            logger.debug("n = " + n);
        }
        byte[] b;
        byte[] vi = IV.toByteArray();
        byte[] vi1 = null;
        for (int i = 0; i < n; i++ )
        {
            b = Arrays.copyOfRange(m1, i * 64, (i + 1) * 64);
            vi1 = CF(vi, b);
            vi = vi1;
        }
        return vi1;
    }

    private static byte[] CF(byte[] vi, byte[] bi)
        throws IOException
    {
        int a, b, c, d, e, f, g, h;
        a = toInteger(vi, 0);
        b = toInteger(vi, 1);
        c = toInteger(vi, 2);
        d = toInteger(vi, 3);
        e = toInteger(vi, 4);
        f = toInteger(vi, 5);
        g = toInteger(vi, 6);
        h = toInteger(vi, 7);

        int[] w = new int[68];
        int[] w1 = new int[64];
        for (int i = 0; i < 16; i++ )
        {
            w[i] = toInteger(bi, i);
        }
        for (int j = 16; j < 68; j++ )
        {
            w[j] = P1(w[j - 16] ^ w[j - 9] ^ Integer.rotateLeft(w[j - 3], 15))
                   ^ Integer.rotateLeft(w[j - 13], 7) ^ w[j - 6];
        }
        for (int j = 0; j < 64; j++ )
        {
            w1[j] = w[j] ^ w[j + 4];
        }
        int ss1, ss2, tt1, tt2;
        for (int j = 0; j < 64; j++ )
        {
            ss1 = Integer.rotateLeft(Integer.rotateLeft(a, 12) + e + Integer.rotateLeft(T(j), j),
                7);
            ss2 = ss1 ^ Integer.rotateLeft(a, 12);
            tt1 = FF(a, b, c, j) + d + ss2 + w1[j];
            tt2 = GG(e, f, g, j) + h + ss1 + w[j];
            d = c;
            c = Integer.rotateLeft(b, 9);
            b = a;
            a = tt1;
            h = g;
            g = Integer.rotateLeft(f, 19);
            f = e;
            e = P0(tt2);
        }
        byte[] v = toByteArray(a, b, c, d, e, f, g, h);
        for (int i = 0; i < v.length; i++ )
        {
            v[i] = (byte)(v[i] ^ vi[i]);
        }
        return v;
    }

    private static int toInteger(byte[] source, int index)
    {
        StringBuilder valueStr = new StringBuilder("");
        for (int i = 0; i < 4; i++ )
        {
            valueStr.append(chars[(byte)((source[index * 4 + i] & 0xF0) >> 4)]);
            valueStr.append(chars[(byte)(source[index * 4 + i] & 0x0F)]);
        }
        return Long.valueOf(valueStr.toString(), 16).intValue();

    }

    private static byte[] toByteArray(int a, int b, int c, int d, int e, int f, int g, int h)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
        baos.write(toByteArray(a));
        baos.write(toByteArray(b));
        baos.write(toByteArray(c));
        baos.write(toByteArray(d));
        baos.write(toByteArray(e));
        baos.write(toByteArray(f));
        baos.write(toByteArray(g));
        baos.write(toByteArray(h));
        return baos.toByteArray();
    }

    private static byte[] toByteArray(int i)
    {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte)(i >>> 24);
        byteArray[1] = (byte)((i & 0xFFFFFF) >>> 16);
        byteArray[2] = (byte)((i & 0xFFFF) >>> 8);
        byteArray[3] = (byte)(i & 0xFF);
        return byteArray;
    }

    @SuppressWarnings("unused")
    private static void printIntArray(int[] intArray, int lineSize)
    {
        for (int i = 0; i < intArray.length; i++ )
        {
            byte[] byteArray = toByteArray(intArray[i]);
            int j = 0;
            while (j < byteArray.length)
            {
                System.out.print(chars[(byteArray[j] & 0xFF) >> 4]);
                System.out.print(chars[byteArray[j] & 0xF]);
                j++ ;
            }
            System.out.print(" ");
            if (i % lineSize == (lineSize - 1))
            {
                System.out.println(" ");
            }
        }
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[])
    {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++ )
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0) n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回对源串的摘要结果，结果为16进制小写表达方式
     * @param source
     * @param charsetname
     * @return
     * @throws IOException
     */
    public static String SM3Encode(String source, String charsetname)
        throws IOException
    {
        String resultString = null;
        if (charsetname == null || "".equals(charsetname))
        {
            resultString = byteArrayToHexString(SM3.hash(source.getBytes()));
        }
        else
        {
            resultString = byteArrayToHexString(SM3.hash(source.getBytes(charsetname)));
        }
        return resultString;
    }

    /**
     * 
     * app_id5E6339211Ftimestamp2017-09-14 13:54:45 687trans_id20170914135445687536273PG65cP4C4977I0z3durISe1c24547409
    app_id:5E6339211F
    timestamp:2017-09-14 13:54:45 687
    trans_id:20170914135445687536273
    token: d1e36c3e8e1abdd704c74591ee3da7a124f072e11ee041968f3940cdc1499438 
    PG65cP4C4977I0z3durISe1c24547409   这个app_secrect
     * 
     * @param args
     * @throws IOException
     */

    public static Map<String, Object> makeToken(String appId, String appSecrect)
        throws IOException
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = "";
        String timestamp = "2017-09-14 13:54:45 687";
        String transId = "20170914135445687536273";

        timestamp = getFormatedDateString(8);
        transId = timestamp.replace("-", "").replace(" ", "").replace(":", "");
        transId = transId + (new Random().nextInt(999999));

        StringBuilder sb = new StringBuilder();
        sb.append("app_id");
        sb.append(appId);
        sb.append("timestamp");
        sb.append(timestamp);
        sb.append("trans_id");
        sb.append(transId);
        sb.append(appSecrect);
        token = SM3.SM3Encode(sb.toString(), "UTF-8");
        result.put("token", token);
        result.put("timestamp", timestamp);
        result.put("trans_id", transId);
        return result;
    }

    /**
     * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * @param timeZoneOffset
     * @return
     */
    public static String getFormatedDateString(float timeZoneOffset)
    {
        if (timeZoneOffset > 13 || timeZoneOffset < -12)
        {
            timeZoneOffset = 0;
        }

        int newTime = (int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0)
        {
            timeZone = TimeZone.getDefault();
        }
        else
        {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    public static void main(String[] args)
        throws IOException
    {
        byte[] source, sm3HashValue;

        source = new byte[] {97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100,
            97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100,
            97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100, 97, 98, 99, 100,
            97, 98, 99, 100, 97, 98, 99, 100};
        sm3HashValue = SM3.hash(source);
        System.out.println(SM3.SM3Encode(sm3HashValue.toString(), "utf-8"));
        String token = SM3.SM3Encode(
            "app_id5E6339211Ftimestamp2017-09-14 13:54:45 687trans_id20170914135445687536273PG65cP4C4977I0z3durISe1c24547409",
            "utf-8");
        System.out.println(token);
        System.out.println(
            "d1e36c3e8e1abdd704c74591ee3da7a124f072e11ee041968f3940cdc1499438".equals(token));
        System.out.println("20170914135445687536273".length());
        System.out.println("20170914155804273365621".length());
    }
}
