import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.security.MessageDigest;

public class App {
    private static final int TAMANHO_BLOCO = 1024;

    public static void main(String[] args) throws Exception {
        System.out.println("Escolha o video que sera lido: ");
        System.out.println("1: FuncoesResumo - SHA1.mp4");
        System.out.println("2:  FuncoesResumo - Hash Functions.mp4");
        Scanner in = new Scanner(System.in);
        int op = in.nextInt();

        String filePath;
        switch (op) {
            case 1:
                filePath = "resources/videobase.mp4";
                break;
            case 2:
                filePath = "resources/videoexercicio.mp4";
                break;
            default:
                filePath = "resources/videobase.mp4";
                break;
        }
        in.close();
        
        BufferedInputStream videoFile = new BufferedInputStream(new FileInputStream(filePath));
        ArrayList<byte[]> blocos = new ArrayList<byte[]>();
        byte[] buffer;

        while (videoFile.available() > 0) {
            if (videoFile.available() < TAMANHO_BLOCO) {
                buffer = new byte[videoFile.available()];
            } else {
                buffer = new byte[TAMANHO_BLOCO];
            }
            videoFile.read(buffer);
            blocos.add(buffer);
        }
        videoFile.close();

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] blocoAntigo = md.digest(blocos.get(blocos.size() - 1));
        System.out.println("Ultimo bloco: " + bytesToHex(blocoAntigo));

        for (int i = blocos.size() - 2; i >= 0; i--) {
            byte[] blocoAtual = blocos.get(i);
            byte[] blocoNovo = new byte [blocoAtual.length + blocoAntigo.length];

            System.arraycopy(blocoAtual, 0, blocoNovo, 0,blocoAtual.length);
            System.arraycopy(blocoAntigo, 0, blocoNovo, blocoAtual.length , blocoAntigo.length);

            blocoAntigo = md.digest(blocoNovo);
        }

        System.out.println("Primeiro bloco: " + bytesToHex(blocoAntigo));

    }
   
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}