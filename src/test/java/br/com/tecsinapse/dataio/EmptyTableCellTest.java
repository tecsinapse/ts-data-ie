/*
 * Tecsinapse Data Input and Output
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later
 * See the LICENSE file in the root directory or <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package br.com.tecsinapse.dataio;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.tecsinapse.dataio.style.Style;

public class EmptyTableCellTest {

    private EmptyTableCell emptyTableCell;

    @BeforeClass
    public void setUp() {
        emptyTableCell = EmptyTableCell.EMPTY_CELL;
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testSetColspan() {
        emptyTableCell.setColspan(0);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testSetContent() {
        emptyTableCell.setContent("");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testSetRowspan() {
        emptyTableCell.setRowspan(0);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testSetTableCellType() {
        emptyTableCell.setTableCellStyle(Style.TABLE_CELL_STYLE_BODY);
    }

}