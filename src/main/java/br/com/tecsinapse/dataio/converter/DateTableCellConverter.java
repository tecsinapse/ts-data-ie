/*
 * Tecsinapse Data Input and Output
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later
 * See the LICENSE file in the root directory or <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package br.com.tecsinapse.dataio.converter;

import java.util.Date;

import br.com.tecsinapse.dataio.util.CommonUtils;

import br.com.tecsinapse.dataio.util.ExporterDateUtils;

public class DateTableCellConverter implements FromDateConverter<Date> {

    @Override
    public Date apply(Date input) {
        return input;
    }

    @Override
    public Date apply(String input) {
        return CommonUtils.isNullOrEmpty(input) ? null : ExporterDateUtils.parseDate(input);
    }

}
