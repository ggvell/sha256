import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App{
    private static final int TAMANHO_BLOCO = 1024;
    public static void main(String[] args) throws Exception {
        String filePath = "../resources/videobase.mp4"; 
        try {
            BufferedInputStream videoFile = new BufferedInputStream(new FileInputStream(filePath));
            ArrayList<byte[]> blocos = new ArrayList<byte[]>();
            byte[] buffer;

            while(videoFile.available() > 0){
                if(videoFile.available() < 1024){
                    buffer = new byte[videoFile.available()];
                }
                else{
                    buffer = new byte[1024];
                }
                videoFile.read(buffer);
                blocos.add(buffer);
            }
            videoFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}