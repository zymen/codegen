package net.zymen.codegen.service

public interface DirFileService {
    def createDirectory(String directory)

    def writeIntoFile(String filename, String content)

    def appendBeforeClassEnd(String filename, String content)

    Boolean fileExists(String filename)
}