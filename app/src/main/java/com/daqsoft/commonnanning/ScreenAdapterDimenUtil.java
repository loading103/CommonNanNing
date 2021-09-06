package com.daqsoft.commonnanning;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
/**
 * @ClassName: ScreenAdapterDimenUtil
 * @Description: 屏幕适配工具类，以375dp为基准
 * @Author: PuHua
 * @CreateDate: 2019/6/10 15:14
 * @Version: 1.0
 */
public class ScreenAdapterDimenUtil {
    private float baseW;
    private static final String supportStr = "294;300;320;340;375;400;480;520;600;720;";
    private final static String rootPath = "E:\\android_workspace\\2019\\CommonNanNing\\CommonNanNing\\app\\src\\main\\res\\values-sw{0}dp\\";
    /**
     * DP模板
     */
    private final static String WTemplate = "<dimen name=\"dp_{0}\">{1}dp</dimen>\n";
    /**
     * sp模板
     */
    private final static String SPTemplate = "<dimen name=\"sp_{0}\">{1}sp</dimen>\n";
    public ScreenAdapterDimenUtil(float baseW) {
        this.baseW = baseW;
        File dir = new File(rootPath);
        if (!dir.exists()) {
            dir.mkdir();

        }
    }
        public static void main(String[] args) {
            //基准的屏幕最小宽度，可以根据实际情况制定。
            float baseW = 375f;//720*1280(xhdpi)屏幕宽度为360dp
            new ScreenAdapterDimenUtil(baseW).generate();
        }
        public void generate() {
        String[] vals = supportStr.split(";");
        for (String val : vals) {
            screenString(Integer.parseInt(val));
        }

    }
        public void screenString(float w) {
            StringBuffer sbForWidth = new StringBuffer();
            sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            sbForWidth.append("<resources>");
            float cellw = w  / baseW;//2表示屏幕密度xhdpi

            for (int i = 1; i <= 375; i++) {
                sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
                        change(cellw * i) + ""));
            }

            for(int i =0 ;i <=50 ;i++){
                if (i>=30&&i%2==1){
                    continue;
                }
                sbForWidth.append(SPTemplate.replace("{0}", i + "").replace("{1}",
                        change(cellw * i) + ""));
            }
            sbForWidth.append("</resources>");

            File fileDir = new File(rootPath.replace("{0}", (int)w + ""));
            fileDir.mkdir();

            File layxFile = new File(fileDir.getAbsolutePath(), "dimens.xml");
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
                pw.print(sbForWidth.toString());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

}
