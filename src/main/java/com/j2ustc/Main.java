package com.j2ustc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by 2ustc on 2017/5/25.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length<2 || !args[0].endsWith("atlas")){
            return;
        }
        String altasFile = args[0];
        String altasImage = "";
        String outFolde = args[1];
        LinkedHashMap<String, ArrayList<Integer>> images = new LinkedHashMap<String, ArrayList<Integer>>();
        String line = "", curName = "";
        int lineNum = 0;
        BufferedReader reader = new BufferedReader(new FileReader(altasFile));
        while ((line = reader.readLine()) != null) {
            lineNum++;
            if (altasImage.length() == 0 && lineNum == 2) {
                altasImage = String.format("%s%s%s", new File(altasFile).getParent(), File.separator, line);
            } else if (lineNum == 6) {
                images.put((curName = line), new ArrayList<Integer>());
                lineNum = -1;
            } else if (altasImage.length() > 0 && null != curName) {
                ArrayList<Integer> info = images.get(curName);
                line = line.trim();
                if (line.startsWith("xy") || line.startsWith("size")) {
                    String[] num = line.split(":")[1].split(",");
                    info.add(Integer.valueOf(num[0].trim()));
                    info.add(Integer.valueOf(num[1].trim()));
                }
            }
        }
        BufferedImage image = (BufferedImage) ImageIO.read(new File(altasImage));
        for (Map.Entry<String, ArrayList<Integer>> entry : images.entrySet()) {
            ArrayList<Integer> info = entry.getValue();
            ImageIO.write(image.getSubimage(info.get(0), info.get(1), info.get(2), info.get(3)), "png", new File(String.format("%s%s%s.png", outFolde,File.separator, entry.getKey())));
        }
    }
}
