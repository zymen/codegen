package net.zymen.codegen.console

import jline.console.ConsoleReaderTestSupport
import jline.console.completer.StringsCompleter
import org.junit.Test

class JLine2CommandCompleterTest extends ConsoleReaderTestSupport {
    @Test
    public void test1() throws Exception {
        console.addCompleter(new StringsCompleter("foo", "bar", "baz"));

        assertBuffer("foo ", this.createBuffer("f").tab());
// single tab completes0 to unambiguous "ba"
        assertBuffer("ba", this.createBuffer("b").tab());
        assertBuffer("ba", this.createBuffer("ba").tab());
        assertBuffer("baz ", this.createBuffer("baz").tab());
    }
}
