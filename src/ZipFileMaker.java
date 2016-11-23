import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by andrey on 23.11.16.
 */
public class ZipFileMaker {
    private Map archives= new HashMap<String, String[]>();
    private final File directoryPath;
    private ArrayList<File> files = new ArrayList<>();

    public ZipFileMaker(String directoryPath){
        archives.put("audio",new String[]{"mp3","wav","wma"});
        archives.put("video",new String[]{"avi","mp4","flv","3gp"});
        archives.put("image",new String[]{"jpeg","jpg","gif","png"});
        this.directoryPath = new File(directoryPath);
    }

    public static boolean checkDirectoryPath(String directoryPath){
        File file = new File(directoryPath);
        return file.isDirectory() & file.exists();
    }

    private void findFiles(File path){
        File[] filesList = path.listFiles();
        for (File file:filesList) {
            if(file.isDirectory()) {findFiles(file);}
            else{files.add(file);}
        }
    }

    private boolean checkExtension(String path, String [] extensions){
        for (String extension : extensions) {
            if(path.endsWith(extension))return true;
        }
        return false;
    }

    private ArrayList<File> sort(String [] extensions){
        ArrayList <File> sortFiles = new ArrayList<>();
        for (File file : files){
            if (checkExtension(file.getAbsolutePath(),extensions)) sortFiles.add(file);
        }
        return sortFiles;
    }

    private void createZipFile(File pathForArchives,ArrayList<File> files){
        try(ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(pathForArchives))){
            for(File file:files) {
                ZipEntry zipEntry = new ZipEntry(file.getAbsolutePath().replace(directoryPath.getAbsolutePath(),""));
                zipOut.putNextEntry(zipEntry);
                write(new FileInputStream(file),zipOut);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void write(InputStream inputStream, ZipOutputStream outputStream){
        byte[] buffer=new byte[2048];
        int lenght;
        try{
            while ((lenght=inputStream.read(buffer))>=0){outputStream.write(buffer,0,lenght);}
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void zipDirectory(){
        findFiles(directoryPath);
        archives.forEach((zipFolder,extensions)-> createZipFile(new File(directoryPath,zipFolder.toString()+".zip"),sort((String[])extensions)));
    }
}
