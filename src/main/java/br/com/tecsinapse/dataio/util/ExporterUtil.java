/*
 * Tecsinapse Data Input and Output
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later
 * See the LICENSE file in the root directory or <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package br.com.tecsinapse.dataio.util;

import static br.com.tecsinapse.dataio.type.SeparatorType.SEMICOLON;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.experimental.UtilityClass;

import br.com.tecsinapse.dataio.Table;
import br.com.tecsinapse.dataio.exceptions.ExporterException;
import br.com.tecsinapse.dataio.exceptions.ExporterNotImplementedException;
import br.com.tecsinapse.dataio.txt.FieldTxt;
import br.com.tecsinapse.dataio.txt.FileTxt;
import br.com.tecsinapse.dataio.type.FileType;

@UtilityClass
public final class ExporterUtil {

    public static File writeDataToFile(List<Table> table, FileType fileType, String filename) throws IOException {
        return writeDataToFile(table, fileType, filename, "UTF-8");
    }

    public static File writeDataToFile(List<Table> table, FileType fileType, String filename, String charset) throws IOException {
        return writeDataToFile(table, fileType, filename, charset, SEMICOLON.getSeparator());
    }

    public static File writeDataToFile(List<Table> table, FileType fileType, String filename, String charset, String separator) throws IOException {
        File file = createFile(filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            writeData(table, fileType, fos, charset, separator);
        }
        return file;
    }

    public static void writeData(List<Table> table, FileType fileType, OutputStream outputStream, String charset, String separator) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        if (fileType == FileType.XLSX || fileType == FileType.XLSM) {
            writeXlsx(table, bufferedOutputStream);
            bufferedOutputStream.flush();
            return;
        }
        if (fileType == FileType.SXLSX) {
            writeSxlsx(table, bufferedOutputStream);
            bufferedOutputStream.flush();
            return;
        }
        if (fileType == FileType.XLS) {
            writeXls(table, bufferedOutputStream);
            bufferedOutputStream.flush();
            return;
        }
        if (fileType == FileType.CSV || fileType == FileType.TXT) {
            writeCsv(table, bufferedOutputStream, charset, separator);
            bufferedOutputStream.flush();
            return;
        }
        throw new ExporterNotImplementedException(String.format("File type not supported. %s", fileType));
    }

    public static void writeFileTxtToOutput(FileTxt file, String chartsetName, OutputStream out) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(out, chartsetName)) {
            for (List<FieldTxt> lines : file.getFields()) {
                StringBuilder line = new StringBuilder();
                for (Iterator<FieldTxt> it = lines.iterator(); it.hasNext(); ) {
                    FieldTxt field = it.next();
                    line.append(field.getValue());
                    if (it.hasNext() || file.isEndsWithSeparator()) {
                        line.append(field.getSeparator());
                    }
                }
                line.append("\r\n");
                writer.write(line.toString());
            }
            writer.flush();
        }
    }

    private static void writeSxlsx(List<Table> tables, OutputStream outputStream) throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(new XSSFWorkbook(), 1000);
        writeSpreadsheet(wb, tables, outputStream);
    }

    private static void writeXlsx(List<Table> tables, OutputStream outputStream) throws IOException {
        writeSpreadsheet(new XSSFWorkbook(), tables, outputStream);
    }

    private static void writeXls(List<Table> tables, OutputStream outputStream) throws IOException {
        writeSpreadsheet(new HSSFWorkbook(), tables, outputStream);
    }

    private static void writeSpreadsheet(Workbook workbook, List<Table> tables, OutputStream outputStream) throws IOException {
        WorkbookUtil.newWorkbookUtil().toWorkBook(workbook, tables).write(outputStream);
    }

    private static void writeCsv(List<Table> tables, OutputStream outputStream, String charset, String separator) throws IOException {
        if (tables == null || tables.isEmpty()) {
            throw new ExporterException("Invalid Table.");
        }
        CsvUtil.write(tables.get(0).toStringMatrix(), outputStream, charset, separator);
    }

    private static File createFile(String fileName) throws IOException {
        File originalFile = new File(fileName);
        if (originalFile.isAbsolute()) {
            return originalFile;
        }
        Path tempDir = Files.createTempDirectory("data-io-tmp");
        File newFile = new File(tempDir.toFile(), fileName);
        if (!newFile.getParentFile().exists()) {
            newFile.mkdirs();
        }
        return newFile;
    }

    public static File getCsvFile(Table t, String fileName, String charsetName, char separator) throws IOException {
        return getCsvFile(t, fileName, charsetName, String.valueOf(separator));
    }

    public static File getCsvFile(Table t, String fileName, String charsetName, String separator) throws IOException {
        return writeDataToFile(Collections.singletonList(t), FileType.CSV, fileName, charsetName, separator);
    }

    public static File getCsvFile(Table t, File f, String charsetName, char separator) throws IOException {
        return getCsvFile(t, f, charsetName, String.valueOf(separator));
    }

    public static File getCsvFile(Table t, File f, String charsetName, String separator) throws IOException {
        try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(f))) {
            writeData(Collections.singletonList(t), FileType.CSV, fos, charsetName, separator);
        }
        return f;
    }

    public static File getXlsFile(Table t, String file) throws IOException {
        return getXlsFile(Collections.singletonList(t), file);
    }

    public static File getXlsFile(List<Table> t, String file) throws IOException {
        return writeDataToFile(t, FileType.XLS, file);
    }

    public static File getXlsxFile(Table t, String file) throws IOException {
        return getXlsxFile(Collections.singletonList(t), file);
    }

    public static File getSxlsxFile(Table t, String file) throws IOException {
        return getSxlsxFile(Collections.singletonList(t), file);
    }

    public static File getXlsxFile(List<Table> t, String file) throws IOException {
        return writeDataToFile(t, FileType.XLSX, file);
    }

    public static File getSxlsxFile(List<Table> t, String file) throws IOException {
        return writeDataToFile(t, FileType.SXLSX, file);
    }

    public static void writeCsvToOutput(Table t, String chartsetName, OutputStream out) throws IOException {
        writeData(Collections.singletonList(t), FileType.CSV, out, chartsetName, SEMICOLON.getSeparator());
    }

}
