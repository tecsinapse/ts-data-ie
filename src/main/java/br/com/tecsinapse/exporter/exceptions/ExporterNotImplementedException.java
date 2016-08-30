/*
 * Tecsinapse Data Input and Output
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later
 * See the LICENSE file in the root directory or <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package br.com.tecsinapse.exporter.exceptions;

public class ExporterNotImplementedException extends RuntimeException {

    private static final long serialVersionUID = -34323506834857603L;

    public ExporterNotImplementedException(String message) {
        super(message);
    }
}
