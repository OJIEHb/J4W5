import java.util.Scanner;

/**
 * Created by andrey on 23.11.16.
 */
public class Main {
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter path to directory!");
        String path;
        do{
            path = scanner.next();
        }while(!ZipFileMaker.checkDirectoryPath(path));
        ZipFileMaker zipFileMaker= new ZipFileMaker(path);
        zipFileMaker.zipDirectory();
    }
}
