package module2.huffman;

import java.io.*;

public class Main {
    public static void main(String args[]) throws IOException {
        //	args = new String[3];
        //	args[0]="dec";
        //	args[1]="C:/Users/Misha/Desktop/coder_decoder/Тест.txt.hf";
        //	args[2]="C:/Users/Misha/Desktop/coder_decoder/Тест22.txt";
        if (args.length < 1)
            usage();

        try {
            if (args[0].equals("enc"))
                doEncode(args);
            else if (args[0].equals("dec"))
                doDeocde(args);
            else
                usage();
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: " + e.toString());
            usage();
        }

        System.exit(0);
    }

    public static void doEncode(String[] args) throws IOException {
        if (args.length < 3)
            usage();

        File inFile = new File(args[1]);
        File outFile = new File(args[2]);
        InputStream in = new FileInputStream(inFile);
        HuffmanOutputStream hout = new HuffmanOutputStream(new FileOutputStream(outFile));
        byte buf[] = new byte[4096];
        int len;

        while ((len = in.read(buf)) != -1)
            hout.write(buf, 0, len);

        in.close();
        hout.close();

        System.out.println("Сжатие выполнено");
        System.out.println("Исходный размер файла:     " + inFile.length());
        System.out.println("Размер сжатого файла:   " + outFile.length());
        System.out.print("Эффективность сжатия: ");
        if (inFile.length() > outFile.length()) {
            System.out.format("%.2f%%\n",
                    (100.0 - (((double) outFile.length() / (double) inFile.length()) * 100)));
        } else
            System.out.println("отсутствует");
    }

    public static void doDeocde(String[] args) throws IOException {
        if (args.length < 3)
            usage();

        File inFile = new File(args[1]);
        File outFile = new File(args[2]);
        HuffmanInputStream huffmanInputStream = new HuffmanInputStream(new FileInputStream(inFile));
        OutputStream fileOutputStream = new FileOutputStream(outFile);
        byte buf[] = new byte[4096];
        int len;

        while ((len = huffmanInputStream.read(buf)) != -1)
            fileOutputStream.write(buf, 0, len);

        huffmanInputStream.close();
        fileOutputStream.close();
        System.out.println("Распаковка выполнена");
        System.out.println("Исходный размер файла:     " + inFile.length());
        System.out.println("Размер распакованного файла:     " + outFile.length());
    }

    public static void usage() {
        System.err.println("Читай README");
    }
}
