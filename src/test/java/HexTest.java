import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * PLEASE EDIT THIS DESCRIPTION.
 * Created by Slevin Philips on 2016/7/12.
 */
public class HexTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        createAllChinese();
    }

   private static void  createAllChinese() throws UnsupportedEncodingException {
        byte a = Byte.MIN_VALUE;
        while (a < Byte.MAX_VALUE) {
            byte b = Byte.MIN_VALUE;
            while (b < Byte.MAX_VALUE) {
                byte[] bytes = new byte[]{a, b};
                System.out.print(new String(bytes, "GB2312"));
                b++;
            }
            System.out.println();
            a++;
        }
    }

    void test() {
        String str = "47 45 54 20 2f 77 65 62 73 6f 63 6b 65 74 20 48" +
                " 54 54 50 2f 31 2e 31 0d 0a 48 6f 73 74 3a 20 66" +
                " 65 65 64 62 61 63 6b 2e 73 6e 68 34 38 2e 63 6f" +
                " 6d 3a 38 30 38 30 0d 0a 43 6f 6e 6e 65 63 74 69" +
                " 6f 6e 3a 20 55 70 67 72 61 64 65 0d 0a 50 72 61" +
                " 67 6d 61 3a 20 6e 6f 2d 63 61 63 68 65 0d 0a 43" +
                " 61 63 68 65 2d 43 6f 6e 74 72 6f 6c 3a 20 6e 6f" +
                " 2d 63 61 63 68 65 0d 0a 55 70 67 72 61 64 65 3a" +
                " 20 77 65 62 73 6f 63 6b 65 74 0d 0a 4f 72 69 67" +
                " 69 6e 3a 20 68 74 74 70 3a 2f 2f 66 65 65 64 62" +
                " 61 63 6b 2e 73 6e 68 34 38 2e 63 6f 6d 0d 0a 53" +
                " 65 63 2d 57 65 62 53 6f 63 6b 65 74 2d 56 65 72" +
                " 73 69 6f 6e 3a 20 31 33 0d 0a 44 4e 54 3a 20 31" +
                " 0d 0a 55 73 65 72 2d 41 67 65 6e 74 3a 20 4d 6f" +
                " 7a 69 6c 6c 61 2f 35 2e 30 20 28 57 69 6e 64 6f" +
                " 77 73 20 4e 54 20 36 2e 31 3b 20 57 69 6e 36 34" +
                " 3b 20 78 36 34 29 20 41 70 70 6c 65 57 65 62 4b" +
                " 69 74 2f 35 33 37 2e 33 36 20 28 4b 48 54 4d 4c" +
                " 2c 20 6c 69 6b 65 20 47 65 63 6b 6f 29 20 43 68" +
                " 72 6f 6d 65 2f 35 31 2e 30 2e 32 37 30 34 2e 37" +
                " 39 20 53 61 66 61 72 69 2f 35 33 37 2e 33 36 0d" +
                " 0a 41 63 63 65 70 74 2d 45 6e 63 6f 64 69 6e 67" +
                " 3a 20 67 7a 69 70 2c 20 64 65 66 6c 61 74 65 2c" +
                " 20 73 64 63 68 0d 0a 41 63 63 65 70 74 2d 4c 61" +
                " 6e 67 75 61 67 65 3a 20 7a 68 2d 43 4e 2c 7a 68" +
                " 3b 71 3d 30 2e 38 2c 65 6e 3b 71 3d 30 2e 36 2c" +
                " 6a 61 3b 71 3d 30 2e 34 2c 7a 68 2d 54 57 3b 71" +
                " 3d 30 2e 32 0d 0a 43 6f 6f 6b 69 65 3a 20 5f 5f" +
                " 75 74 6d 61 3d 31 35 37 34 30 36 39 36 38 2e 31" +
                " 31 39 36 30 30 31 39 31 2e 31 34 36 37 37 31 30" +
                " 30 32 35 2e 31 34 36 37 37 31 30 30 32 35 2e 31" +
                " 34 36 37 37 31 30 30 32 35 2e 31 3b 20 5f 5f 75" +
                " 74 6d 7a 3d 31 35 37 34 30 36 39 36 38 2e 31 34" +
                " 36 37 37 31 30 30 32 35 2e 31 2e 31 2e 75 74 6d" +
                " 63 73 72 3d 28 64 69 72 65 63 74 29 7c 75 74 6d" +
                " 63 63 6e 3d 28 64 69 72 65 63 74 29 7c 75 74 6d" +
                " 63 6d 64 3d 28 6e 6f 6e 65 29 3b 20 48 6d 5f 6c" +
                " 76 74 5f 39 66 38 34 65 66 66 62 36 63 37 37 36" +
                " 37 65 66 36 37 65 62 61 63 63 63 61 64 34 61 32" +
                " 32 37 39 3d 31 34 36 36 34 38 33 35 37 35 2c 31" +
                " 34 36 36 36 35 31 39 36 35 2c 31 34 36 37 36 30" +
                " 36 36 30 31 2c 31 34 36 37 37 31 30 31 30 30 3b" +
                " 20 70 67 76 5f 70 76 69 3d 35 39 39 31 32 37 36" +
                " 31 36 30 0d 0a 53 65 63 2d 57 65 62 53 6f 63 6b" +
                " 65 74 2d 4b 65 79 3a 20 34 54 4c 75 76 78 54 67" +
                " 6a 4f 33 67 62 63 38 47 69 39 32 64 35 41 3d 3d" +
                " 0d 0a 53 65 63 2d 57 65 62 53 6f 63 6b 65 74 2d" +
                " 45 78 74 65 6e 73 69 6f 6e 73 3a 20 70 65 72 6d" +
                " 65 73 73 61 67 65 2d 64 65 66 6c 61 74 65 3b 20" +
                " 63 6c 69 65 6e 74 5f 6d 61 78 5f 77 69 6e 64 6f" +
                " 77 5f 62 69 74 73 0d 0a 0d 0a";
        String[] array = str.split(" ");
        byte[] bytes = new byte[array.length];
        int i = 0;
        for (String s : array) {
            byte b = Byte.parseByte(s, 16);
//            System.out.print(new String(new byte[]{b}));
            bytes[i] = b;
            i++;
        }
        String result = new String(bytes);
//        System.out.println(result);


        byte b = Byte.MIN_VALUE;
        do {
            b++;
            String s = new String(new byte[]{b});
//            System.out.println(b + "=" + s);
        }
        while (b < Byte.MAX_VALUE);

        String[] a = new String[]{"我", "和", "我和", "我和你", "你好", "你好啊"};
        for (String ss : a) {
            ByteBuffer buffer = Charset.forName("GB2312").encode(ss);
            for (byte b1 : ss.getBytes()) {
                System.out.print(b1 + " ");
            }

            System.out.print("=" + ss + "=");
            for (byte b2 : buffer.array()) {
                System.out.print(b2 + " ");
            }
            System.out.println();
        }

    }
}
