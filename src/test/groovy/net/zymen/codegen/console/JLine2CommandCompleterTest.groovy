package net.zymen.codegen.console

import jline.console.ConsoleReaderTestSupport
import jline.console.completer.StringsCompleter
import org.junit.Test

class JLine2CommandCompleterTest extends ConsoleReaderTestSupport {
    @Test
    void "should be possible to select commands"() throws Exception {
        console.addCompleter(new JLine2CommandCompleter())

        assertBuffer("create ", this.createBuffer("cre").tab())
        assertBuffer("create entity", this.createBuffer("create e").tab())
    }

    @Test
    void "should be possible to select properties for command"() throws Exception {
        console.addCompleter(new JLine2CommandCompleter())

        assertBuffer("create property dataType", this.createBuffer("create property d").tab())
    }

    @Test
    void "should be possible to add next property"() throws Exception {
        console.addCompleter(new JLine2CommandCompleter())

        assertBuffer("create property dataType:Integer name", this.createBuffer("create property dataType:Integer na").tab())
    }
}
