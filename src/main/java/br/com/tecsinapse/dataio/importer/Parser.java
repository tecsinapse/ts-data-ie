/*
 * Tecsinapse Data Input and Output
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later
 * See the LICENSE file in the root directory or <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package br.com.tecsinapse.dataio.importer;

import java.io.Closeable;
import java.util.List;

import br.com.tecsinapse.dataio.ExporterFormatter;
import br.com.tecsinapse.dataio.type.FileType;

public interface Parser<T> extends Closeable {

    List<T> parse() throws Exception;

    void setExporterFormatter(ExporterFormatter exporterFormatter);

    ExporterFormatter getExporterFormatter();

    void setHeadersRows(int headersRows);

    boolean isIgnoreBlankLinesAtEnd();

    void setIgnoreBlankLinesAtEnd(boolean ignoreBlankLinesAtEnd);

    List<List<String>> getLines() throws Exception;

    int getNumberOfSheets();

    void setSheetNumber(int sheetNumber);

    void setLastsheet(boolean lastsheet);

    void setFirstVisibleSheet();

    int getSheetNumber();

    void setSheetNumberAsFirstNotHidden();

    FileType getFileType();

    void setGroup(Class<?> group);
}
