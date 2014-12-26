package net.zymen.codegen.service

class SystemDirFileService implements DirFileService{
    @Override
    def createDirectory(String directory) {
        new File(directory).mkdirs()
    }
}
