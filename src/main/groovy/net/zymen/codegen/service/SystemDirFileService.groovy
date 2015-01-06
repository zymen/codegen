package net.zymen.codegen.service

class SystemDirFileService implements DirFileService{
    @Override
    def createDirectory(String directory) {
        new File(directory).mkdirs()
    }

    @Override
    def writeIntoFile(String filename, String content) {
        new File(filename).write(content)
    }

    @Override
    def appendBeforeClassEnd(String filename, String content) {
        String fileContent = new File(filename).getText()
        Integer insertPosition = fileContent.lastIndexOf('}')

        String contentBefore = fileContent.substring(0, insertPosition)
        String contentAfter = fileContent.substring(insertPosition)

        println "Writing content into ${filename}"
        new File(filename).write(contentBefore + content + contentAfter)
    }

    @Override
    Boolean fileExists(String filename) {
        new File(filename).exists()
    }
}
