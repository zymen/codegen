package net.zymen.codegen.console

import jline.console.ConsoleReaderTestSupport
import jline.console.completer.StringsCompleter
import org.junit.Test

class JLine2CommandCompleterTest extends ConsoleReaderTestSupport {
    @Test
    void "should be possible to select commands"() throws Exception {
        console.addCompleter(new JLine2CommandCompleter())

        assertBuffer("create", this.createBuffer("cre").tab())
// single tab completes0 to unambiguous "ba"
        //assertBuffer("ba", this.createBuffer("b").tab());
        //assertBuffer("ba", this.createBuffer("ba").tab());
        //assertBuffer("baz ", this.createBuffer("baz").tab());
    }
}
