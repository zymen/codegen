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
}
