package summersbt.whereemp.ProcessData;

import org.springframework.web.multipart.MultipartFile;
import summersbt.whereemp.Objects.Employee;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnArchive {
    public static Vector<Employee> unArchive(MultipartFile file, Vector<Employee> allEmployee) {
        if (allEmployee == null)
            allEmployee = new Vector<>();
        return unZip(file, allEmployee);
    }

    private static Vector<Employee> unZip(MultipartFile file, Vector<Employee> allEmployee) {
        try {
            if (!Process.getFileExtension(file.getOriginalFilename()).equals("zip")) // проверка файла на zip
                return allEmployee;
            ZipFile zip = new ZipFile(convertFiles.convertMultToFile(file), Charset.forName("CP866"));
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println(entry.getName());
                if (Process.getFileExtension(entry.getName()).equals("xlsx")) { // ролаерка на правильный ексель
                    File fileSave = new File(convertFiles.convertMultToFile(file).getParent(), destinationFile(entry.getName()));
                    write(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(fileSave)));
                    Employee employee = ParseExcel.ReadExcel(fileSave, allEmployee); // читаем файл
                    if (employee != null && !LoadAllFiles.checkEmp(employee, allEmployee)) // проверка объекта на новизну (если такой объект уже был, то он автоматически дополняется)
                        allEmployee.add(employee);
                    fileSave.delete();
                }
            }
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allEmployee;
    }

    // получить имя файла
    private static String destinationFile(final String srcZip) {
        String str = srcZip.substring(srcZip.lastIndexOf("/") + 1, srcZip.length());
        return str;
    }

    // запись данных
    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
