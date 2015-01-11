package net.zymen.codegen.console

import jline.console.ConsoleReaderTestSupport
import jline.console.completer.StringsCompleter
import org.junit.Before
import org.junit.Test

class JLine2CommandCompleterTest extends ConsoleReaderTestSupport {
    @Before
    void setupTest() {
        console.addCompleter(new JLine2CommandCompleter())
    }

    @Test
    void "should be possible to select commands"() throws Exception {
        assertBuffer("create ", this.createBuffer("cre").tab())
        assertBuffer("create entity", this.createBuffer("create e").tab())
    }

    @Test
    void "should be possible to select properties for command"() throws Exception {
        assertBuffer("create property dataType", this.createBuffer("create property d").tab())
    }

    @Test
    void "should be possible to add next property"() throws Exception {
        assertBuffer("create property dataType:Integer name", this.createBuffer("create property dataType:Integer na").tab())
    }

    @Test
    void "should be possible to suggest next property"() throws Exception {
        assertBuffer("create property dataType:Integer", this.createBuffer("create property dataType:Integer").tab())
    }

    @Test
    void "should be possible to add property just after command name"() throws Exception {
        assertBuffer("create property", this.createBuffer("create property").tab())
    }
}
