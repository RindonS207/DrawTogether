package rindong.project.file;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImage
{
    public static void savefile(JFrame mainwindow, BufferedImage image)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("保存图片文件");
        fileChooser.setFileFilter(new FileNameExtensionFilter("图片文件(jpg)",new String[]{"jpg"}));
        File desktopPath= FileSystemView.getFileSystemView().getHomeDirectory();
        File selectFilePath=new File(desktopPath,"未命名.jpg");
        fileChooser.setSelectedFile(selectFilePath);
        int value = fileChooser.showSaveDialog(mainwindow);
        if (value == 0)
        {
            try
            {
                File save=fileChooser.getSelectedFile();
                if (save == null)
                {
                    return;
                }
                else
                {
                    ImageIO.write(image,"jpg",save);
                }
            }
            catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
