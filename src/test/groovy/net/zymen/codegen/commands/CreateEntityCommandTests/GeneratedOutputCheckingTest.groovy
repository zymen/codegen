package net.zymen.codegen.commands.CreateEntityCommandTests

import net.zymen.codegen.Context
import net.zymen.codegen.commands.CreateEntityCommand
import net.zymen.codegen.service.DirFileService
import spock.lang.Specification

class GeneratedOutputCheckingTest extends Specification{
    def "should generate correct output for model layer"() {
        given:
        def dirFileService = Mock(DirFileService)
        dirFileService.fileExists(_) >> { false }
        CreateEntityCommand command = new CreateEntityCommand(name: 'Test', layer: 'model', context: new Context(topPackage: 'pack'))
        command.dirFileService = dirFileService

        when:
        command.execute()

        then:
        1 * dirFileService.writeIntoFile(_,
                """\
package pack.model;

class Test {

}
""")
    }

    def "should generate correct output for model service"() {
        given:
            def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            CreateEntityCommand command = new CreateEntityCommand(name: 'TestService', layer: 'service', context: new Context(topPackage: 'pack'))
            command.dirFileService = dirFileService

        when:
            command.execute()

        then:
            1 * dirFileService.writeIntoFile(_,
                    """\
package pack.service;

class TestService {

}
""")
    }

    def "should generate correct output for model controller"() {
        given:
        def dirFileService = Mock(DirFileService)
            dirFileService.fileExists(_) >> { false }
            CreateEntityCommand command = new CreateEntityCommand(name: 'TestController', layer: 'controller', context: new Context(topPackage: 'pack'))
            command.dirFileService = dirFileService

        when:
            command.execute()

        then:
        1 * dirFileService.writeIntoFile(_,
                """\
package pack.controller;

import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/TestController")
@Consumes("application/json")
@Component
class TestController {

}
""")
    }
}
